package com.vilt.eventmanagement.eventmanagement_events.dto;

import com.vilt.eventmanagement.eventmanagement_events.Enums.Locations;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueUpdateDTO {

    private Long id; // Required for identifying which venue to update

    @Size(min = 3, max = 100, message = "Venue name must be between 3 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private Locations location; // Enum, automatically validated

    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String address;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format (e.g., user@example.com)")
    private String email;

    @Min(value = 10000, message = "Price per session must be at least 10000")
    @Max(value = 100000, message = "Price per session cannot exceed 100,000")
    private Long pricePerSession;

    @Min(value = 10, message = "Capacity must be at least 10")
    @Max(value = 100000, message = "Capacity cannot exceed 100,000")
    private Integer maxCapacity;
}