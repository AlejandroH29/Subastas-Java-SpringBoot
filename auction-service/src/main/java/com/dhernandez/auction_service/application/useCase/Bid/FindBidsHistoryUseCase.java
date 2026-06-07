package com.dhernandez.auction_service.application.useCase.Bid;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.result.BidData;

public interface FindBidsHistoryUseCase {
    public PageResult<BidData> findBidsHistory(Long auctionId, PageRequest pageRequest);
}
