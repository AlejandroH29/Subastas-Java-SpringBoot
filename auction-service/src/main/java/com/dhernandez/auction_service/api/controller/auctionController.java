package com.dhernandez.auction_service.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhernandez.auction_service.api.dto.createAuctionRequest;
import com.dhernandez.auction_service.application.command.createAuctionCommand;
import com.dhernandez.auction_service.application.result.createAuctionResult;
import com.dhernandez.auction_service.application.useCase.createAuctionUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("Auction/auction")
public class auctionController {

    @Autowired createAuctionUseCase auctionUseCase;

    @PostMapping("/createAuction")
    public ResponseEntity<createAuctionResult>createAuction(@RequestBody createAuctionRequest entryAuctionDTO){
        createAuctionCommand auctionCommand = new createAuctionCommand(entryAuctionDTO.getTitle(), entryAuctionDTO.getDescription(), entryAuctionDTO.getStartTime(), entryAuctionDTO.getEndTime(), entryAuctionDTO.getStartingPrice(), entryAuctionDTO.getOwnerId());
        return new ResponseEntity<createAuctionResult>(auctionUseCase.createAuction(auctionCommand), HttpStatus.CREATED);
    }
}
