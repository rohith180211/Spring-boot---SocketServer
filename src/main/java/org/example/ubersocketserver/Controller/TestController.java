package org.example.ubersocketserver.Controller;


import org.example.ubersocketserver.dto.chatRequest;
import org.example.ubersocketserver.dto.chatResponse;
import org.example.ubersocketserver.dto.testRequest;
import org.example.ubersocketserver.dto.testResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;

    TestController(SimpMessagingTemplate messagingTemplate, SimpMessagingTemplate simpMessagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public testResponse pingCheck(testRequest message) {
        System.out.println("Received message from client "+ message.getData());
        return new testResponse("Received");
    }

//    @Scheduled(fixedDelay = 2000)
//    public void periodicMessage() {
//        simpMessagingTemplate.convertAndSend ("/topic/scheduled","Periodic message sent " + System.currentTimeMillis());
//    }
    @MessageMapping("/chat/{room}")
    @SendTo("/topic/message/{room}")
    public chatResponse chatMessage(@DestinationVariable String room,  chatRequest req) {
        return chatResponse.builder().name(req.getName()).message(req.getMessage()).time(""+ System.currentTimeMillis()).build();
    }

    @MessageMapping("/privateChat/{room}/{userId}")
    public void privateChatMessage(@DestinationVariable String room,  @DestinationVariable String userId,chatRequest req) {

        chatResponse response= chatResponse.builder().name(req.getName()).message(req.getMessage()).time(""+ System.currentTimeMillis()).build();
        simpMessagingTemplate.convertAndSendToUser (userId,"/queue/privateMessage/"+room,response);

    }

}
