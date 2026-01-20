package com.dhernandez.auction_service.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.port.out.existAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.saveAuctionPort;
import com.dhernandez.auction_service.application.useCase.createAuctionUseCase;
import com.dhernandez.auction_service.application.useCase.createAuctionUseCaseImp;

@Configuration
public class AuctionUseCaseConfig {
    @Bean
    public createAuctionUseCase createAuctionUseCase(existAuctionByTitlePort existAuction, saveAuctionPort saveAuction){
        return new createAuctionUseCaseImp(existAuction, saveAuction);
    }
}
