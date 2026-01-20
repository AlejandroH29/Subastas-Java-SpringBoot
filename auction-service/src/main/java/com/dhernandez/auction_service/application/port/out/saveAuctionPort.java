package com.dhernandez.auction_service.application.port.out;

import com.dhernandez.auction_service.domain.model.Auction;

public interface saveAuctionPort {
    public Auction saveAuction(Auction auction);
}
