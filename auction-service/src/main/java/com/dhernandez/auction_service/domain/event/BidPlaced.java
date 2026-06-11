    package com.dhernandez.auction_service.domain.event;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    public class BidPlaced implements DomainEvent{
        private final Long auctionId;
        private final Long bidId;
        private final Long userId;
        private final BigDecimal amount;
        private final LocalDateTime timeStamp;
        public BidPlaced(Long auctionId, Long bidId, Long userId, BigDecimal amount, LocalDateTime timeStamp){
            this.auctionId = auctionId;
            this.bidId = bidId;
            this.userId = userId;
            this.amount = amount;
            this.timeStamp = timeStamp;
        }

        public Long getAuctionId(){
            return auctionId;
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
