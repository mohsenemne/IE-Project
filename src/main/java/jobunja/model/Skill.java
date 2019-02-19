package jobunja.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private int points;

    public Skill(String name, int points){
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points ;
    }

    public static List<Skill> parseSkills(JSONArray skillsJA) {
        List<Skill> skills = new ArrayList<Skill>();
        for (int i=0; i<skillsJA.size(); i++) {
            JSONObject skillJO = (JSONObject) skillsJA.get(i);
            String skillName = (String) skillJO.get("name");
            int skillPoints = (int) (long) skillJO.get("point");

            skills.add(new Skill(skillName, skillPoints));
        }
        return skills;
    }
}
