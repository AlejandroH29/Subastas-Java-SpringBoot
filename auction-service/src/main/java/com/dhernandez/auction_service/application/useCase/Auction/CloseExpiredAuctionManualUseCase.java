package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.command.CloseAuctionCommand;
import com.dhernandez.auction_service.application.result.CloseAuctionResult;

public interface CloseExpiredAuctionManualUseCase {
    public CloseAuctionResult closeAuction(CloseAuctionCommand command, Long userId);
}
