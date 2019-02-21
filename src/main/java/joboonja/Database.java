package joboonja;

import joboonja.model.*;
import joboonja.repo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Database {
    private static Database db = null;

    private ProjectRepo projects;
    private UserRepo users;
    private BidRepo bids;
    private SkillsRepo skills;

    private Database(){
        projects = new ProjectRepo();
        users = new UserRepo();
        bids = new BidRepo();
        skills = new SkillsRepo();
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
        String imageURL = (String) jo.get("imageURL");
        List<Skill> requiredSkills = Skill.parseSkills((JSONArray)jo.get("skills"));
        int budget = (int)(long)jo.get("budget");
        long deadline = (long)jo.get("deadline");

        Project newProject = new Project(id, title, description, imageURL, requiredSkills, budget, deadline);
        if(projects.add(newProject) < 0)
            return -1;
        return 0;
    }

    public int addBid(JSONObject jo) throws ParseException {
        User biddingUser = users.get((String) jo.get("biddingUser"));
        if(biddingUser == null)
            return -1;

        Project project = projects.get((String) jo.get("projectID"));
        if(project == null)
            return -2;

        int bidAmount = (int)(long)jo.get("bidAmount");
        int points = project.bidPointsCalc(biddingUser, bidAmount);
        if(points < 0)
            return points - 2;

        Bid newBid = new Bid(biddingUser, project, bidAmount, points);
        if(bids.add(newBid) == 0)
            project.addBid(newBid);

        return 0;
    }

    public int addSkill(JSONObject jo) throws ParseException {
        String skillName = (String) jo.get("name");
        return skills.add(skillName);
    }
}