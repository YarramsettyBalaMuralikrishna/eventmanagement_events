package com.vilt.eventmanagement.eventmanagement_events.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vilt.eventmanagement.eventmanagement_events.Enums.Locations;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"events"})
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Venue name cannot be blank")
    @Size(min = 3, max = 100, message = "Venue name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Location is required")
    @Enumerated(EnumType.STRING) // safer than ORDINAL
    private Locations location;

    @Email(message = "Email should be in a valid format (e.g., user@example.com)")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String address;

    @NotNull(message = "Max capacity is required")
    @Min(value = 10000, message = "Capacity must be at least 10000")
    @Max(value = 100000, message = "Capacity cannot exceed 100,000")
    private Long pricePerSession;

    @NotNull(message = "Max capacity is required")
    @Min(value = 10, message = "Capacity must be at least 10")
    @Max(value = 100000, message = "Capacity cannot exceed 100,000")
    private Integer maxCapacity;

    @OneToMany(mappedBy = "venue", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JsonManagedReference
//    @JsonIgnoreProperties({"events"})
    private List<Events> events;
    // getters and setters
}
