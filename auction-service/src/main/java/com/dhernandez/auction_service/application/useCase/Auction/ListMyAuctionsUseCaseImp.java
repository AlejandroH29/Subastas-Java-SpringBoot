package com.dhernandez.auction_service.application.useCase.Auction;

import java.util.ArrayList;
import java.util.List;

import com.dhernandez.auction_service.application.port.out.Auction.FindMyAuctionsPort;
import com.dhernandez.auction_service.application.result.MyAuctions;
import com.dhernandez.auction_service.application.result.MyAuctionsResult;
import com.dhernandez.auction_service.domain.model.Auction;

public class ListMyAuctionsUseCaseImp implements ListMyAuctionsUseCase{

    private final FindMyAuctionsPort findMyAuctionsPort;
    public ListMyAuctionsUseCaseImp(FindMyAuctionsPort findMyAuctionsPort){
        this.findMyAuctionsPort = findMyAuctionsPort;
    }

    @Override
    public MyAuctionsResult listOfMyAuctions(Long userId) {
        List<Auction> myAuctionsFound = findMyAuctionsPort.findMyAuctions(userId);
        List<MyAuctions> myAuctions = new ArrayList<>();
        for(Auction auction: myAuctionsFound){
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
        return new MyAuctionsResult(myAuctions);
    }
    
}
