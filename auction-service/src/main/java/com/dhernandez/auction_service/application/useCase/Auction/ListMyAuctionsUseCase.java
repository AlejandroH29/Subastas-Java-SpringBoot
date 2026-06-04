package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.result.MyAuctionsResult;

public interface ListMyAuctionsUseCase {
    public MyAuctionsResult listOfMyAuctions(Long userId);
}
