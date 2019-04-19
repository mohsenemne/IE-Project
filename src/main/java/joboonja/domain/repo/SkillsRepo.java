package joboonja.domain.repo;

import joboonja.domain.model.Skill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SkillsRepo {
    private List<String> skills;

    public SkillsRepo(){ skills = new LinkedList<>(); }

    public int add(String name){
        if(skills.contains(name)){
            return -1;
        }
        skills.add(name);
        return 0;
    }

    public List<String> getList(List<String> userSkills) {
        List<String> skills = new ArrayList<>();
        for(String s: this.skills){
            if(!userSkills.contains(s))
                skills.add(s);
        }
        return skills;
    }
}