package joboonja.repo;

import joboonja.model.Bid;

import java.util.ArrayList;
import java.util.List;

public class BidRepo {
    private List<Bid> bids;

    public BidRepo(){ bids = new ArrayList<>(); }

    public int add(Bid newBid){
        for(Bid b: bids){
            if(b.getProject().equals(newBid.getProject()) && b.getBiddingUser().equals(newBid.getBiddingUser())){
                b.setBidAmount(newBid.getBidAmount());
                b.setPoints(newBid.getPoints());
                return -1;
            }
        }

        bids.add(newBid);
        return 0;
    }
}