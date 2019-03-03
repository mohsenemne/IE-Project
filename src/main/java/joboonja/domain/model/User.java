package joboonja.domain.model;

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

    public List<Skill> getSkills() {
        return skills;
    }

    public String getJsonInfo() {
        String info = "{\"id\":\"" + username + "\",\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName
                + "\",\"jobTitle\":\"" + jobTitle + "\",\"skills\":";
        info += Skill.getJsonInfo(skills);
        info += ",\"bio\":\"" + bio + "\"}";

        return info;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getJobTitle() { return jobTitle; }
}
