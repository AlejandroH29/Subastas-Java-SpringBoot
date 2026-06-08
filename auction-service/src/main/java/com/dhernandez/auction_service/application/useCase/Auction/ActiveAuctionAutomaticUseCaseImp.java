package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
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
        while(true){
            PageResult<Auction> auctionsToActivate = auctionsToActivatePort.findAuctiosToActivate(new PageRequest(0, 50));
            if(auctionsToActivate.getData().isEmpty()){
                break;
            }else{
                for(Auction auction: auctionsToActivate.getData()){
                    try {
                        auction.activeAuction();
                        saveAuctions.saveAuction(auction);
                    } catch (RuntimeException e) {
                        System.err.println("No se pudo activar la subasta con id: " + auction.getIdAuction() + ", Error: " + e.getMessage());                
                    }
                }
            }
        }
    }
    
}
