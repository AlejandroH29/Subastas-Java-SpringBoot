package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.port.out.Auction.FindExpiredAuctionsPort;
import com.dhernandez.auction_service.application.port.out.Auction.SaveAuctionPort;
import com.dhernandez.auction_service.domain.model.Auction;

public class CloseExpiredAuctionsUseCaseImp implements CloseExpiredAuctionsUseCase{  
    
    private final FindExpiredAuctionsPort findAuctionsPort;
    private final SaveAuctionPort saveAuctions;

    public CloseExpiredAuctionsUseCaseImp(FindExpiredAuctionsPort findAuctionsPort, SaveAuctionPort saveAuctions){
        this.findAuctionsPort = findAuctionsPort;
        this.saveAuctions = saveAuctions;
    }

    @Override
    public void closeExpiredAuctions() {
        while(true){
            PageResult<Auction> expiredAuctions = findAuctionsPort.findExpiredAuctions(new PageRequest(0, 50));
            if(expiredAuctions.getData().isEmpty()){
                break;
            }else{
                for(Auction auction: expiredAuctions.getData()){
                    try {
                        auction.closeAuction();
                        saveAuctions.saveAuction(auction);
                    } catch (RuntimeException e) {
                        System.err.println("No se pudo activar la subasta con id: " + auction.getIdAuction() + ", Error: " + e.getMessage());                
                    }
                }
            }   
        }
    }
    
}
