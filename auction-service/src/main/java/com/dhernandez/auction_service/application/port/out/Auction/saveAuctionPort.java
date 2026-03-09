package com.dhernandez.auction_service.application.port.out.Auction;

import com.dhernandez.auction_service.domain.model.Auction;

public interface SaveAuctionPort {
    public Auction saveAuction(Auction auction);
}
