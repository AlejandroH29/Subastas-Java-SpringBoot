package com.dhernandez.auction_service.application.useCase.Bid;


import java.util.ArrayList;
import java.util.List;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Bid.FindAuctionHistoryBidsPort;
import com.dhernandez.auction_service.application.result.BidData;
import com.dhernandez.auction_service.application.useCase.Exception.NoAuctionFound;
import com.dhernandez.auction_service.domain.model.Bid;

public class FindBidsHistoryUseCaseImp implements FindBidsHistoryUseCase {

    private final FindAuctionHistoryBidsPort findAuctionHistoryPort;
    private final FindAuctionByIdPort findAuctionByIdPort;
    public FindBidsHistoryUseCaseImp(FindAuctionHistoryBidsPort findAuctionHistoryPort, FindAuctionByIdPort findAuctionByIdPort){
        this.findAuctionHistoryPort = findAuctionHistoryPort;
        this.findAuctionByIdPort = findAuctionByIdPort;
    }

    @Override
    public PageResult<BidData> findBidsHistory(Long auctionId, PageRequest pageRequest) {
        if(findAuctionByIdPort.findAuction(auctionId) == null){
            throw new NoAuctionFound("No se encontro la subasta o no existe");
        }
        PageResult<Bid> bidsHistoryFound = findAuctionHistoryPort.findAuctionHistoryBids(auctionId, pageRequest);
        List<BidData> bidsHistory = new ArrayList<>();
        for(Bid bid : bidsHistoryFound.getData()){
            BidData bidData = new BidData(bid.getId(), bid.getUserId(), bid.getAmount(), bid.getTimeStamp());
            bidsHistory.add(bidData);
        }
        return new PageResult<BidData>(bidsHistory, 
                                        bidsHistoryFound.getPage(), 
                                        bidsHistoryFound.getPageSize(), 
                                        bidsHistoryFound.getTotalItems(), 
                                        bidsHistoryFound.getTotalPages());
    }
    
}
