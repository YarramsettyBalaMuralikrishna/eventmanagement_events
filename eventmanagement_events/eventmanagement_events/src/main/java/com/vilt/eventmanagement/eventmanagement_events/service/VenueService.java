package com.vilt.eventmanagement.eventmanagement_events.service;

import com.vilt.eventmanagement.eventmanagement_events.Enums.Locations;
import com.vilt.eventmanagement.eventmanagement_events.dto.VenueUpdateDTO;
import com.vilt.eventmanagement.eventmanagement_events.entity.Venue;
import com.vilt.eventmanagement.eventmanagement_events.repository.VenueRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository){
        this.venueRepository = venueRepository;
    }

    // create Venue
    public ResponseEntity<Venue> createVenue(Venue venue){
        Venue savedVenue = venueRepository.save(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
    }


    // updateVenue
    @Transactional
    public ResponseEntity<?> updateVenue(VenueUpdateDTO dto){
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().body("Venue ID is required for update");
        }

        Venue venue = venueRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Venue with ID " + dto.getId() + " not found"));

        // ✅ Apply updates only if fields are provided
        if (dto.getName() != null) {
            venue.setName(dto.getName());
        }
        if(dto.getEmail()!=null){
            venue.setEmail(dto.getEmail());
        }
        if (dto.getDescription() != null) {
            venue.setDescription(dto.getDescription());
        }
        if (dto.getLocation() != null) {
            venue.setLocation(dto.getLocation());
        }
        if (dto.getAddress() != null) {
            venue.setAddress(dto.getAddress());
        }
        if (dto.getPricePerSession() != null) {
            venue.setPricePerSession(dto.getPricePerSession());
        }
        if (dto.getMaxCapacity() != null) {
            venue.setMaxCapacity(dto.getMaxCapacity());
        }

        Venue updated = venueRepository.save(venue);
        return ResponseEntity.ok(updated);
    }

    @Transactional
    public ResponseEntity<?> deleteVenue(long venueId){
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        if (optionalVenue.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Venue with ID: " + venueId  + " not found");
        }
        venueRepository.delete(optionalVenue.get());
        return ResponseEntity.ok("Venue with ID: " + venueId + " deleted successfully");
        // later phase for all non completed events change the events venue to null
    }

    // read venue
    public ResponseEntity<?> showVenueById(long venueId){
        Optional<Venue> venue = venueRepository.findById(venueId);
        if(venue.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venue with ID:"+venueId+ " dose not exists");
        }
        return ResponseEntity.ok(venue.get());
    }

    public ResponseEntity<?> showVenueByName(String name){
        List<Venue> allVenues = venueRepository.findByNameContainingIgnoreCase(name);
        if(allVenues.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venue with name:"+name+ " dose not found");
        }
        return ResponseEntity.ok(allVenues);
    }

    public ResponseEntity<?> showVenueByLocation(Locations location){
        List<Venue> allVenues = venueRepository.findByLocation(location);
        if (allVenues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No venues found for location: " + location);
        }
        return ResponseEntity.ok(allVenues);
    }

    public ResponseEntity<?> showAllVenues(){
        List<Venue> allVenues = venueRepository.findAll();
        if (allVenues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No venues found");
        }
        return ResponseEntity.ok(allVenues);
    }

    public ResponseEntity<?> showVenueByEmail(String email){
        Optional<Venue> venue = venueRepository.findByEmail(email);
        if (venue.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No venue found with email: " + email);
        }
        return ResponseEntity.ok(venue.get());
    }

}
