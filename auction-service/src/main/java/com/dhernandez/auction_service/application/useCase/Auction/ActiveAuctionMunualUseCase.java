package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.command.ActiveAuctionCommand;
import com.dhernandez.auction_service.application.result.ActiveAuctionResult;

public interface ActiveAuctionMunualUseCase {
    public ActiveAuctionResult activeAuction(ActiveAuctionCommand command, Long userId);
}
