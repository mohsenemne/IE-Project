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
}


class User {
    private String username ;
    //private  HashMap<String, int> skills ;
    private Vector<Skill> skills ;

    public User (String username, Vector<Skill> skills) {
        this.username = username ;
        this.skills = skills ;
    }

    public int skillsPoint(Vector<Skill> requiredSkills) {
        int result = 0;
        //Iterator it = requiredSkills.iterator();
        for (int i=0; i<requiredSkills.size(); i++){
            for (int j=0; j<this.skills.size(); j++){
                int point = this.skills.get(j).getPoint(requiredSkills.get(i));
                if (point < 0){
                    return -1;
                }
                else if(point > 0){
                    result += point*point;
                }
            }
        }
        return result*10000;
    }

    public String getUsername() {
        return username;
    }
}

class Project {
    public static class Bid {
        public Bid (String username, int bidAmount) {
            this.username = username;
            this.bidAmount = bidAmount;
        }

        private String username;
        private int bidAmount;
    }

    private String title;
    private Vector<Skill> requiredSkills;
    private Vector<Bid> bids;
    private int budget;

    public Project (String title, Vector<Skill> requiredSkills, int budget) {
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.budget = budget;
    }

    public Vector<Skill> getRequiredSkills() {
        return this.requiredSkills;
    }

    public int getPoint(int userOffer) {
        return this.budget - userOffer;
    }

    public String getTitle() {
        return this.title;
    }

    public void addBid (String username, int bidAmount) {
        bids.add(new Bid(username, bidAmount));
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static Vector<User> users;
    private static Vector<Project> projects;

    public static void main(String[] args) {
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
                    Project p = findProject(commandData);
                    if(p == null) {
                        System.out.println("Project doesn't exist!");
                        break;
                    }
                    System.out.println(commandData);
                    isFinished = true;
                    break;
            }
        }
    }

    private static User findUser(String username) {
        if (users == null){
            return null;
        }
        for (int i=0; i<users.size(); i++){
            if (username.equals(users.get(i).getUsername())) {
                return users.get(i);
            }
        }
        return null;
    }

    private static Project findProject(String projectTitle) {
        if (projects == null){
            return null;
        }
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
        Vector<Skill> skills = new Vector<Skill>();
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

        users.add(new User(username, skills));
    }

    private static void addProject (String jsonString) throws ParseException {
        Object obj =  new JSONParser().parse(jsonString);
        JSONObject jo = (JSONObject) obj;

        String title = (String) jo.get("title");
        Vector<Skill> skills = parseSkills((JSONArray)jo.get("skills"));
        int budget = (int) (long) jo.get("budget");

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
        prj.addBid(biddingUser, bidAmount);
    }
}