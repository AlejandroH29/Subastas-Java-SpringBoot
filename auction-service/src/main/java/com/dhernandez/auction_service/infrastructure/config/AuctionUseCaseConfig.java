package com.dhernandez.auction_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.port.out.Auction.ExistAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionsReadyToActivatePort;
import com.dhernandez.auction_service.application.port.out.Auction.FindExpiredAuctionsPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.useCase.Auction.ActiveAuctionAutomaticUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.ActiveAuctionAutomaticUseCaseImp;
import com.dhernandez.auction_service.application.useCase.Auction.ActiveAuctionManualUseCaseImp;
import com.dhernandez.auction_service.application.useCase.Auction.ActiveAuctionMunualUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionManualUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionManualUseCaseImp;
import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionsUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionsUseCaseImp;
import com.dhernandez.auction_service.application.useCase.Auction.CreateAuctionUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CreateAuctionUseCaseImp;

@Configuration
public class AuctionUseCaseConfig {
    @Bean
    public CreateAuctionUseCase createAuctionUseCase(ExistAuctionByTitlePort existAuction, SaveAuctionPort saveAuction){
        return new CreateAuctionUseCaseImp(existAuction, saveAuction);
    }
    @Bean
    public CloseExpiredAuctionsUseCase closeExpiredAuctionsUseCase(FindExpiredAuctionsPort findAuctionsPort, SaveAuctionPort saveAuctions){
        return new CloseExpiredAuctionsUseCaseImp(findAuctionsPort, saveAuctions);
    }

    @Bean
    public CloseExpiredAuctionManualUseCase closeExpiredAuctionManualUseCase(FindAuctionByIdPort findAuctionById, SaveAuctionPort saveAuction){
        return new CloseExpiredAuctionManualUseCaseImp(findAuctionById, saveAuction);
    }
    
    @Bean
    public ActiveAuctionMunualUseCase activeAuctionUseCase(FindAuctionByIdPort findAuctionById, SaveAuctionPort saveAuction){
        return new ActiveAuctionManualUseCaseImp(findAuctionById, saveAuction);
    }

    @Bean
    public ActiveAuctionAutomaticUseCase activeAuctionAutomaticUseCase(FindAuctionsReadyToActivatePort auctionsToActivatePort, SaveAuctionPort saveAuctions){
        return new ActiveAuctionAutomaticUseCaseImp(auctionsToActivatePort, saveAuctions);
    }
}
