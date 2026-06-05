package com.dhernandez.auction_service.application.useCase.Bid;

import com.dhernandez.auction_service.application.result.BidsHistoryResult;

public interface FindBidsHistoryUseCase {
    public BidsHistoryResult findBidsHistory(Long auctionId);
}
