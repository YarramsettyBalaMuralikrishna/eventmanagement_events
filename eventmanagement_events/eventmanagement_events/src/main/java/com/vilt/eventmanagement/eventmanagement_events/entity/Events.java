package com.vilt.eventmanagement.eventmanagement_events.entity;

import com.vilt.eventmanagement.eventmanagement_events.Enums.EventSessions;
import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Event Name cannot be empty")
    @Size(min = 3, max = 50, message = "Event name should be between 3 to 50 characters")
    private String name;

    @NotNull(message = "Event type is required")
    @Enumerated(EnumType.STRING)
    private EventTypes types;

    @NotEmpty(message = "Event description cannot be empty")
    @Size(min = 3, max = 500, message = "Event description should be between 3 to 500 characters")
    private String description;
    // user ID
    @NotNull(message = "user ID is required")
    private long userId;

    @Future(message = "Event date must be in the future")
    @Column(columnDefinition = "DATE")
    private LocalDate eventDate;

    @Enumerated(EnumType.STRING)
    private EventSessions eventSession;

    @NotNull(message = "Budget cannot be null")
    @Min(value = 10000, message = "Budget must be at least 10000")
    @Max(value = 1000000, message = "Budget must not exceed 1,000,000")
    private Long budget;

    @NotNull(message = "Guest count is required")
    @Min(value = 1, message = "Guest count must be greater than 0")
    @Max(value = 5000, message = "Guest count must not exceed 5000")
    private Integer guestCount;

    // exception for improper Vendor ID
    private Long vendorId;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    @JsonBackReference
    private Venue venue;

}
