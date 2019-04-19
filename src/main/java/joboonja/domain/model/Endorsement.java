package joboonja.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Endorsement {
    private User endorser;
    private User target;
    private String skill;

    public Endorsement(User endorser, User target, String skill){
        this.endorser = endorser;
        this.target = target;
        this.skill = skill;
    }

    public static List<String> getSkillNames(List<Endorsement> endorsements) {
        List<String> skills = new ArrayList<>();
        for(Endorsement e: endorsements){
            skills.add(e.skill);
        }
        return skills;
    }

    public User getEndorser() { return endorser; }

    public User getTarget() { return target; }

    public String getSkill() { return skill; }

}
