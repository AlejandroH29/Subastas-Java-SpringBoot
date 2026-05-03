package com.dhernandez.auction_service.infrastructure.persistence.adapter.Auction;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.Auction.ExistAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;
import com.dhernandez.auction_service.domain.model.Auction;
import com.dhernandez.auction_service.domain.model.Enum.EnumAuction;
import com.dhernandez.auction_service.infrastructure.persistence.AuctionJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.AuctionJpaRepository;

@Component 
public class AuctionPersistanceAdapter implements ExistAuctionByTitlePort, SaveAuctionPort, FindAuctionByIdPort{

    private final AuctionJpaRepository auctionRepository;
    public AuctionPersistanceAdapter( AuctionJpaRepository auctionRepository){
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction saveAuction(Auction auction) {
        if(auction.getIdAuction() == null){
            AuctionJpaEntity auctionEntity = new AuctionJpaEntity(auction.getTitle(), 
                                                                    auction.getDescription(), 
                                                                    auction.getStartTime(), 
                                                                    auction.getEndTime(), 
                                                                    auction.getStatus().toString(), 
                                                                    auction.getStartingPrice(), 
                                                                    auction.getOwnerId());
            AuctionJpaEntity savedAuction = auctionRepository.save(auctionEntity);
            return new Auction(savedAuction.getIdAuction(), 
                                savedAuction.getTitle(), 
                                savedAuction.getDescription(), 
                                savedAuction.getStartTime(), 
                                savedAuction.getEndTime(), 
                                EnumAuction.valueOf(savedAuction.getStatus()), 
                                savedAuction.getStartingPrice(), 
                                savedAuction.getCurrentPrice(), 
                                savedAuction.getOwnerId(), 
                                savedAuction.getWinnerId());
        } else{
            AuctionJpaEntity auctionFound = findAuctionJpa(auction.getIdAuction());
            if(auctionFound == null){
                throw new ErrorCreatingAuction("No se encontro la subasta en la base de datos");
            }
            auctionFound.setCurrentPrice(auction.getCurrentPrice());
            auctionFound.setWinnerId(auction.getWinnerId());
            AuctionJpaEntity savedAuction = auctionRepository.save(auctionFound);
            return new Auction(savedAuction.getIdAuction(), 
                                savedAuction.getTitle(), 
                                savedAuction.getDescription(), 
                                savedAuction.getStartTime(), 
                                savedAuction.getEndTime(), 
                                EnumAuction.valueOf(savedAuction.getStatus()), 
                                savedAuction.getStartingPrice(), 
                                savedAuction.getCurrentPrice(), 
                                savedAuction.getOwnerId(), 
                                savedAuction.getWinnerId());
        }
    }

    @Override
    public boolean existAuction(String title) {
        boolean verification = auctionRepository.existsByTitle(title);
        if(verification){
            throw new ErrorCreatingAuction("Ya existe una subasta con este titulo");
        }
        return verification;
    }

    @Override
    public Auction findAuction(Long id) {
        AuctionJpaEntity auctionFound = findAuctionJpa(id);
        if(auctionFound == null){
            return null;
        }
        return new Auction(auctionFound.getIdAuction(), 
                            auctionFound.getTitle(), 
                            auctionFound.getDescription(),
                            auctionFound.getStartTime(),
                            auctionFound.getEndTime(),
                            EnumAuction.valueOf(auctionFound.getStatus()),
                            auctionFound.getStartingPrice(),
                            auctionFound.getCurrentPrice(),
                            auctionFound.getOwnerId(),
                            auctionFound.getWinnerId()
                        );
    }

    public AuctionJpaEntity findAuctionJpa(Long id){
        AuctionJpaEntity auction = auctionRepository.findById(id).orElse(null);
        return auction;
    }
    
}
