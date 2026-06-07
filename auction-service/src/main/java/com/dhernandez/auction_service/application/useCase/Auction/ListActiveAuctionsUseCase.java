package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.result.AuctionStatusActive;

public interface ListActiveAuctionsUseCase {
    public PageResult<AuctionStatusActive> listActiveAuctions(PageRequest pageRequest);
}
