package jobunja.repo;

import jobunja.model.Bid;

import java.util.ArrayList;
import java.util.List;

public class BidRepo {
    private List<Bid> bids;

    public BidRepo(){
        bids = new ArrayList<Bid>();
    }

    public int add(Bid newBid){
        for(Bid b: bids){
            if(b.getProject().equals(newBid.getProject()) && b.getBiddingUser().equals(newBid.getBiddingUser())){
                b.setBidAmount(newBid.getBidAmount());
                return -1;
            }
        }

        bids.add(newBid);
        return 0;
    }
}