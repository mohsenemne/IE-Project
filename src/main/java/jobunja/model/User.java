package jobunja.model;

import jobunja.repo.SkillsRepo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private List<Skill> skills;
    private String bio;

    public User(String username, String firstName, String lastName, String jobTitle, List<Skill> skills, String bio){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.skills = skills;
        this.bio = bio;
    }

    public User(JSONObject jo){
        username = (String) jo.get("username");
        firstName = (String) jo.get("firstName");
        lastName = (String) jo.get("lastName");
        jobTitle = (String) jo.get("jobTitle");
        skills = Skill.parseSkills((JSONArray)jo.get("skills"));
        bio = (String) jo.get("bio");
    }

    public String getUsername() {
        return username;
    }

    public List<Skill> getSkills() {
        return skills;
    }

}
