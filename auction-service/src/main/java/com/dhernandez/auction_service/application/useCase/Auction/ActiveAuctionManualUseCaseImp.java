package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.command.ActiveAuctionCommand;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.result.ActiveAuctionResult;
import com.dhernandez.auction_service.application.useCase.Exception.IncorrectUserIdAuction;
import com.dhernandez.auction_service.application.useCase.Exception.NoAuctionFound;
import com.dhernandez.auction_service.domain.model.Auction;

import org.springframework.transaction.annotation.Transactional;

public class ActiveAuctionManualUseCaseImp implements ActiveAuctionMunualUseCase{
    private final FindAuctionByIdPort findAuctionById;
    private final SaveAuctionPort saveAuction;
    public ActiveAuctionManualUseCaseImp(FindAuctionByIdPort findAuctionById, SaveAuctionPort saveAuction){
        this.findAuctionById = findAuctionById;
        this.saveAuction = saveAuction;
    }
    
    @Transactional
    @Override
    public ActiveAuctionResult activeAuction(ActiveAuctionCommand command, Long userId) {
        Auction auctionFound = findAuctionById.findAuction(command.getAuctionId());
        if(auctionFound == null){
            throw new NoAuctionFound("No se encontro la subasta");
        }else{
            if(!auctionFound.getOwnerId().equals(userId)){
                throw new IncorrectUserIdAuction("Usuarios dueño de subasta diferente");
            }
            auctionFound.activeAuction();
            saveAuction.saveAuction(auctionFound);
            return new ActiveAuctionResult(auctionFound.getTitle(), auctionFound.getDescription(), auctionFound.getStartTime(), auctionFound.getEndTime(), auctionFound.getStatus(), auctionFound.getStartingPrice());
        }
    }
    
}
