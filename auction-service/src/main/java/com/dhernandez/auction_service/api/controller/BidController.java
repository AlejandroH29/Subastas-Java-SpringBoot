package com.dhernandez.auction_service.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhernandez.auction_service.api.dto.CreateBidRequest;
import com.dhernandez.auction_service.application.command.CreateBidCommand;
import com.dhernandez.auction_service.application.result.CreateBidResult;
import com.dhernandez.auction_service.application.useCase.Bid.PlaceBidUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("Auction/bid")
public class BidController {
    private PlaceBidUseCase placeBidUseCase;
    public BidController(PlaceBidUseCase placeBidUseCase){
        this.placeBidUseCase = placeBidUseCase;
    }
    @PostMapping("/createBid")
    public ResponseEntity<CreateBidResult>createBid(@Valid @RequestBody CreateBidRequest bidDto){
        Long userId = Long.parseLong((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        CreateBidCommand command = new CreateBidCommand(bidDto.getAuctionId(), bidDto.getAmount());
        return new ResponseEntity<CreateBidResult>(placeBidUseCase.placeBid(command, userId), HttpStatus.ACCEPTED);
    }
}
