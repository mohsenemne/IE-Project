package joboonja.domain.model;

public class Bid {
    private User biddingUser;
    private Project project;
    private int bidAmount;
    private int points ;

    public Bid(User biddingUser, Project project, int bidAmount, int points){
        this.biddingUser = biddingUser;
        this.project = project;
        this.bidAmount = bidAmount;
        this.points = points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
