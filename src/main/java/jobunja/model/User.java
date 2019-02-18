package jobunja.model;

import jobunja.repo.SkillsRepo;

import java.util.List;

public class User {
    private String username;
    private List<Skill> skills;

    public User(String username, List<Skill> skills){
        this.username = username;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public List<Skill> getSkills() {
        return skills;
    }
}
