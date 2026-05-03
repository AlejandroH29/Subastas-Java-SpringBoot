package com.dhernandez.auction_service.application.port.out.Bid;

import com.dhernandez.auction_service.domain.model.Bid;

public interface SaveBidPort {
    public Bid saveBid(Bid bid);
}
