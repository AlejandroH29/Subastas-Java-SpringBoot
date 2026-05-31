package com.dhernandez.auction_service.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhernandez.auction_service.api.dto.ActiveAuctionRequest;
import com.dhernandez.auction_service.api.dto.CloseAuctionRequest;
import com.dhernandez.auction_service.api.dto.CreateAuctionRequest;
import com.dhernandez.auction_service.application.command.ActiveAuctionCommand;
import com.dhernandez.auction_service.application.command.CloseAuctionCommand;
import com.dhernandez.auction_service.application.command.CreateAuctionCommand;
import com.dhernandez.auction_service.application.result.ActiveAuctionResult;
import com.dhernandez.auction_service.application.result.CloseAuctionResult;
import com.dhernandez.auction_service.application.result.CreateAuctionResult;
import com.dhernandez.auction_service.application.useCase.Auction.ActiveAuctionMunualUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CloseExpiredAuctionManualUseCase;
import com.dhernandez.auction_service.application.useCase.Auction.CreateAuctionUseCase;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("Auction/auction")
public class AuctionController {

    private final CreateAuctionUseCase auctionUseCase;
    private final ActiveAuctionMunualUseCase activeAuction;
    private final CloseExpiredAuctionManualUseCase closeAuction;
    public AuctionController(CreateAuctionUseCase auctionUseCase, ActiveAuctionMunualUseCase activeAuction, CloseExpiredAuctionManualUseCase closeAuction){
        this.auctionUseCase = auctionUseCase;
        this.activeAuction = activeAuction;
        this.closeAuction = closeAuction;
    }

    @PostMapping("/createAuction")
    public ResponseEntity<CreateAuctionResult>createAuction(@Valid @RequestBody CreateAuctionRequest entryAuctionDTO){
        CreateAuctionCommand auctionCommand = new CreateAuctionCommand(entryAuctionDTO.getTitle(), entryAuctionDTO.getDescription(), entryAuctionDTO.getStartTime(), entryAuctionDTO.getEndTime(), entryAuctionDTO.getStartingPrice());
        Long ownerId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new ResponseEntity<CreateAuctionResult>(auctionUseCase.createAuction(auctionCommand, ownerId), HttpStatus.CREATED);
    }

    @PutMapping("/activeAuction")
    public ResponseEntity<ActiveAuctionResult> activeAuction(@Valid @RequestBody ActiveAuctionRequest entryDTO){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ActiveAuctionCommand command = new ActiveAuctionCommand(entryDTO.getAuctionId());
        return new ResponseEntity<ActiveAuctionResult>(activeAuction.activeAuction(command, userId), HttpStatus.OK);
    }

    @PutMapping("/closeAuction")
    public ResponseEntity<CloseAuctionResult> closeAuction(@Valid @RequestBody CloseAuctionRequest entryDTO){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        CloseAuctionCommand command = new CloseAuctionCommand(entryDTO.getAuctionId());
        return new ResponseEntity<CloseAuctionResult>(closeAuction.closeAuction(command, userId), HttpStatus.OK);
    }
}
