package com.dhernandez.auction_service.application.useCase.Auction;

import org.springframework.transaction.annotation.Transactional;

import com.dhernandez.auction_service.application.command.CloseAuctionCommand;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.result.CloseAuctionResult;
import com.dhernandez.auction_service.application.useCase.Exception.IncorrectUserIdAuction;
import com.dhernandez.auction_service.application.useCase.Exception.NoAuctionFound;
import com.dhernandez.auction_service.domain.model.Auction;

public class CloseExpiredAuctionManualUseCaseImp implements CloseExpiredAuctionManualUseCase{
    private final FindAuctionByIdPort findAuctionById;
    private final SaveAuctionPort saveAuction;
    public CloseExpiredAuctionManualUseCaseImp(FindAuctionByIdPort findAuctionById, SaveAuctionPort saveAuction){
        this.findAuctionById = findAuctionById;
        this.saveAuction = saveAuction;
    }

    @Transactional
    @Override
    public CloseAuctionResult closeAuction(CloseAuctionCommand command, Long userId) {
        Auction auctionFound = findAuctionById.findAuction(command.getAuctionId());
        if(auctionFound == null){
            throw new NoAuctionFound("No se encontro la subasta");
        }else{
            if(!auctionFound.getOwnerId().equals(userId)){
                throw new IncorrectUserIdAuction("Usuarios dueño de subasta diferente");
            }
            auctionFound.closeAuction();
            saveAuction.saveAuction(auctionFound);
            return new CloseAuctionResult(auctionFound.getTitle(), auctionFound.getDescription(), auctionFound.getStartTime(), auctionFound.getEndTime(), auctionFound.getStatus(), auctionFound.getStartingPrice());
        }
    }
    
}
