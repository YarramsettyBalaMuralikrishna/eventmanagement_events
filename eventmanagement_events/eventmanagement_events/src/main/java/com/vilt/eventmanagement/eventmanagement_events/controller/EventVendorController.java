package com.vilt.eventmanagement.eventmanagement_events.controller;


import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import com.vilt.eventmanagement.eventmanagement_events.service.EventVendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
public class EventVendorController {
    private final EventVendorService eventVendorService;
    public EventVendorController(EventVendorService eventVendorService){
        this.eventVendorService = eventVendorService;
    }

    @GetMapping("/events/{vendorId}")
    public ResponseEntity<?> eventsMappedToVendor(@PathVariable Long vendorId){
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

    @PutMapping("/events/{eventId}/assignVendor/{vendorId}")
    public ResponseEntity<String> assignVendorToEvent(@PathVariable("eventId") long eventId, @PathVariable("vendorId") Long vendorId){
        return eventVendorService.assignVendorToEvent(eventId, vendorId);
    }
}
