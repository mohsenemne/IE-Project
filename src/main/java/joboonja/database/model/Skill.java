package joboonja.database.model;

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

    String getName() { return name; }

    int getPoints() {
        return points ;
    }

    public static List<Skill> parseSkills(JSONArray skillsJA) {
        List<Skill> skills = new ArrayList<>();
        for (Object o : skillsJA) {
            JSONObject skillJO = (JSONObject) o;
            String skillName = (String) skillJO.get("name");
            int skillPoints = (int) (long) skillJO.get("point");

            skills.add(new Skill(skillName, skillPoints));
        }
        return skills;
    }

    public static String getJsonInfo(List<Skill> skills) {
        String info = "[";
        for (int i = 0; i < skills.size(); i++) {
            info += "{\"name\":\"" + skills.get(i).getName() + "\",\"point\":" + skills.get(i).getPoints() + "}";
            if (i != skills.size() - 1)
                info += ",";
        }
        info += "]";

        return info;
    }
}