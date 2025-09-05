package com.vilt.eventmanagement.eventmanagement_events.service;


import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.InvalidEventException;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.VendorAssignedException;
import com.vilt.eventmanagement.eventmanagement_events.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventVendorService {

    private final EventRepository eventRepository;
    public EventVendorService(EventRepository eventRepository ){
        this.eventRepository = eventRepository;
    }

    // look at events which are mapped to particular vendor
    public ResponseEntity<?> eventsMappedToVendor(Long vendorId){
        List<Events> allEventsOfVendor = eventRepository.findByVendorId(vendorId);
        if(allEventsOfVendor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found for vendor with id:"+vendorId);
        }
        return ResponseEntity.ok(allEventsOfVendor);
    }

    // look for events which are not mapped to any vendor and having service this service
    public ResponseEntity<?> fetchEventsByServiceAndNoVendor(EventTypes service){
        List<Events> allEventsOfServiceNull = eventRepository.findByTypesAndVendorIdIsNull(service);
        if(allEventsOfServiceNull.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found with no vendor assigned for type:"+service);
        }
        return ResponseEntity.ok(allEventsOfServiceNull);
    }

    // provide service for event with no vendor
    public ResponseEntity<String> assignVendorToEvent(long eventId, Long vendorId) {
        Events event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventException("Event with Id: " + eventId + " not found"));

        if (event.getVendorId() != null) {
            throw new VendorAssignedException("Vendor for event '" + event.getName() + "' is already assigned");
        }

        event.setVendorId(vendorId);
        eventRepository.save(event);

        return ResponseEntity.ok("Vendor assigned successfully");
    }



}
