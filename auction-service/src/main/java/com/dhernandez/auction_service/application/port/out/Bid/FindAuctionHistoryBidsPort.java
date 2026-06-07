package com.dhernandez.auction_service.application.port.out.Bid;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.domain.model.Bid;

public interface FindAuctionHistoryBidsPort {
    public PageResult<Bid> findAuctionHistoryBids(Long auctionId, PageRequest pageRequest);
}
