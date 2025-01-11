package org.example.ubersocketserver.dto;


import lombok.*;
import org.example.ubersocketserver.models.ExactLocation;

import java.util.List;
import java.util.Optional;

@Getter

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class rideRequestDto {
    private Long passengerId;
   // private  ExactLocation startLocation;
   // private ExactLocation endLocation;
    private Optional<List<Long>> driverIds;
    private Long bookingId;
}
