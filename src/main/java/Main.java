import javafx.util.Pair;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

class Skill {
    private String name ;
    private int point ;

    public Skill (String name, int point) {
        this.name = name ;
        this.point = point ;
    }

    public int getPoint (Skill s){
        if (this.name.equals(s.name)) {
            return this.point - s.point ;
        }
        else
            return 0 ;
    }

    public String getName(){
        return name;
    }
}


class User {
    private String username ;
    private Vector<Skill> skills ;

    public User (String username, Vector<Skill> skills) {
        this.username = username ;
        this.skills = skills ;
    }

    public String getUsername() {
        return username;
    }

    public Vector<Skill> getSkills() {
        return skills;
    }
}

class Project {
    public static class Bid {
        public Bid (String username, int bidAmount, int skillsPoints) {
            this.username = username;
            this.bidAmount = bidAmount;
            this.skillsPoints = skillsPoints;
        }

        private String username;
        private int bidAmount;
        private int skillsPoints;
    }

    private String title;
    private Vector<Skill> requiredSkills;
    private Vector<Bid> bids;
    private int budget;

    public Project (String title, Vector<Skill> requiredSkills, int budget) {
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.budget = budget;
        this.bids = new Vector<>();
    }

    public String getTitle() {
        return this.title;
    }

    public int calculateSkillsPoint(Vector<Skill> skills) {
        int result = 0;
        for (int i=0; i<requiredSkills.size(); i++){
            int j;
            for (j=0; j<skills.size(); j++){
                if(!requiredSkills.get(i).getName().equals(skills.get(j).getName())){
                    continue;
                }
                int point = skills.get(j).getPoint(this.requiredSkills.get(i));
                if (point < 0){
                    return -1;
                }
                result += point*point;
                break;
            }
            if(j == skills.size()){
                return -1;
            }
        }
        return result*10000;
    }

    private Bid findBid(String username){
        for (int i=0; i<this.bids.size(); i++){
            if (username.equals(bids.get(i).username)) {
                return bids.get(i);
            }
        }
        return null;
    }

    public void addBid(int bidAmount, User user) {
        int skillsPoint = calculateSkillsPoint(user.getSkills());
        if(skillsPoint < 0){
            System.out.println("User " + user.getUsername() + "'s skill doesn't satisfy project " + this.title + "'s requirments!");
            return;
        }
        if(bidAmount > this.budget){
            System.out.println("Project " + this.title + "'s budget cannot satisfy user " + user.getUsername() + "'s bid!");
            return;
        }

        Bid bid = findBid(user.getUsername());
        if(bid != null){
            bid.bidAmount = bidAmount;
            return;
        }

        bids.add(new Bid(user.getUsername(), bidAmount, skillsPoint));
    }

    public String auction(){
        int max = 0;
        int maxID = 0;
        for (int i=0; i<this.bids.size(); i++){
            int temp = bids.get(i).skillsPoints + (budget - bids.get(i).bidAmount);
            if (temp > max) {
                max = temp;
                maxID = i;
            }
        }
        return bids.get(maxID).username;
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static Vector<User> users = new Vector<>();
    private static Vector<Project> projects = new Vector<>();

    public static void main(String[] args) throws ParseException {
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    System.out.println(commandData);
                    registerUser(commandData);
                    break;
                case "addProject":
                    System.out.println(commandData);
                    addProject(commandData);
                    break;
                case "bid":
                    System.out.println(commandData);
                    addBid(commandData);
                    break;
                case "auction":
                    auction(commandData);
                    isFinished = true;
                    break;
            }
        }
    }

    private static User findUser(String username) {
        for (int i=0; i<users.size(); i++){
            if (username.equals(users.get(i).getUsername())) {
                return users.get(i);
            }
        }
        return null;
    }

    private static Project findProject(String projectTitle) {
        for (int i=0; i<projects.size(); i++){
            if (projectTitle.equals(projects.get(i).getTitle())) {
                return projects.get(i);
            }
        }
        return null;
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex+1));
    }

    private static Vector<Skill> parseSkills(JSONArray skillsJA) {
        Vector<Skill> skills = new Vector<>();
        for (int i=0; i<skillsJA.size(); i++) {
            JSONObject skillJO = (JSONObject) skillsJA.get(i);
            String skillName = (String) skillJO.get("name");
            int skillPoints = (int) (long) skillJO.get("points");
            skills.add(new Skill(skillName, skillPoints));
        }
        return skills;
    }

    private static void registerUser (String jsonString) throws ParseException {
        Object obj =  new JSONParser().parse(jsonString);
        JSONObject jo = (JSONObject) obj;

        String username = (String) jo.get("username");
        Vector<Skill> skills = parseSkills((JSONArray)jo.get("skills"));

        if(findUser(username) != null){
            System.out.println("Username " + username + " is already taken!");
            return;
        }

        users.add(new User(username, skills));
    }

    private static void addProject (String jsonString) throws ParseException {
        Object obj =  new JSONParser().parse(jsonString);
        JSONObject jo = (JSONObject) obj;

        String title = (String) jo.get("title");
        Vector<Skill> skills = parseSkills((JSONArray)jo.get("skills"));
        int budget = (int) (long) jo.get("budget");

        if(findProject(title) != null){
            System.out.println("ProjectTitle " + title + " is already taken!");
            return;
        }
        projects.add(new Project(title, skills, budget));
    }

    private static void addBid (String jsonString) throws ParseException {
        Object obj =  new JSONParser().parse(jsonString);
        JSONObject jo = (JSONObject) obj;

        String biddingUser = (String) jo.get("biddingUser");
        String projectTitle = (String)jo.get("projectTitle");
        int bidAmount = (int) (long) jo.get("bidAmount");

        Project prj = findProject(projectTitle);
        User user = findUser(biddingUser);

        if(user == null){
            System.out.println("User " + biddingUser + " doesn't exist!");
            return;
        }
        if(prj == null){
            System.out.println("Project " + projectTitle + " doesn't exist!");
            return;
        }

        prj.addBid(bidAmount, user);
    }

    private static void auction (String jsonString) throws ParseException {
        Object obj =  new JSONParser().parse(jsonString);
        JSONObject jo = (JSONObject) obj;

        String projectTitle = (String)jo.get("projectTitle");

        Project prj = findProject(projectTitle);
        if(prj == null){
            System.out.println("Project " + projectTitle + " doesn't exist!");
            return;
        }
        String winner = prj.auction();
        System.out.println("winner: " + winner);
    }
}