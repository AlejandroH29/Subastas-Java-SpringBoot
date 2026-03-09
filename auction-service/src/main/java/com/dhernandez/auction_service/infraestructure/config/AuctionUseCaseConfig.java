package com.dhernandez.auction_service.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.port.out.Auction.ExistAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.useCase.Auction.CreateAuctionUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CreateAuctionUseCaseImp;

@Configuration
public class AuctionUseCaseConfig {
    @Bean
    public CreateAuctionUseCase createAuctionUseCase(ExistAuctionByTitlePort existAuction, SaveAuctionPort saveAuction){
        return new CreateAuctionUseCaseImp(existAuction, saveAuction);
    }
}
