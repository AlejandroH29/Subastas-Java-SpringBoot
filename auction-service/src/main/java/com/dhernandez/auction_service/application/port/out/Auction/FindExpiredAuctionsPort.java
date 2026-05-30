package com.dhernandez.auction_service.application.port.out.Auction;

import java.util.List;

import com.dhernandez.auction_service.domain.model.Auction;

public interface FindExpiredAuctionsPort {
    public List<Auction> findExpiredAuctions();
}
