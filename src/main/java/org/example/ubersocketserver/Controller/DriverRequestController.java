package org.example.ubersocketserver.Controller;



import org.example.ubersocketserver.Producers.KafkaProducerService;
import org.example.ubersocketserver.dto.UpdateBookingRequestDto;
import org.example.ubersocketserver.dto.UpdateBookingResponseDto;
import org.example.ubersocketserver.dto.rideRequestDto;
import org.example.ubersocketserver.dto.rideResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/socket")
public class DriverRequestController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RestTemplate restTemplate;

    private final KafkaProducerService kafkaProducerService;


    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate, KafkaProducerService kafkaProducerService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.restTemplate = new RestTemplate();
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping
    public Boolean help() {
        kafkaProducerService.publishMessage("sample-topic", "Hello");
        return true;
    }

    @PostMapping ("/newride")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody rideRequestDto requestDto) {
        System.out.println("request for rides received");
        sendDriversNewRideRequest(requestDto);
        System.out.println("Req completed");
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    public void sendDriversNewRideRequest(rideRequestDto requestDto) {
        System.out.println("Executed periodic function");
        // TODO: Ideally the request should only go to nearby drivers, but for simplicity we send it everyone
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", requestDto);
    }

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideResponseHandler(@DestinationVariable String userId, rideResponseDto rideResponseDto) {

        System.out.println(rideResponseDto.getResponse() +" "+userId);
        UpdateBookingRequestDto requestDto = UpdateBookingRequestDto.builder()
                .driverId(Optional.of(Long.parseLong(userId)))
                .bookingStatus("SCHEDULED")
                .build();
        ResponseEntity<UpdateBookingResponseDto> result = this.restTemplate.postForEntity("http://localhost:8001/api/v1/booking/" + rideResponseDto.bookingId, requestDto, UpdateBookingResponseDto.class);
        kafkaProducerService.publishMessage("sample-topic", "Hello");
        System.out.println(result.getStatusCode());
    }
}