package org.example.ubersocketserver.dto;


import lombok.*;
import org.example.uberprojectentityservice.Models.BookingStatus;
import org.example.uberprojectentityservice.Models.Driver;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingResponseDto {
     private Long bookingId;
     private BookingStatus status;
     private Optional<Driver> driver;
}
