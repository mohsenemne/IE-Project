package joboonja.domain.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password ;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private List<Skill> skills;
    private String bio;
    private String profilePictureURL ;

    public User(String username, String password, String firstName, String lastName, String jobTitle, List<Skill> skills, String bio,
                String profilePictureURL){
        this.username = username;
        this.password = password ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.skills = skills;
        this.bio = bio;
        this.profilePictureURL = profilePictureURL ;
    }

    public User(JSONObject jo){
        this.username = (String) jo.get("username");
        this.password = (String) jo.get("password");
        this.firstName = (String) jo.get("firstName");
        this.lastName = (String) jo.get("lastName");
        this.jobTitle = (String) jo.get("jobTitle");
        this.skills = new ArrayList<>();
        this.bio = (String) jo.get("bio");
        this.profilePictureURL = (String) jo.get("profilePictureURL");
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password ;
    }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getJobTitle() { return jobTitle; }
    public List<Skill> getSkills() {
        return skills;
    }
    public String getBio() { return bio; }
    public String getProfilePictureURL() { return profilePictureURL; }


    public String getName() {
        return firstName + " " + lastName;
    }

    public void incPoint(String skill) {
        for (Skill s : skills){
            if (s.getName().equals(skill))
                s.incPoint();
        }
    }

    public boolean deleteSkill(String skillName) {
        for (Skill s : skills) {
            if(s.getName().equals(skillName)) {
                skills.remove(s);
                return true;
            }
        }
        return false;
    }

    public boolean hasSkill(String skillName){
        for (Skill s : skills) {
            if(s.getName().equals(skillName)) {
                return true;
            }
        }
        return false;
    }

    public boolean addSkill(String skillName){
        if (hasSkill(skillName))
            return false;

        skills.add(new Skill(skillName, 0));
        return true;
    }

    public String toJSONString() throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();
        String info = Obj.writeValueAsString(this);

        return info;
    }

    static public String toJSONString(List<User> users) throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();
        String info = Obj.writeValueAsString(users);

        return info;
    }
}