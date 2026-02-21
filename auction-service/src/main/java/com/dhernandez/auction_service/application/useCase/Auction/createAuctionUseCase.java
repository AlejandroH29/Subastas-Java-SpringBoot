package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.command.createAuctionCommand;
import com.dhernandez.auction_service.application.result.createAuctionResult;

public interface createAuctionUseCase {
    public createAuctionResult createAuction(createAuctionCommand command);
}
