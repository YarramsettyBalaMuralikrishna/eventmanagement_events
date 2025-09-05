package com.vilt.eventmanagement.eventmanagement_events.service;


import com.vilt.eventmanagement.eventmanagement_events.Enums.EventSessions;
import com.vilt.eventmanagement.eventmanagement_events.dto.EventUpdateDTO;
import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import com.vilt.eventmanagement.eventmanagement_events.entity.Venue;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.InvalidEventException;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.venueException.InvalidVenueException;
import com.vilt.eventmanagement.eventmanagement_events.repository.EventRepository;
import com.vilt.eventmanagement.eventmanagement_events.repository.VenueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventUserService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventUserService(EventRepository eventRepository, VenueRepository venueRepository){
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public boolean checkForEventEligibilityTime(long userId, LocalDate currentDate, EventSessions session){
        List<Events> evntInTimeFrame = eventRepository.findByUserIdAndEventDateAndEventSession(userId, currentDate, session);
        return evntInTimeFrame.isEmpty();
    }

    // event creation
    public ResponseEntity<String> createEvent(Events event){
        Venue venue = event.getVenue();
        // check for venue
        Venue venueCheck = venueRepository.findById(venue.getId())
                .orElseThrow(() -> new InvalidVenueException(
                        "Venue with ID: " + venue.getId() + " and name: " + venue.getName() + " not found"));
        // exception for vendor -> pick from vendor service
        // ^^^ since we are using jwt token userID is obtained form it and vendor ID must be validated before hand
        Long vendorID =  event.getVendorId();

        // guest count should not be more than venue max capacity
        int userExpectedGuestCount = event.getGuestCount();
        int venueMaxCapacity = venueCheck.getMaxCapacity();
        if(userExpectedGuestCount>venueMaxCapacity){
            throw new InvalidEventException("Expected guest count of current event is "
                    +userExpectedGuestCount+" greater than venue capacity "+ venueMaxCapacity+" please change venue or reduce guest count");
        }

        // check for budget
        // for now check only for venue budget later we can look for vendor thing
        long userBudget = event.getBudget();
        long venueCost = venueCheck.getPricePerSession();
        if (userBudget < venueCost) {throw new InvalidEventException(
                    "Your budget (" + userBudget +") is less than the venue cost (" + venueCost +
                            "). Please increase your budget or choose a different venue.");
        }

        // user should not have any other event in that time frame
        LocalDate eventDate = event.getEventDate();
        EventSessions session = event.getEventSession();
        if(!checkForEventEligibilityTime(event.getUserId(), eventDate, session)){
            throw new InvalidEventException(
                    "You already have an event scheduled on " + eventDate +
                            " during the " + session + " session. Please choose a different date or session.");
        }

        event.setVenue(venueCheck);
        // all good
        eventRepository.save(event);
        return new ResponseEntity<>(
                "Event '" + event.getName() + "' has been successfully created at location '"
                        + event.getVenue().getLocation() + "' on "
                        + event.getEventDate() + " during "
                        + event.getEventSession() + " session.",
                HttpStatus.CREATED
        );

    }

    // Updating event
    @Transactional
    public ResponseEntity<EventUpdateDTO> updateEvent(EventUpdateDTO dto, long userId) {
        // Fetch current event
        Events currentEvent = eventRepository.findById(dto.getId())
                .orElseThrow(() -> new InvalidEventException("Event with ID: " + dto.getId() + " not found"));

        // Validate userId (cannot be changed)
        if (currentEvent.getUserId() != userId) {
            throw new InvalidEventException("User ID cannot be changed for an event.");
        }

        // at least one field to be  updated
        if (!dto.hasAtLeastOneFieldToUpdate()) {
            throw new InvalidEventException("Please provide at least one field to update the event.");
        }

        LocalDate today = LocalDate.now();

        // Update event name
        if (dto.getName() != null) {
            currentEvent.setName(dto.getName());
        }

        // Update event description
        if (dto.getDescription() != null) {
            currentEvent.setDescription(dto.getDescription());
        }

        // Update event date (only if >= 2 days from today)
        if (dto.getEventDate() != null) {
            LocalDate newDate = dto.getEventDate();
            if (!newDate.isAfter(today.plusDays(2))) { // must be at least 2 days in future
                throw new InvalidEventException("Event date must be at least 2 days from today (" + today + ").");
            }
            currentEvent.setEventDate(newDate);
        } else {
            // if no new date provided, allow updates only if current date is at least 2 days from today
            if (!currentEvent.getEventDate().isAfter(today.plusDays(2))) {
                throw new InvalidEventException("Cannot update this event within 2 days of the scheduled date.");
            }
        }

        //  Update Event Type
        if (dto.getTypes() != null) {
            currentEvent.setTypes(dto.getTypes());
        }

        // Update Event Session
        if (dto.getEventSession() != null) {
            currentEvent.setEventSession(dto.getEventSession());
        }

        // Handle venue changes
        Venue oldVenue = currentEvent.getVenue();
        Venue newVenue = null;
        if (dto.getVenueId() != null && !Objects.equals(dto.getVenueId(), oldVenue.getId())) {
            newVenue = venueRepository.findById(dto.getVenueId())
                    .orElseThrow(() -> new InvalidVenueException("Venue with ID: " + dto.getVenueId() + " does not exist"));
            currentEvent.setVenue(newVenue);
        }

        // Update budget
        Long oldBudget = currentEvent.getBudget();
        Long venuePrice = (newVenue != null) ? newVenue.getPricePerSession() : oldVenue.getPricePerSession();

        // Determine which budget to use for validation: new budget if provided, else old budget
        Long budgetToUse = (dto.getBudget() != null) ? dto.getBudget() : oldBudget;

        // Check if the budget satisfies the venue cost
        if (budgetToUse < venuePrice) {
            throw new InvalidEventException(
                    "Budget (" + budgetToUse + ") cannot be less than the new venue cost (" + venuePrice + ")."
            );
        }

// Update the budget only if the user provided a new one
        if (dto.getBudget() != null) {
            currentEvent.setBudget(dto.getBudget());
        }

        // Update guest count
        if (dto.getGuestCount() != null) {
            int maxCapacity = (newVenue != null) ? newVenue.getMaxCapacity() : oldVenue.getMaxCapacity();
            if (dto.getGuestCount() > maxCapacity) {
                throw new InvalidEventException("Guest count (" + dto.getGuestCount() + ") exceeds venue capacity (" + maxCapacity + ").");
            }
            currentEvent.setGuestCount(dto.getGuestCount());
        }

        // Save updated event
        Events updatedEvent = eventRepository.save(currentEvent);

        // Return response
        return ResponseEntity.ok(new EventUpdateDTO(updatedEvent));

        // vendor updation should be done
        // NO USER id field in DTO so no need to worry here
    }

    // Deletion of Event
    @Transactional
    public String deleteEvent(long eventID){
        Events currentEvent = eventRepository.findById(eventID)
                .orElseThrow(() -> new InvalidEventException("Event with id: " + eventID + " not found"));

        // notify user and vendor _> app complexity
        //------

//        remove that event from venue list
        Venue venue = currentEvent.getVenue();
        if (venue != null && venue.getEvents() != null) {
            venue.getEvents().remove(currentEvent);
        }
        // Delete the event itself
        eventRepository.delete(currentEvent);

        return "Event with ID " + eventID + " has been deleted successfully.";

    }

    // by event Name + userName

    // by date + userID


    // method for past events by userID

    // by session + userID


}
