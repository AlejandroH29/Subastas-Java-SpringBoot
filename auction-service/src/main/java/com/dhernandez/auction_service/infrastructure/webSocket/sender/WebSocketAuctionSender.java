package com.dhernandez.auction_service.infrastructure.webSocket.sender;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.infrastructure.webSocket.payload.PayLoadEvent;

@Component
public class WebSocketAuctionSender {
    private final SimpMessagingTemplate simpMessagingTemplate;
    public WebSocketAuctionSender(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendAuctionUpdate(Long auctionId, PayLoadEvent payLoad){
        simpMessagingTemplate.convertAndSend("/topic/auction/" + auctionId, payLoad);
    }
}
