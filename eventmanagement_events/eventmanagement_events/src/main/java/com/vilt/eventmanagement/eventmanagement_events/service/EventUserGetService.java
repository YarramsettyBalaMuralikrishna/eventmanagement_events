package com.vilt.eventmanagement.eventmanagement_events.service;


import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import com.vilt.eventmanagement.eventmanagement_events.repository.EventRepository;
import com.vilt.eventmanagement.eventmanagement_events.repository.VenueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventUserGetService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventUserGetService(EventRepository eventRepository, VenueRepository venueRepository){
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public ResponseEntity<?> getEventByName(String name, long userId){
        // user name checking
        List<Events> getEvents = eventRepository.findByNameContainingIgnoreCaseAndUserId(name, userId);
        if(getEvents.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events were found for name: " + name + " and userID: " + userId);
        }
        return ResponseEntity.ok(getEvents);
    }

    public ResponseEntity<?> getEventById(long eventId, long userId){
        Optional<Events> events = eventRepository.findByIdAndUserId(eventId, userId);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No event found with ID: " + eventId + " for userID: " + userId);
        }
        return ResponseEntity.ok(events);

    }

    // all events by particular user completed and about to happen
    // latter change it to getCompleted Events
    // Upcoming events
    public ResponseEntity<?> eventsByUser(long userId){
        List<Events> events = eventRepository.findByUserId(userId);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" events for user ID :"+ userId+" is not found");
        }
        return ResponseEntity.ok(events);
    }

    // past events of a user

    // future events of a user

    // get event with particular date

    // venue for a particular event along with all its details


}
