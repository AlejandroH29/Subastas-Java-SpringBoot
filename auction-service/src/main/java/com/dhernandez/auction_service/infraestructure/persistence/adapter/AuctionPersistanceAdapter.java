package com.dhernandez.auction_service.infraestructure.persistence.adapter;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.existAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.saveAuctionPort;
import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;
import com.dhernandez.auction_service.domain.model.Auction;
import com.dhernandez.auction_service.domain.model.EnumAuction;
import com.dhernandez.auction_service.infraestructure.persistence.AuctionJpaEntity;
import com.dhernandez.auction_service.infraestructure.persistence.repository.AuctionJpaRepository;

@Component 
public class AuctionPersistanceAdapter implements existAuctionByTitlePort, saveAuctionPort{

    private final AuctionJpaRepository auctionRepository;
    public AuctionPersistanceAdapter( AuctionJpaRepository auctionRepository){
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction saveAuction(Auction auction) {
        AuctionJpaEntity auctionEntity = new AuctionJpaEntity(auction.getTitle(), auction.getDescription(), auction.getStartTime(), auction.getEndTime(), auction.getStatus().toString(), auction.getStartingPrice(), auction.getOwnerId());
        AuctionJpaEntity savedAuction = auctionRepository.save(auctionEntity);
        return new Auction(savedAuction.getIdAuction().toString(), savedAuction.getTitle(), savedAuction.getDescription(), savedAuction.getStartTime(), savedAuction.getEndTime(), EnumAuction.valueOf(savedAuction.getStatus()), savedAuction.getStartingPrice(), savedAuction.getCurrentPrice(), savedAuction.getOwnerId(), savedAuction.getWinnerId());
    }

    @Override
    public boolean existAuction(String title) {
        boolean verification = auctionRepository.existByTitle(title);
        if(verification){
            throw new ErrorCreatingAuction("Ya existe una subasta con este titulo");
        }
        return verification;
    }
    
}
