package com.dhernandez.auction_service.application.result;

import java.util.Collections;
import java.util.List;

public class BidsHistoryResult {
    public List<BidData> bidsHistory;
    public BidsHistoryResult( List<BidData> bidsHistory){
        this.bidsHistory = bidsHistory;
    }
    public List<BidData> getBidsHistory(){
        return Collections.unmodifiableList(bidsHistory);
    }
}
