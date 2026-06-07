package com.dhernandez.auction_service.application.useCase.Auction;

import java.util.ArrayList;
import java.util.List;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByStatusPort;
import com.dhernandez.auction_service.application.result.AuctionStatusActive;
import com.dhernandez.auction_service.domain.model.Auction;

public class ListActiveAuctionUseCaseImp implements ListActiveAuctionsUseCase {
    private final FindAuctionByStatusPort findAuctionByStatusPort;
    public ListActiveAuctionUseCaseImp(FindAuctionByStatusPort findAuctionByStatusPort){
        this.findAuctionByStatusPort = findAuctionByStatusPort;
    }

    @Override
    public PageResult<AuctionStatusActive> listActiveAuctions(PageRequest pageRequest) {
        PageResult<Auction> activeAuctionsFound = findAuctionByStatusPort.findAuctionsByStatus("ACTIVE", pageRequest);
        List<AuctionStatusActive> auctionsActive = new ArrayList<>();
        for(Auction auction: activeAuctionsFound.getData()){
            AuctionStatusActive auctionActive = new AuctionStatusActive(auction.getIdAuction(), 
                                                                        auction.getTitle(), 
                                                                        auction.getDescription(), 
                                                                        auction.getStatus(), 
                                                                        auction.getStartingPrice(), 
                                                                        auction.getCurrentPrice(), 
                                                                        auction.getEndTime());
            auctionsActive.add(auctionActive);
        }
        return new PageResult<AuctionStatusActive>(auctionsActive, 
                                                    activeAuctionsFound.getPage(), 
                                                    activeAuctionsFound.getPageSize(), 
                                                    activeAuctionsFound.getTotalItems(), 
                                                    activeAuctionsFound.getTotalPages());
    }
    
}
