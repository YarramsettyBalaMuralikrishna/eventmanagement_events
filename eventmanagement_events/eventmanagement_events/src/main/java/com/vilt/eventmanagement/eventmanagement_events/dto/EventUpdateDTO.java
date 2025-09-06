package com.vilt.eventmanagement.eventmanagement_events.dto;
import com.vilt.eventmanagement.eventmanagement_events.Enums.EventSessions;
import com.vilt.eventmanagement.eventmanagement_events.Enums.EventTypes;
import com.vilt.eventmanagement.eventmanagement_events.entity.Events;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDTO {
    @NotNull(message = "Event ID is required for update")
    private Long id;

    // Optional fields: user may provide any subset
    @Size(min = 3, max = 50, message = "Event name should be between 3 to 50 characters")
    private String name;

    private EventTypes types;

    @Size(min = 3, max = 500, message = "Event description should be between 3 to 500 characters")
    private String description;

    @Future(message = "Event date must be in the future")
    private LocalDate eventDate;

    private EventSessions eventSession;

    @Min(value = 10000, message = "Budget must be at least 10000")
    @Max(value = 1000000, message = "Budget must not exceed 1,000,000")
    private Long budget;

    @Min(value = 1, message = "Guest count must be greater than 0")
    @Max(value = 5000, message = "Guest count must not exceed 5000")
    private Integer guestCount;

    private Long vendorId;

    private Long venueId;

    // Helper method to check if at least one field is provided
    public boolean hasAtLeastOneFieldToUpdate() {
        return name != null || types != null || description != null || eventDate != null
                || eventSession != null || budget != null || guestCount != null
                || vendorId != null || venueId != null;
    }
    public EventUpdateDTO(Events event) {
        this.id = event.getId();
        this.name = event.getName();
        this.types = event.getTypes();
        this.description = event.getDescription();
        this.eventDate = event.getEventDate();
        this.eventSession = event.getEventSession();
        this.budget = event.getBudget();
        this.guestCount = event.getGuestCount();
        this.vendorId = event.getVendorId();
        this.venueId = event.getVenue() != null ? event.getVenue().getId() : null;
    }
}
