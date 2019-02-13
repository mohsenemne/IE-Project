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
}

class Project {
    public static class Bid {
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
}

class Bid {
    private String username;
    private int bidAmount;
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static Vector<User> users;
    private static Vector<Project> projects;
    private static Vector<Bid> bids;

    public static void main(String[] args) {
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    System.out.println(commandData);
                    break;
                case "addProject":
                    System.out.println(commandData);
                    break;
                case "bid":
                    System.out.println(commandData);
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

    private static void jsonParser (String jsonString) throws ParseException {
        String str = "{\"username\":\"user1\",\"skills\":[{\"name\":\"HTML\",\"points\":10},{\"name\":\"CSS\",\"points\":20}]}" ;
        Object obj =  new JSONParser().parse(str);
        JSONObject jo = (JSONObject) obj;

        System.out.println((String) jo.get("username"));
        JSONArray jsonarray = (JSONArray)jo.get("skills");
        for (int i=0; i<jsonarray.size(); i++) {
            JSONObject jsonobject = (JSONObject) jsonarray.get(i);
            System.out.println((String) jsonobject.get("name"));
            System.out.println("kir");
            System.out.println((int) (long) jsonobject.get("points"));
        }
        //System.out.println((String) jo.get("skills"));
        //System.out.println((String) jo.get("skills.point"));
        //System.out.println((String) jo.get("firstName"));
    }
}
