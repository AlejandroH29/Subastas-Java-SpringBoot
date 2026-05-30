package com.dhernandez.auction_service.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionsUseCase;
@Component
public class AuctionScheduler {
    private final CloseExpiredAuctionsUseCase closeExpiredAuction;
    public AuctionScheduler(CloseExpiredAuctionsUseCase closeExpiredAuction){
        this.closeExpiredAuction = closeExpiredAuction;
    }
    @Scheduled(fixedRate = 5000)
    public void closeExpiredAuctions(){
        closeExpiredAuction.closeExpiredAuctions();
    }
}
