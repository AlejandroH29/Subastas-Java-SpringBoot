package com.dhernandez.auction_service.infrastructure.webSocket.mapper;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.domain.event.AuctionClosed;
import com.dhernandez.auction_service.infrastructure.webSocket.payload.AuctionClosedEventPayload;

@Component
public class AuctionClosedEventMapper {
    public AuctionClosedEventPayload map(AuctionClosed auctionClosed){
        return new AuctionClosedEventPayload("AUCTION_CLOSED", 
                                                auctionClosed.getAuctionId(), 
                                                auctionClosed.getEndTime(), 
                                                auctionClosed.getWinnerId(), 
                                                auctionClosed.getFinalPrice());
    }
}
