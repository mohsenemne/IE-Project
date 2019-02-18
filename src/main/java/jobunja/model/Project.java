package jobunja.model;

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

    public User auction() {
        if(bids.size() == 0){
            return null;
        }
        for(Bid b: bids){

        }
        return null;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }
}
