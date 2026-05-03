package com.dhernandez.auction_service.application.port.out.Auction;

import com.dhernandez.auction_service.domain.model.Auction;

public interface FindAuctionByIdPort {
    public Auction findAuction(Long id);
}
