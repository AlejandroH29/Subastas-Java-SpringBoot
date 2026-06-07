package com.dhernandez.auction_service.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhernandez.auction_service.api.dto.CreateBidRequest;
import com.dhernandez.auction_service.api.exception.InvalidPageException;
import com.dhernandez.auction_service.api.exception.InvalidPaginationException;
import com.dhernandez.auction_service.application.command.CreateBidCommand;
import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.result.BidData;
import com.dhernandez.auction_service.application.result.CreateBidResult;
import com.dhernandez.auction_service.application.useCase.Bid.FindBidsHistoryUseCase;
import com.dhernandez.auction_service.application.useCase.Bid.PlaceBidUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("Auction/bid")
public class BidController {
    private PlaceBidUseCase placeBidUseCase;
    private FindBidsHistoryUseCase findBidsHistory;
    public BidController(PlaceBidUseCase placeBidUseCase, FindBidsHistoryUseCase findBidsHistory){
        this.placeBidUseCase = placeBidUseCase;
        this.findBidsHistory = findBidsHistory;
    }
    @PostMapping("/createBid")
    public ResponseEntity<CreateBidResult>createBid(@Valid @RequestBody CreateBidRequest bidDto){
        Long userId = Long.parseLong((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        CreateBidCommand command = new CreateBidCommand(bidDto.getAuctionId(), bidDto.getAmount());
        return new ResponseEntity<CreateBidResult>(placeBidUseCase.placeBid(command, userId), HttpStatus.ACCEPTED);
    }
    @GetMapping("/{auctionId}")
    public ResponseEntity<PageResult<BidData>>findBidsHistory(@PathVariable Long auctionId, 
                                                                @RequestParam(defaultValue = "0") int page, 
                                                                @RequestParam(defaultValue = "10") int size){
        if(page < 0 ){
            throw new InvalidPageException("page debe ser >= 0 ");
        }
        if(size <= 0 || size > 50){
            throw new InvalidPaginationException("size debe de ser >= 1 y <= 50");
        }
        return new ResponseEntity<PageResult<BidData>>(findBidsHistory.findBidsHistory(auctionId, new PageRequest(page, size)), 
                                                            HttpStatus.OK);    
    }
}
