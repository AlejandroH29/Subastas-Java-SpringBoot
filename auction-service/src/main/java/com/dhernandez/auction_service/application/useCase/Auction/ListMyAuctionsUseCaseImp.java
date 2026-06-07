package com.dhernandez.auction_service.application.useCase.Auction;

import java.util.ArrayList;
import java.util.List;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.port.out.Auction.FindMyAuctionsPort;
import com.dhernandez.auction_service.application.result.MyAuctions;
import com.dhernandez.auction_service.domain.model.Auction;

public class ListMyAuctionsUseCaseImp implements ListMyAuctionsUseCase{

    private final FindMyAuctionsPort findMyAuctionsPort;
    public ListMyAuctionsUseCaseImp(FindMyAuctionsPort findMyAuctionsPort){
        this.findMyAuctionsPort = findMyAuctionsPort;
    }

    @Override
    public PageResult<MyAuctions> listOfMyAuctions(Long userId, PageRequest pageRequest) {
        PageResult<Auction> myAuctionsFound = findMyAuctionsPort.findMyAuctions(userId, pageRequest);
        List<MyAuctions> myAuctions = new ArrayList<>();
        for(Auction auction: myAuctionsFound.getData()){
            MyAuctions myAuction = new MyAuctions(auction.getIdAuction(), 
                                                    auction.getTitle(), 
                                                    auction.getDescription(), 
                                                    auction.getStartTime(), 
                                                    auction.getEndTime(), 
                                                    auction.getStatus(), 
                                                    auction.getStartingPrice(), 
                                                    auction.getCurrentPrice(), 
                                                    auction.getWinnerId());
            myAuctions.add(myAuction);
        }
        return new PageResult<MyAuctions>(myAuctions, myAuctionsFound.getPage(), myAuctionsFound.getPageSize(), myAuctionsFound.getTotalItems(), myAuctionsFound.getTotalPages());
    }
    
}
