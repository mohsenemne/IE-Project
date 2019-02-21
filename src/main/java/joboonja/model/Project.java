package joboonja.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class Project {
    private String id;
    private String title;
    private String description;
    private String imageURL;
    private List<Skill> requiredSkills;
    private List<Bid> bids;
    private int budget;
    private long deadline;
    private User winner;

    public Project(String id, String title, String description, String imageURL, List<Skill> skills, int budget, long deadline){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.requiredSkills = skills;
        this.budget = budget;
        this.deadline = deadline;
    }

    public String getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public int getBudget() {
        return budget;
    }


    public void addBid(Bid bid) {
        bids.add(bid);
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
        for(Skill rs: requiredSkills){
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
}
