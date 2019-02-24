package joboonja.database.repo;

import joboonja.database.model.Project;
import joboonja.database.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {
    private List<Project> projects;

    public ProjectRepo(){
        projects = new ArrayList<>();
    }

    public int add(Project newProject){
        if(this.contains(newProject.getTitle())){
            return -1;
        }
        projects.add(newProject);
        return 0;
    }

    private boolean contains(String projectID){
        for (Project p: projects) {
            if(p.getID().equals(projectID)){
                return true;
            }
        }
        return false;
    }

    public Project get(String projectID) {
        for (Project p: projects) {
            if(p.getID().equals(projectID)){
                return p;
            }
        }
        return null;
    }

    public String getApplicableJsonList(List<Skill> skills) {
        String info = "[";
        for (Project p : projects) {
            if(p.skillsPointCalc(skills) < 0)
                continue;
            char str = info.charAt(info.length() - 1);
            if(str != ',' && str != '['){
                info += ",";
            }
            info += "{\"id\":\"" + p.getID() + "\",\"title\":\"" + p.getTitle()
                    + "\",\"budget\":" + p.getBudget() + "}";
        }
        info += "]";
        return info;
    }
}
