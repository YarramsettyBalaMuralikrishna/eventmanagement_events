use inClass;
-- CREATE TABLE Venue (
--     idVenue        INT PRIMARY KEY AUTO_INCREMENT,
--     capacity       INT,                     
--     description    VARCHAR(255),
--     fee            DECIMAL(10,2),   
--     venueCol       VARCHAR(45),
--     maxNoOfEvents  INT                      
-- );
-- CREATE TABLE Events (
--     eventID     INT PRIMARY KEY AUTO_INCREMENT,
--     eventName   VARCHAR(100),
--     eventType   ENUM('WEDDING', 'MARRIAGE', 'DJ-NIGHT'),
--     description VARCHAR(255),
--     location    VARCHAR(100),

--     venueId     INT,
--     userId      VARCHAR(45),   
--     paymentID   VARCHAR(45),   

--     eventDate   DATE,
--     startTime   TIME,          
--     endTime     TIME,          

--     budget      DECIMAL(12,2), 

--     CONSTRAINT fk_events_venue
--         FOREIGN KEY (venueId) REFERENCES Venue(idVenue)
--         ON UPDATE CASCADE
--         ON DELETE RESTRICT
-- );

-- CREATE INDEX idx_events_date   ON Events(eventDate); 
-- CREATE INDEX idx_events_venue  ON Events(venueId);

-- CREATE TABLE Event_Vendor (
--     idEvent_Vendor INT PRIMARY KEY AUTO_INCREMENT,
--     status         ENUM('PENDING', 'COMPLETED', 'REJECTED', 'APPROVED'),
--     vendorID       VARCHAR(45),  
--     eventID        INT NOT NULL,

--     CONSTRAINT fk_event_vendor_event
--         FOREIGN KEY (eventID) REFERENCES Events(eventID)
--         ON UPDATE CASCADE
--         ON DELETE CASCADE
-- ) ENGINE=InnoDB;

-- -- Helpful indexes
-- CREATE INDEX idx_ev_status  ON Event_Vendor(status);
-- CREATE INDEX idx_ev_vendor  ON Event_Vendor(vendorID);