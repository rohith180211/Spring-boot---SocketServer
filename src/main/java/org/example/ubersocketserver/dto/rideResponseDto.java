package org.example.ubersocketserver.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class rideResponseDto {
    public Boolean response;
    public Long bookingId;
}
