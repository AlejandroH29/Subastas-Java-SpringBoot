package com.dhernandez.auction_service.application.port.out.Bid;

import java.util.List;

import com.dhernandez.auction_service.domain.model.Bid;

public interface FindAuctionHistoryBidsPort {
    public List<Bid> findAuctionHistoryBids(Long auctionId);
}
