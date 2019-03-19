package joboonja.domain;

import joboonja.domain.model.*;
import joboonja.domain.repo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Database {
    private static Database db = null;

    private ProjectRepo projects;
    private UserRepo users;
    private BidRepo bids;
    private SkillsRepo skills;
    private EndorsementRepo endorsements;

    private Database(){
        projects = new ProjectRepo();
        users = new UserRepo();
        bids = new BidRepo();
        skills = new SkillsRepo();
        endorsements = new EndorsementRepo();
    }

    public static Database getInstance()
    {
        if (db == null)
            db = new Database();

        return db;
    }

    public int registerUser(JSONObject jo) throws ParseException {
        String username = (String) jo.get("username");
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");
        String jobTitle = (String) jo.get("jobTitle");
        List<Skill> skills = Skill.parseSkills((JSONArray)jo.get("skills"));
        String bio = (String) jo.get("bio");

        User newUser = new User(username, firstName, lastName, jobTitle, skills, bio);
        if(users.add(newUser) < 0)
            return -1;
        return 0;
    }

    public int addProject(JSONObject jo) throws ParseException {
        String id = (String) jo.get("id");
        String title = (String) jo.get("title");
        String description = (String) jo.get("description");
        String imageURL = (String) jo.get("imageUrl");
        List<Skill> requiredSkills = Skill.parseSkills((JSONArray)jo.get("skills"));
        int budget = (int)(long)jo.get("budget");
        long deadline = (long)jo.get("deadline");

        Project newProject = new Project(id, title, description, imageURL, requiredSkills, budget, deadline);
        if(projects.add(newProject) < 0)
            return -1;
        return 0;
    }

    public int addBid(String biddingUser, String projectID, int bidAmount) {
        User user = users.get(biddingUser);
        if(user == null)
            return -1;

        Project project = projects.get(projectID);
        if(project == null)
            return -2;

        int points = project.bidPointsCalc(user, bidAmount);
        if(points < 0)
            return points - 2;

        Bid newBid = new Bid(user, project, bidAmount, points);
        if(bids.add(newBid) == 0)
            project.addBid(newBid);

        return 0;
    }

    public int addSkill(String skillName)  {
        return skills.add(skillName);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Project getProject(String projectID) {
        return projects.get(projectID);
    }

    public List<Project> getApplicableProjects(String username) {
        User user = users.get(username);
        if(user == null)
            return null;
        return projects.getApplicables(user.getSkills());
    }

    public List<User> getUsersList() {
        return users.getList();
    }

    public boolean hasBidded(String username, String projectID) {
        User user = users.get(username);
        Project project = projects.get(projectID);

        return bids.hasBidded(user, project);
    }

    public boolean hasEndorsed(User endorser, User target, String skill){
        return endorsements.hasEndorsed(endorser, target, skill);
    }

    public boolean endorse(String endorser, String target, String skill){
        User endrsr = users.get(endorser);
        if (endrsr == null)
            return false;
        User trgt = users.get(target);
        if (trgt == null)
            return false;
        return endorsements.addEndorsment(endrsr, trgt, skill);
    }

    public boolean deleteSkill(String skillName, String username){
        User u = users.get(username);
        if (u == null)
            return false;
        return u.deleteSkill(skillName);
    }

    public boolean addSkill(String skillName, String username){
        User u = users.get(username);
        if (u == null)
            return false;
        return u.addSkill(skillName);
    }

    public List<String> getSkills() {
        return skills.getList();
    }
}