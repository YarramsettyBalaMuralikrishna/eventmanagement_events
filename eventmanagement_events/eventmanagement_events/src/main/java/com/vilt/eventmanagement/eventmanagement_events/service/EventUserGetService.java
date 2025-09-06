package com.vilt.eventmanagement.eventmanagement_events.service;


import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.InvalidDateException;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.InvalidEventException;
import com.vilt.eventmanagement.eventmanagement_events.repository.EventRepository;
import com.vilt.eventmanagement.eventmanagement_events.repository.VenueRepository;
import jdk.jfr.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Events eve = events.get();
        return ResponseEntity.ok(eve);

    }

    // Upcoming events
    public ResponseEntity<?> eventsByUser(long userId){
        List<Events> events = eventRepository.findByUserId(userId);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" events for user ID :"+ userId+" is not found");
        }
        return ResponseEntity.ok(events);
    }

    // past events of a user
    public ResponseEntity<?> pastEventsByUser(long userId){
        LocalDate today = LocalDate.now();
        List<Events> pastEvents = eventRepository.findByUserIdAndEventDateBefore(userId, today);
        if(pastEvents.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No past events found for user before date "+ today);
        }
        return ResponseEntity.ok(pastEvents);
    }

    // future events of a user
    public ResponseEntity<?> futureEventsByUser(long userId){
        LocalDate today = LocalDate.now();
        List<Events> futureEvents = eventRepository.findByUserIdAndEventDateAfter(userId, today);
        if(futureEvents.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No future events found for user with ID: " + userId + " after date " + today);
        }
        return ResponseEntity.ok(futureEvents);
    }

    // get event with particular date
    public ResponseEntity<?> getEventByDate(LocalDate date, long userId){
        List<Events> event = eventRepository.findByUserIdAndEventDate(userId, date);
        if(event.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found for user with ID: " + userId + " on date " + date);
        }
        return ResponseEntity.ok(event);
    }

    // range of events
    public ResponseEntity<?> eventsByDateRange(long userId, LocalDate pastDate, LocalDate futureDate){
        if(pastDate==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("past date cannot be null");
        }
        if(futureDate==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("future date cannot be null");
        }
        if(pastDate.isAfter(futureDate)){
            throw new InvalidDateException("past date cannot be ahead of future date");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        List<Events> events = eventRepository.findByVendorIdAndEventDateBetweenOrderByEventDateAsc(userId, pastDate, futureDate);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found for user with ID: " + userId + " in the date range " + pastDate + " to " + futureDate + ".");
        }
        return ResponseEntity.ok(events);
    }


    // events by session, budget, vendor + userID


}
