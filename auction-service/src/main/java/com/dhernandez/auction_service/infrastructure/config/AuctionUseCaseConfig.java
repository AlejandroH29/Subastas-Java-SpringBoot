package com.dhernandez.auction_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.port.out.Auction.ExistAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.Auction.FindExpiredAuctionsPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
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
}
