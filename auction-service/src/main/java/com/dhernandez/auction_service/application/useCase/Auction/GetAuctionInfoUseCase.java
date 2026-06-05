package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.result.AuctionInfoResult;

public interface GetAuctionInfoUseCase {
    public AuctionInfoResult getAuctionInfo(Long auctionId);
}
