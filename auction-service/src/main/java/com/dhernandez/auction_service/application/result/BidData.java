package com.dhernandez.auction_service.application.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidData {
    private Long bidId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime timeStamp;
    public BidData(Long bidId, Long userId, BigDecimal amount, LocalDateTime timeStamp){
        this.bidId = bidId;
        this.userId = userId;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public Long getBidId(){
        return bidId;
    }
    public Long getUserId(){
        return userId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
}
