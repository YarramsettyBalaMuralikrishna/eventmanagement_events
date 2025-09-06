package com.vilt.eventmanagement.eventmanagement_events.repository;

import com.vilt.eventmanagement.eventmanagement_events.Enums.EventSessions;
import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {
    List<Events> findByNameContainingIgnoreCaseAndUserId(String name, long userId);

    Optional<Events> findByIdAndUserId(long eventId, long userId);

    List<Events> findByUserId(long userId);

    List<Events> findByUserIdAndEventDateAndEventSession(long userId, LocalDate eventDate, EventSessions eventSession);

    List<Events> findByVendorId(long vendorId);

    List<Events> findByTypesAndVendorIdIsNull(EventTypes service);

    List<Events> findByUserIdAndEventDateBefore(long userId, LocalDate today);

    List<Events> findByUserIdAndEventDateAfter(long userId, LocalDate today);

    List<Events> findByUserIdAndEventDate(long userId, LocalDate date);

    List<Events> findByVendorIdAndEventDateBefore(long vendorId, LocalDate today);

    List<Events> findByVendorIdAndEventDateAfter(long vendorId, LocalDate today);

    List<Events> findByVendorIdAndEventDate(long vendorId, LocalDate date);

    List<Events> findByVendorIdAndEventDateBetweenOrderByEventDateAsc(long vendorId, LocalDate pastDate, LocalDate futureDate);
}
