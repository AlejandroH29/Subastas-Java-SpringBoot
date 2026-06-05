package com.dhernandez.auction_service.infrastructure.persistence.adapter.Bid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.Bid.FindAuctionHistoryBidsPort;
import com.dhernandez.auction_service.application.port.out.Bid.SaveBidPort;
import com.dhernandez.auction_service.domain.model.Bid;
import com.dhernandez.auction_service.infrastructure.persistence.BidJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.BidJpaRepository;

@Component
public class BidPersistanceAdapter implements SaveBidPort, FindAuctionHistoryBidsPort {
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

    @Override
    public List<Bid> findAuctionHistoryBids(Long auctionId) {
        List<BidJpaEntity> bidsFound = bidRepository.findByAuctionIdOrderByTimeStampDesc(auctionId);
        List<Bid> bidsHistory = new ArrayList<>();
        for(BidJpaEntity bid : bidsFound){
            Bid bidHitory = new Bid(bid.getId(), bid.getAuctionId(), bid.getUserId(), bid.getAmount(), bid.getTimeStamp());
            bidsHistory.add(bidHitory);
        }
        return bidsHistory;
    }
    
}
