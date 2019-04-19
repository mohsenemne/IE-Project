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

    public String toJSONString() {
//        JSONObject JSONInfo = new JSONObject();
//        JSONInfo.put("id", username);
//        JSONInfo.put("firstName", firstName);
//        JSONInfo.put("lastName", lastName);
//        JSONInfo.put("jobTitle", jobTitle);
//        JSONInfo.put("skills", Skill.getJsonInfo(skills));
//        JSONInfo.put("bio", bio);
//        return JSONInfo.toString() ;
//

        String info = "{\"id\":\"" + username + "\",\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName
                + "\",\"jobTitle\":\"" + jobTitle + "\",\"skills\":";
        info += Skill.getJsonInfo(skills);
        info += ",\"bio\":\"" + bio + "\"}";

        return info;
    }

    static public String toJSONString(List<User> users) {
        String info = "[";
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            info += "{\"id\":\"" + u.getUsername() + "\",\"name\":\"" + u.getName() + "\",\"jobTitle\":\"" + u.getJobTitle() +  "\"}";
            if (i != users.size() - 1)
                info += ",";
        }
        info += "]";

        return info;
    }
}