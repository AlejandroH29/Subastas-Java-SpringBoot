package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.command.CreateAuctionCommand;
import com.dhernandez.auction_service.application.result.CreateAuctionResult;

public interface CreateAuctionUseCase {
    public CreateAuctionResult createAuction(CreateAuctionCommand auctionCommand);
}
