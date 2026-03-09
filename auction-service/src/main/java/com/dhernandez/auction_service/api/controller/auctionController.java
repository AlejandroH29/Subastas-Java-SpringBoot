package com.dhernandez.auction_service.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhernandez.auction_service.api.dto.CreateAuctionRequest;
import com.dhernandez.auction_service.application.command.createAuctionCommand;
import com.dhernandez.auction_service.application.result.CreateAuctionResult;
import com.dhernandez.auction_service.application.useCase.Auction.CreateAuctionUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("Auction/auction")
public class AuctionController {

    @Autowired CreateAuctionUseCase auctionUseCase;

    @PostMapping("/createAuction")
    public ResponseEntity<CreateAuctionResult>createAuction(@RequestBody CreateAuctionRequest entryAuctionDTO){
        createAuctionCommand auctionCommand = new createAuctionCommand(entryAuctionDTO.getTitle(), entryAuctionDTO.getDescription(), entryAuctionDTO.getStartTime(), entryAuctionDTO.getEndTime(), entryAuctionDTO.getStartingPrice(), entryAuctionDTO.getOwnerId());
        return new ResponseEntity<CreateAuctionResult>(auctionUseCase.createAuction(auctionCommand), HttpStatus.CREATED);
    }
}
