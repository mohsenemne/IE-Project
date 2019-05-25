package joboonja.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageURL;
    private List<Skill> skills;
    private List<Bid> bids;
    private int budget;
    private long deadline;
    private long creationDate;
    private User winner;

    public Project(String id, String title, String description, String imageURL, List<Skill> skills, int budget, long deadline, long creationDate, User winner){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.skills = skills;
        this.budget = budget;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.winner = winner;
    }

    public String getID() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() { return description; }
    public String getImageURL() { return imageURL; }
    public List<Skill> getSkills() {
        return skills;
    }
    public int getBudget() {
        return budget;
    }
    public long getDeadline() { return deadline; }
    public User getWinner() { return winner; }

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public void setBids(List<Bid> bids){
        this.bids = bids;
    }

    public User auction() {
        if(bids.size() == 0){
            return null;
        }
        int maxOffer = -1 ;
        User winner = null ;
        for(Bid b: bids){
            if(maxOffer<b.getPoints()){
                maxOffer = b.getPoints() ;
                winner = b.getBiddingUser() ;
            }
        }

        return winner;
    }

    public int skillsPointCalc(List<Skill> skills) {
        int result = 0;
        for(Skill rs: this.skills){
            for(Skill s: skills){
                if(rs.getName().equals(s.getName())){
                    if(rs.getPoints() > s.getPoints())
                        return -1 ;
                    int point = s.getPoints() - rs.getPoints() ;
                    result += point*point ;
                }
            }
        }
        return result*10000;
    }

    public int bidPointsCalc(User user, int bidAmount){
        int skillsPoint = this.skillsPointCalc(user.getSkills()) ;
        int offerPoint = this.getBudget() - bidAmount ;
        if(skillsPoint<0)
            return -1;
        if(offerPoint<0)
            return -2;
        return skillsPoint + offerPoint;
    }

    public String toJSONString() throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();
        String info = Obj.writeValueAsString(this);

        return info;
    }

    public static String toJSONString(List<Project> projects) throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();
        String info = Obj.writeValueAsString(projects);

        return info;
    }

    public long getCreationDate() {
        return creationDate;
    }
}