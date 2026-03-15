package com.dhernandez.auction_service.application.useCase.Auction;

import org.springframework.transaction.annotation.Transactional;

import com.dhernandez.auction_service.application.command.CreateAuctionCommand;
import com.dhernandez.auction_service.application.port.out.Auction.ExistAuctionByTitlePort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.result.CreateAuctionResult;
import com.dhernandez.auction_service.domain.model.Auction;



public class CreateAuctionUseCaseImp implements CreateAuctionUseCase{
    private ExistAuctionByTitlePort existAuctionPort;
    private SaveAuctionPort saveAcution; 
    public CreateAuctionUseCaseImp(ExistAuctionByTitlePort existAuctionPort, SaveAuctionPort saveAuction){
        this.existAuctionPort = existAuctionPort;
        this.saveAcution = saveAuction;
    }

    @Transactional
    @Override
    public CreateAuctionResult createAuction(CreateAuctionCommand command) {
        Auction auction;
        existAuctionPort.existAuction(command.getTitle());
        auction = new Auction(command.getTitle(), command.getDescription(), command.getStartTime(), command.getEndTime(), command.getStartingPrice(), command.getOwnerId());
        saveAcution.saveAuction(auction);    
        CreateAuctionResult result = new CreateAuctionResult(auction.getIdAuction(), auction.getTitle(), auction.getDescription(), auction.getStatus(), auction.getStartTime(), auction.getEndTime(), auction.getStartingPrice());
        return result;
    }

}
    

