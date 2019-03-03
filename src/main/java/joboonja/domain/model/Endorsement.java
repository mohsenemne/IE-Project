package joboonja.domain.model;

public class Endorsement {
    private User endorser;
    private User target;
    private String skill;

    public Endorsement(User endorser, User target, String skill){
        this.endorser = endorser;
        this.target = target;
        this.skill = skill;
    }

    public User getEndorser() { return endorser; }

    public User getTarget() { return target; }

    public String getSkill() { return skill; }

}
