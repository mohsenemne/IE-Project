package joboonja.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static String toJSONString(List<String> skills) throws JsonProcessingException {
        ObjectMapper Obj = new ObjectMapper();
        String info = Obj.writeValueAsString(skills);

        return info;
    }

    public static List<String> getNames(List<Skill> skills) {
        List<String> skillNames = new ArrayList<>();
        for(Skill s: skills){
            skillNames.add(s.name);
        }
        return skillNames;
    }

    public String getName() { return name; }

    public int getPoints() {
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

    public void incPoint() {
        points++;
    }
}