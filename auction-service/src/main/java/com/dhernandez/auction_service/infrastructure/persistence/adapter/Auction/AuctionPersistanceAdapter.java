package com.dhernandez.auction_service.infrastructure.persistence.adapter.Auction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.port.out.Auction.ExistAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByStatusPort;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionsReadyToActivatePort;
import com.dhernandez.auction_service.application.port.out.Auction.FindExpiredAuctionsPort;
import com.dhernandez.auction_service.application.port.out.Auction.FindMyAuctionsPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;
import com.dhernandez.auction_service.domain.model.Auction;
import com.dhernandez.auction_service.domain.model.Enum.EnumAuction;
import com.dhernandez.auction_service.infrastructure.persistence.AuctionJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.AuctionJpaRepository;

@Component 
public class AuctionPersistanceAdapter implements ExistAuctionByTitlePort, SaveAuctionPort, FindAuctionByIdPort, FindExpiredAuctionsPort, FindAuctionsReadyToActivatePort, FindAuctionByStatusPort, FindMyAuctionsPort{

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
            auctionFound.setStatus(auction.getStatus().toString());
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

    @Override
    public PageResult<Auction> findExpiredAuctions(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), 
                                                                            pageRequest.getSize(),
                                                                            Sort.by("endTime").ascending()
                                                                                .and(Sort.by("idAuction").ascending()));
        List<Auction> auctions = new ArrayList<>();
        Page<AuctionJpaEntity> auctionsFound = auctionRepository.
                                                    findByStatusAndEndTimeLessThanEqual(
                                                        "ACTIVE", 
                                                        LocalDateTime.now(),
                                                        pageable);
                                                        
        for(AuctionJpaEntity auction : auctionsFound.getContent()){
            Auction auctionDomain = new Auction(auction.getIdAuction(), 
                                                    auction.getTitle(), 
                                                    auction.getDescription(), 
                                                    auction.getStartTime(), 
                                                    auction.getEndTime(), 
                                                    EnumAuction.valueOf(auction.getStatus().toString()), 
                                                    auction.getStartingPrice(), 
                                                    auction.getCurrentPrice(), 
                                                    auction.getOwnerId(), 
                                                    auction.getWinnerId());
            auctions.add(auctionDomain);
        }
        return new PageResult<Auction>(auctions, 
                                        auctionsFound.getNumber(), 
                                        auctionsFound.getSize(), 
                                        (int) auctionsFound.getTotalElements(), 
                                        auctionsFound.getTotalPages());
    }

    public AuctionJpaEntity findAuctionJpa(Long id){
        AuctionJpaEntity auction = auctionRepository.findById(id).orElse(null);
        return auction;
    }

    @Override
    public PageResult<Auction> findAuctiosToActivate(PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), 
                                                                            pageRequest.getSize(),
                                                                            Sort.by("startTime").ascending()
                                                                                .and(Sort.by("idAuction").ascending()));
        List<Auction> auctions = new ArrayList<>();
        Page<AuctionJpaEntity> pagination = auctionRepository.findAuctionsReadyToActivate(LocalDateTime.now(), pageable);
        for(AuctionJpaEntity auction : pagination.getContent()){
            Auction auctionDomain = new Auction(auction.getIdAuction(), 
                                                    auction.getTitle(), 
                                                    auction.getDescription(), 
                                                    auction.getStartTime(), 
                                                    auction.getEndTime(), 
                                                    EnumAuction.valueOf(auction.getStatus().toString()), 
                                                    auction.getStartingPrice(), 
                                                    auction.getCurrentPrice(), 
                                                    auction.getOwnerId(), 
                                                    auction.getWinnerId());
            auctions.add(auctionDomain);
        }
        return new PageResult<Auction>(auctions, 
                                        pagination.getNumber(), 
                                        pagination.getSize(), 
                                        (int) pagination.getTotalElements(), 
                                        pagination.getTotalPages());
    }

    @Override
    public PageResult<Auction> findAuctionsByStatus(String status, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(),
                                                                            pageRequest.getSize(), 
                                                                            Sort.by("endTime").ascending()
                                                                                .and(Sort.by("idAuction").ascending()));
        List<Auction> activeAuctions = new ArrayList<>();
        Page<AuctionJpaEntity> pagination = auctionRepository.findByStatus(status, pageable);
        for(AuctionJpaEntity auction: pagination.getContent()){
            Auction auctionFound = new Auction(auction.getIdAuction(), 
                                                auction.getTitle(), 
                                                auction.getDescription(), 
                                                auction.getStartTime(), 
                                                auction.getEndTime(), 
                                                EnumAuction.valueOf(auction.getStatus()), 
                                                auction.getStartingPrice(), 
                                                auction.getCurrentPrice(), 
                                                auction.getOwnerId(), 
                                                auction.getWinnerId());
            activeAuctions.add(auctionFound);
        }
        return new PageResult<Auction>(activeAuctions, 
                                        pagination.getNumber(), 
                                        pagination.getSize(), 
                                        (int) pagination.getTotalElements(), 
                                        pagination.getTotalPages());
    }

    @Override
    public PageResult<Auction> findMyAuctions(Long userId, PageRequest pageRequest) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), 
                                                                            pageRequest.getSize(),
                                                                            Sort.by("startTime").descending()
                                                                                .and(Sort.by("idAuction").descending()));
        Page<AuctionJpaEntity> pagination = auctionRepository.findByOwnerId(userId, pageable);
        List<Auction> myAuctions = new ArrayList<>();
        for(AuctionJpaEntity auction : pagination.getContent()){
            Auction myAuction = new Auction(auction.getIdAuction(), 
                                                auction.getTitle(), 
                                                auction.getDescription(), 
                                                auction.getStartTime(), 
                                                auction.getEndTime(), 
                                                EnumAuction.valueOf(auction.getStatus()), 
                                                auction.getStartingPrice(), 
                                                auction.getCurrentPrice(), 
                                                auction.getOwnerId(), 
                                                auction.getWinnerId());
            myAuctions.add(myAuction);
        }
        return new PageResult<Auction>(myAuctions, pagination.getNumber(), pagination.getSize(), (int) pagination.getTotalElements(), pagination.getTotalPages());
    } 
    
}
