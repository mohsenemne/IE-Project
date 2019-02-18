package jobunja.model;

import java.util.List;

public class Project {
    private String title;
    private List<Skill> requiredSkills;
    private List<Bid> bids;
    private int budget;

    public Project(String title, List<Skill> skills, int budget){
        this.title = title;
        this.requiredSkills = skills;
        this.budget = budget;
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

        for(Bid b: bids){

        }
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }
}
