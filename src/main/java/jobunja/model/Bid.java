package jobunja.model;

public class Bid {
    private User biddingUser;
    private Project project;
    private int bidAmount;

    public Bid(User biddingUser, Project project, int bidAmount){
        this.biddingUser = biddingUser;
        this.project = project;
        this.bidAmount = bidAmount;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public User getBiddingUser() {
        return biddingUser;
    }

    public Project getProject() {
        return project;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
}
