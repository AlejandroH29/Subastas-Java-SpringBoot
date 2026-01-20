package com.dhernandez.auction_service.application.useCase;

import com.dhernandez.auction_service.application.command.createAuctionCommand;
import com.dhernandez.auction_service.application.port.out.existAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.saveAuctionPort;
import com.dhernandez.auction_service.application.result.createAuctionResult;
import com.dhernandez.auction_service.domain.model.Auction;

public class createAuctionUseCaseImp implements createAuctionUseCase{
    private existAuctionByTitlePort existAuctionPort;
    private saveAuctionPort saveAcution; 
    createAuctionUseCaseImp(existAuctionByTitlePort existAuctionPort, saveAuctionPort saveAuction){
        this.existAuctionPort = existAuctionPort;
        this.saveAcution = saveAuction;
    }

    @Override
    public createAuctionResult createAuction(createAuctionCommand command) {
        Auction auction;
        existAuctionPort.existAuction(command.getTitle());
        auction = new Auction(command.getTitle(), command.getDescription(), command.getStartTime(), command.getEndTime(), command.getStartingPrice(), command.getOwnerId());
        saveAcution.saveAuction(auction);    
        createAuctionResult result = new createAuctionResult(auction.getIdAuction(), auction.getTitle(), auction.getDescription(), auction.getStatus(), auction.getStartTime(), auction.getEndTime(), auction.getStartingPrice());
        return result;
    }

}
    

