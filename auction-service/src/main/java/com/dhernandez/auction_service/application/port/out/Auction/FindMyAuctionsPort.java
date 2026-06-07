package com.dhernandez.auction_service.application.port.out.Auction;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.domain.model.Auction;

public interface FindMyAuctionsPort {
    public PageResult<Auction> findMyAuctions(Long userId, PageRequest pageRequest);
}
