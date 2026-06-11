package com.dhernandez.auction_service.infrastructure.webSocket.mapper;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.domain.event.BidPlaced;
import com.dhernandez.auction_service.infrastructure.webSocket.payload.BidPlacedEventPayload;

@Component
public class BidPlacedEventMapper {
    public BidPlacedEventPayload map(BidPlaced bidPlaced){
        return new BidPlacedEventPayload("BID_PLACED", 
                                            bidPlaced.getAuctionId(), 
                                            bidPlaced.getAmount(), 
                                            bidPlaced.getUserId(), 
                                            bidPlaced.getTimeStamp());
    }
}
