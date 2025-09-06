package com.vilt.eventmanagement.eventmanagement_events.controller;


import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import com.vilt.eventmanagement.eventmanagement_events.service.EventVendorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/vendor")
public class EventVendorController {
    private final EventVendorService eventVendorService;

    @Value("${app.vendor.id}")
    private long vendorId;

    public EventVendorController(EventVendorService eventVendorService){
        this.eventVendorService = eventVendorService;
    }

    @GetMapping("/eventsMapped")
    public ResponseEntity<?> eventsMappedToVendor(){
        return eventVendorService.eventsMappedToVendor(vendorId);
    }

    @GetMapping("/events/service/novendor/{service}")
    public ResponseEntity<?> fetchEventsByServiceAndNoVendor(@PathVariable String service){
        EventTypes type;
        try {
            type = EventTypes.valueOf(service.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid event type: " + service);
        }
        return eventVendorService.fetchEventsByServiceAndNoVendor(type);
    }

    @PutMapping("/events/{eventId}/assignVendor")
    public ResponseEntity<String> assignVendorToEvent(@PathVariable("eventId") long eventId){
        return eventVendorService.assignVendorToEvent(eventId, vendorId);
    }
    @GetMapping("/getEventsByDateRange")
    public ResponseEntity<?> eventsByDateRange(@RequestParam("pastDate") LocalDate pastDate, @RequestParam("futureDate") LocalDate futureDate){
        return eventVendorService.eventsByDateRange(vendorId, pastDate, futureDate);
    }
    @GetMapping("/pastEvents")
    public ResponseEntity<?> pastEventsByUser(){
        return eventVendorService.pastEventsDoneByVendor(vendorId);
    }

    @GetMapping("/futureEvents")
    public ResponseEntity<?> futureEventsByUser(){
        return eventVendorService.futureEventsByVendor(vendorId);
    }

    @GetMapping("/getEventByDate")
    public ResponseEntity<?> getEventByDate(@RequestParam("date") LocalDate date){
        return  eventVendorService.eventOnDate(vendorId, date);
    }
}
