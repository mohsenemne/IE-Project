package joboonja.domain.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public String getUsername() {
        return username;
    }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getJobTitle() { return jobTitle; }
    public List<Skill> getSkills() {
        return skills;
    }
    public String getBio() { return bio; }


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