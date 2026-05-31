package com.dhernandez.auction_service.application.useCase.Auction;

import java.util.List;

import com.dhernandez.auction_service.application.port.out.Auction.FindAuctionsReadyToActivatePort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.domain.model.Auction;

public class ActiveAuctionAutomaticUseCaseImp implements ActiveAuctionAutomaticUseCase{

    private final FindAuctionsReadyToActivatePort auctionsToActivatePort;
    private final SaveAuctionPort saveAuctions;
    public ActiveAuctionAutomaticUseCaseImp(FindAuctionsReadyToActivatePort auctionsToActivatePort, SaveAuctionPort saveAuctions){
        this.auctionsToActivatePort = auctionsToActivatePort;
        this.saveAuctions = saveAuctions;
    }

    @Override
    public void activeAuctionAuctomatic() {
        List<Auction> auctionsToActivate = auctionsToActivatePort.findAuctiosToActivate();
        for(Auction auction: auctionsToActivate){
            try {
                auction.activeAuction();
                saveAuctions.saveAuction(auction);
            } catch (RuntimeException e) {
                System.err.println("No se pudo activar la subasta con id: " + auction.getIdAuction() + ", Error: " + e.getMessage());                
            }
        }
    }
    
}
