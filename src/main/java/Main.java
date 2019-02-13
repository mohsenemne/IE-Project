import javafx.util.Pair;

import java.util.*;



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


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

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
                    System.out.println(commandData);
                    isFinished = true;
                    break;
            }
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex+1));
    }
}
