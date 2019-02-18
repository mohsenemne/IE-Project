import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jobunja.model.*;
import jobunja.repo.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    private static ProjectRepo projrcts;
    private static UserRepo users;
    private static BidRepo bids;
    private static SkillsRepo skills;

    public static void main(String[] args) throws ParseException {
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    register(commandData);
                    break;
                case "addProject":
                    addProject(commandData);
                    break;
                case "bid":
                    bid(commandData);
                    break;
                case "auction":
                    auction(commandData);
                    isFinished = true;
                    break;
            }
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<String, String>(command.substring(0, spaceIndex), command.substring(spaceIndex+1));
    }

    private static void register(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        User newUser = createUser(jo);
        if(users.add(newUser) < 0){
            System.out.println("Username " + newUser.getUsername() + " is already taken!");
        }
    }

    private static void addProject(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Project newProject = createProject(jo);
        if(projrcts.add(newProject) < 0){
            System.out.println("ProjectTitle " + newProject.getTitle() + " is already taken!");
        }
    }

    private static void bid(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Bid newBid = createBid(jo);
        User biddingUser = newBid.getBiddingUser();
        Project project = newBid.getProject();

        if(biddingUser == null){
            System.out.println("User " + newBid.getBiddingUser().getUsername() + " doesn't exist!");
            newBid = null;
        }
        if(project == null){
            System.out.println("Project " + newBid.getProject().getTitle() + " doesn't exist!");
            newBid = null;
        }
        if(newBid != null){
            if(bids.add(newBid) == 0){
                newBid.getProject().addBid(newBid);
            }
        }
    }

    private static void auction(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Project project = projrcts.get((String) jo.get("projectTitle"));
        project.auction();
    }

    private static List<Skill> parseSkills(JSONArray skillsJA) {
        List<Skill> skills = new ArrayList<Skill>();
        for (int i=0; i<skillsJA.size(); i++) {
            JSONObject skillJO = (JSONObject) skillsJA.get(i);
            String skillName = (String) skillJO.get("name");
            int skillPoints = (int) (long) skillJO.get("points");

            skills.add(new Skill(skillName, skillPoints));
        }
        return skills;
    }

    private static User createUser(JSONObject jo) {
        String username = (String) jo.get("username");
        List<Skill> skills = parseSkills((JSONArray)jo.get("skills"));

        return new User(username, skills);
    }

    private static Project createProject(JSONObject jo) {
        String title = (String) jo.get("title");
        List<Skill> skills = parseSkills((JSONArray)jo.get("skills"));
        int budget = (int)(long)jo.get("budget");

        return new Project(title, skills, budget);
    }

    private static Bid createBid(JSONObject jo) {
        String biddingUser = (String) jo.get("biddingUser");
        String projectTitle = (String) jo.get("projectTitle");
        int bidAmount = (int)(long)jo.get("bidAmount");

        User user = users.get(biddingUser);
        Project project = projrcts.get(projectTitle);

        return new Bid(user, project, bidAmount);
    }
}
