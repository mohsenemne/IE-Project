package joboonja.domain.repo;

import joboonja.domain.model.Bid;
import joboonja.domain.model.Project;
import joboonja.domain.model.User;

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

    public boolean hasBidded(User user, Project project) {
        for (Bid b : bids) {
            if(b.getProject().equals(project) && b.getBiddingUser().equals(user))
                return true;
        }
        return false;
    }
}