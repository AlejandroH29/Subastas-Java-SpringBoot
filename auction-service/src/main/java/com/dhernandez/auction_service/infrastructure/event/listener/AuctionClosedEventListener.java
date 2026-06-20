package com.dhernandez.auction_service.infrastructure.event.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dhernandez.auction_service.domain.event.AuctionClosed;
import com.dhernandez.auction_service.infrastructure.webSocket.mapper.AuctionClosedEventMapper;
import com.dhernandez.auction_service.infrastructure.webSocket.payload.AuctionClosedEventPayload;
import com.dhernandez.auction_service.infrastructure.webSocket.sender.WebSocketAuctionSender;

@Component
public class AuctionClosedEventListener {

    private final AuctionClosedEventMapper auctionClosedEventMapper;
    private final WebSocketAuctionSender webSocketAuctionSender;
    public AuctionClosedEventListener(AuctionClosedEventMapper auctionClosedEventMapper, WebSocketAuctionSender webSocketAuctionSender){
        this.auctionClosedEventMapper = auctionClosedEventMapper;
        this.webSocketAuctionSender = webSocketAuctionSender;
    }
    
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(AuctionClosed auctionClosed){
        AuctionClosedEventPayload payLoad = auctionClosedEventMapper.map(auctionClosed);
        try {
            webSocketAuctionSender.sendAuctionUpdate(auctionClosed.getAuctionId(), payLoad);
        } catch (Exception e) {
            System.err.println("Fallo la conexion webSocket con la subasta: " + auctionClosed.getAuctionId() );
        }
    }
}
