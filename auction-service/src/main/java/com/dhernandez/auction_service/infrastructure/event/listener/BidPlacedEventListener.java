package com.dhernandez.auction_service.infrastructure.event.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dhernandez.auction_service.domain.event.BidPlaced;
import com.dhernandez.auction_service.infrastructure.webSocket.mapper.BidPlacedEventMapper;
import com.dhernandez.auction_service.infrastructure.webSocket.payload.BidPlacedEventPayload;
import com.dhernandez.auction_service.infrastructure.webSocket.sender.WebSocketAuctionSender;

@Component
public class BidPlacedEventListener {
    private final BidPlacedEventMapper bidPlacedEventMapper;
    private final WebSocketAuctionSender webSocketAuctionSender;
    public BidPlacedEventListener(BidPlacedEventMapper bidPlacedEventMapper, WebSocketAuctionSender webSocketAuctionSender){
        this.bidPlacedEventMapper = bidPlacedEventMapper;
        this.webSocketAuctionSender = webSocketAuctionSender;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(BidPlaced bidPlaced){
        BidPlacedEventPayload bidPayLoad = bidPlacedEventMapper.map(bidPlaced);
        try {
            webSocketAuctionSender.sendAuctionUpdate(bidPlaced.getAuctionId(), bidPayLoad);;
        } catch (Exception e) {
            System.err.println("Fallo la conexion webSocket con la subasta: " + bidPlaced.getAuctionId() );
        }
    }
}
