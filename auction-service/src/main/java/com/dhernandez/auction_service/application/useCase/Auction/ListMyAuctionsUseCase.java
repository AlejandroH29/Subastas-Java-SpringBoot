package com.dhernandez.auction_service.application.useCase.Auction;

import com.dhernandez.auction_service.application.pagination.PageRequest;
import com.dhernandez.auction_service.application.pagination.PageResult;
import com.dhernandez.auction_service.application.result.MyAuctions;

public interface ListMyAuctionsUseCase {
    public PageResult<MyAuctions> listOfMyAuctions(Long userId, PageRequest pageRequest);
}
