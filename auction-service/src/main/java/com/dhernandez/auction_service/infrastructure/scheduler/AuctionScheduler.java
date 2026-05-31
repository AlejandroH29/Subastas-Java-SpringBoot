package com.dhernandez.auction_service.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.useCase.Auction.ActiveAuctionAutomaticUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionsUseCase;
@Component
public class AuctionScheduler {
    private final CloseExpiredAuctionsUseCase closeExpiredAuction;
    private final ActiveAuctionAutomaticUseCase activeAuctions;
    public AuctionScheduler(CloseExpiredAuctionsUseCase closeExpiredAuction, ActiveAuctionAutomaticUseCase activeAuctions){
        this.closeExpiredAuction = closeExpiredAuction;
        this.activeAuctions = activeAuctions;
    }
    @Scheduled(fixedRate = 5000)
    public void closeExpiredAuctions(){
        closeExpiredAuction.closeExpiredAuctions();
    }
    @Scheduled(fixedRate = 5000)
    public void activeAuctionsReady(){
        activeAuctions.activeAuctionAuctomatic();
    }
}
