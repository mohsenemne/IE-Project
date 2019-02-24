package joboonja.database.repo;

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
}