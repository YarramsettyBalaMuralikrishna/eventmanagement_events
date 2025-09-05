package com.vilt.eventmanagement.eventmanagement_events.repository;


import com.vilt.eventmanagement.eventmanagement_events.Enums.Locations;
import com.vilt.eventmanagement.eventmanagement_events.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByNameContainingIgnoreCase(String name);

    List<Venue> findByLocation(Locations location);

    Optional<Venue> findByEmail(String email);
}
