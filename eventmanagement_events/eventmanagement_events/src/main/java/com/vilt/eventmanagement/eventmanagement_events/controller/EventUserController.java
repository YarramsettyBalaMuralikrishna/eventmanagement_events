package com.vilt.eventmanagement.eventmanagement_events.controller;


import com.vilt.eventmanagement.eventmanagement_events.dto.EventUpdateDTO;
import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import com.vilt.eventmanagement.eventmanagement_events.service.EventUserGetService;
import com.vilt.eventmanagement.eventmanagement_events.service.EventUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class EventUserController {
    private final EventUserService eventUserService;
    private final EventUserGetService eventUserGetService;

    @Value("${app.user.id}")
    private long userId;

    public EventUserController(EventUserService eventUserService, EventUserGetService eventUserGetService){
        this.eventUserGetService = eventUserGetService;
        this.eventUserService = eventUserService;
    }

    // method for saving event
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@Valid @RequestBody Events event){
        return eventUserService.createEvent(event);
    }

    // updating event
    // date check and update
    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventUpdateDTO events){
        return eventUserService.updateEvent(events, userId);
    }

    //delete event
    @DeleteMapping("/delete/{eventID}")
    public ResponseEntity<?> deleteEvent(@PathVariable("eventID") long eventID){
        return ResponseEntity.ok(eventUserService.deleteEvent(eventID));
    }
    // get event by eventName
    @GetMapping("/getEventByName/{name}")
    public ResponseEntity<?> getEventByName(@PathVariable("name") String name){
        return eventUserGetService.getEventByName(name, userId);
    }

    // get event by eventId
    @GetMapping("/getEventByEventId/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") long eventId){
        return  eventUserGetService.getEventById(eventId, userId);
    }

    // get event by eventsByUser
    @GetMapping("/allEventsByUser")
    public ResponseEntity<?> eventsByUser(){
        return eventUserGetService.eventsByUser(userId);
    }

    @GetMapping("/pastEvents")
    public ResponseEntity<?> pastEventsByUser(){
        return eventUserGetService.pastEventsByUser(userId);
    }

    @GetMapping("/futureEvents")
    public ResponseEntity<?> futureEventsByUser(){
        return eventUserGetService.futureEventsByUser(userId);
    }

    @GetMapping("/getEventByDate")
    public ResponseEntity<?> getEventByDate(@RequestParam("date") LocalDate date){
        return  eventUserGetService.getEventByDate(date, userId);
    }

    @GetMapping("/getEventsByDateRange")
    public ResponseEntity<?> eventsByDateRange(@RequestParam("pastDate") LocalDate pastDate,@RequestParam("futureDate") LocalDate futureDate){
        return eventUserGetService.eventsByDateRange(userId, pastDate, futureDate);
    }

}

