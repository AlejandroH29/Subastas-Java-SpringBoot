package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.result.AuctionInfoResult;
import com.dhernandez.auction_service.application.useCase.Exception.NoAuctionFound;
import com.dhernandez.auction_service.domain.model.Auction;

public class GetAuctionInfoUseCaseImp implements GetAuctionInfoUseCase{

    private final FindAuctionByIdPort findAuctionById;
    public GetAuctionInfoUseCaseImp(FindAuctionByIdPort findAuctionById){
        this.findAuctionById = findAuctionById;
    }

    @Override
    public AuctionInfoResult getAuctionInfo(Long auctionId) {
        Auction auctionFound = findAuctionById.findAuction(auctionId);
        if(auctionFound == null){
            throw new NoAuctionFound("No se encontro la informacion de la subasta");
        }else{
            return new AuctionInfoResult(auctionFound.getIdAuction(), 
                                            auctionFound.getTitle(), 
                                            auctionFound.getDescription(), 
                                            auctionFound.getStartTime(), 
                                            auctionFound.getEndTime(), 
                                            auctionFound.getStatus(), 
                                            auctionFound.getStartingPrice(), 
                                            auctionFound.getCurrentPrice());
        }
    }
    
}
