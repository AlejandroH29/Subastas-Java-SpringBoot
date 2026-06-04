package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.result.ActiveAuctionsResult;

public interface ListActiveAuctionsUseCase {
    public ActiveAuctionsResult listActiveAuctions();
}
