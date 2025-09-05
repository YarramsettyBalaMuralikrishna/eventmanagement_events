package com.vilt.eventmanagement.eventmanagement_events.controller;


import com.vilt.eventmanagement.eventmanagement_events.Enums.Locations;
import com.vilt.eventmanagement.eventmanagement_events.dto.VenueUpdateDTO;
import com.vilt.eventmanagement.eventmanagement_events.entity.Venue;

import com.vilt.eventmanagement.eventmanagement_events.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/venue")
public class VenueController {
    private final VenueService venueService;
    public VenueController(VenueService venueService){
        this.venueService = venueService;
    }

    @PostMapping("/create")
    public ResponseEntity<Venue> createVenue(@Valid @RequestBody  Venue venue){
        return venueService.createVenue(venue);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVenue(@Valid @RequestBody VenueUpdateDTO venue){
        return venueService.updateVenue(venue);
    }

    @DeleteMapping("/delete/{venueId}")
    public ResponseEntity<?> deleteVenue(@PathVariable long venueId){
        return venueService.deleteVenue(venueId);
    }

    @GetMapping("/show/byId/{venueId}")
    public ResponseEntity<?> showVenueById(@PathVariable long venueId){
        return venueService.showVenueById(venueId);
    }

    @GetMapping("/show/byName")
    public ResponseEntity<?> showVenueByName(@RequestParam String name){
        return  venueService.showVenueByName(name);
    }

    @GetMapping("/show/byLocation")
    public ResponseEntity<?> showVenueByLocation(@RequestParam  Locations location){
        return  venueService.showVenueByLocation(location);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAllVenues(){
        return venueService.showAllVenues();
    }

    @GetMapping("/show/byEmail")
    public ResponseEntity<?> showVenueByEmail(@RequestParam String email) {
        return venueService.showVenueByEmail(email);
    }
}
