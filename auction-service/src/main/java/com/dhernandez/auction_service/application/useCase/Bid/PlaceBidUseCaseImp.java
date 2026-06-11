package com.dhernandez.auction_service.application.useCase.Bid;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.dhernandez.auction_service.application.command.CreateBidCommand;
import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionByIdPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.application.port.out.Bid.SaveBidPort;
import com.dhernandez.auction_service.application.port.out.EventPublisher.EventPublisherPort;
import com.dhernandez.auction_service.application.result.CreateBidResult;
import com.dhernandez.auction_service.application.useCase.Exception.FailToPlaceBid;
import com.dhernandez.auction_service.application.useCase.Exception.NoAuctionFound;
import com.dhernandez.auction_service.domain.model.Auction;
import com.dhernandez.auction_service.domain.model.Bid;

public class PlaceBidUseCaseImp implements PlaceBidUseCase { 
    private SaveAuctionPort saveAuction;
    private SaveBidPort saveBid;
    private FindAuctionByIdPort findAuctionById;
    private EventPublisherPort eventPublisherPort;

    public PlaceBidUseCaseImp(SaveAuctionPort saveAuction,SaveBidPort saveBid,FindAuctionByIdPort findAuctionById, EventPublisherPort eventPublisherPort){
        this.saveAuction = saveAuction;
        this.saveBid = saveBid;
        this.findAuctionById = findAuctionById;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    @Transactional
    public CreateBidResult placeBid(CreateBidCommand bidCommand, Long userId) {
        Auction auction = findAuctionById.findAuction(bidCommand.getAuctionId());
        if(auction == null){
            throw new NoAuctionFound("No se encontro la subasta a la que desea pujar");
        }
        Bid bid = new Bid(bidCommand.getAuctionId(), userId, bidCommand.getAmount());
        auction.placeBid(bid);
        try {
            saveAuction.saveAuction(auction);
        } catch (OptimisticLockingFailureException e) {
            throw new FailToPlaceBid("La subasta cambio, intente nuevamente");
        }
        Bid savedBid = saveBid.saveBid(bid);
        eventPublisherPort.publish(auction.getDomainEvents());
        auction.clearEvents();
        return new CreateBidResult(savedBid.getId(), savedBid.getAuctionId(), savedBid.getAmount(), savedBid.getTimeStamp());
    }
    
}
