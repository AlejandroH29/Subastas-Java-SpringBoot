package com.dhernandez.auction_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.port.out.Bid.FindAuctionHistoryBidsPort;
import com.dhernandez.auction_service.application.port.out.Bid.SaveBidPort;
import com.dhernandez.auction_service.application.port.out.EventPublisher.EventPublisherPort;
import com.dhernandez.auction_service.application.useCase.Bid.FindBidsHistoryUseCase;
import com.dhernandez.auction_service.application.useCase.Bid.FindBidsHistoryUseCaseImp;
import com.dhernandez.auction_service.application.useCase.Bid.PlaceBidUseCase;
import com.dhernandez.auction_service.application.useCase.Bid.PlaceBidUseCaseImp;

@Component
public class BidUseCaseConfig {
    @Bean
    public PlaceBidUseCase createBidUseCase(SaveAuctionPort saveAuction,SaveBidPort saveBid,FindAuctionByIdPort findAuctionById, EventPublisherPort eventPublisherPort){
        return new PlaceBidUseCaseImp( saveAuction, saveBid, findAuctionById, eventPublisherPort);
    }
    @Bean
    public FindBidsHistoryUseCase findBidsHistoryUseCase(FindAuctionHistoryBidsPort findAuctionHistoryPort, FindAuctionByIdPort findAuctionByIdPort){
        return new FindBidsHistoryUseCaseImp(findAuctionHistoryPort, findAuctionByIdPort);
    }
}
