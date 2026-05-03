package com.dhernandez.auction_service.application.useCase.Bid;

import com.dhernandez.auction_service.application.command.CreateBidCommand;
import com.dhernandez.auction_service.application.result.CreateBidResult;

public interface PlaceBidUseCase {
    public CreateBidResult placeBid(CreateBidCommand bidCommand, Long userId);
}
