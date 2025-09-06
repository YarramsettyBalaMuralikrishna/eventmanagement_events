package com.vilt.eventmanagement.eventmanagement_events.service;


import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.InvalidDateException;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.InvalidEventException;
import com.vilt.eventmanagement.eventmanagement_events.exceptions.VendorAssignedException;
import com.vilt.eventmanagement.eventmanagement_events.repository.EventRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
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

    // events done by vendor -> past,  ->future
    public ResponseEntity<?>  pastEventsDoneByVendor(long vendorId){
        LocalDate today = LocalDate.now();
        List<Events> events = eventRepository.findByVendorIdAndEventDateBefore(vendorId, today);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No past events found for vendor with ID: " + vendorId + " before date " + today);
        }
        return  ResponseEntity.ok(events);
    }
    public ResponseEntity<?>  futureEventsByVendor(long vendorId){
        LocalDate today = LocalDate.now();
        List<Events> events = eventRepository.findByVendorIdAndEventDateAfter(vendorId, today);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No future events found for vendor with ID: " + vendorId + " after date " + today);
        }
        return  ResponseEntity.ok(events);
    }

    // particular event date by vendor
    public ResponseEntity<?> eventOnDate(long vendorId, LocalDate date){
        List<Events> events = eventRepository.findByVendorIdAndEventDate(vendorId, date);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No  events found for vendor with ID: " + vendorId + " on date " + date);
        }
        return  ResponseEntity.ok(events);
    }

    // events on date from date to date done by vendor
    public ResponseEntity<?> eventsByDateRange(long vendorId, LocalDate pastDate, LocalDate futureDate){
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
        List<Events> events = eventRepository.findByVendorIdAndEventDateBetweenOrderByEventDateAsc(vendorId, pastDate, futureDate);
        if(events.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found for vendor with ID: " + vendorId + " in the date range " + pastDate + " to " + futureDate + ".");
        }
        return ResponseEntity.ok(events);
    }



    //  particular event



}
