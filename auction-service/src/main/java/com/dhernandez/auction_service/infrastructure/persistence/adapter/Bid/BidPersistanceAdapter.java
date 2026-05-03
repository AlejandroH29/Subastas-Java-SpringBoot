package com.dhernandez.auction_service.infrastructure.persistence.adapter.Bid;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.Bid.SaveBidPort;
import com.dhernandez.auction_service.domain.model.Bid;
import com.dhernandez.auction_service.infrastructure.persistence.BidJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.BidJpaRepository;

@Component
public class BidPersistanceAdapter implements SaveBidPort {
    private BidJpaRepository bidRepository;

    public BidPersistanceAdapter(BidJpaRepository bidRepository){
        this.bidRepository = bidRepository;
    }

    @Override
    public Bid saveBid(Bid bid) {
        BidJpaEntity bidEntity = new BidJpaEntity(bid.getAuctionId(), bid.getUserId(), bid.getAmount(), bid.getTimeStamp());
        BidJpaEntity bidSaved = bidRepository.save(bidEntity);
        return new Bid(bidSaved.getId(), bidSaved.getAuctionId(), bidSaved.getUserId(), bidSaved.getAmount(), bidSaved.getTimeStamp());
    }
    
}
