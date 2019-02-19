package jobunja.repo;

import java.util.LinkedList;
import java.util.List;

public class SkillsRepo {
    private List<String> skills;

    public SkillsRepo(){ skills = new LinkedList<>(); }

    public void add(String name){
        skills.add(name);
    }
}