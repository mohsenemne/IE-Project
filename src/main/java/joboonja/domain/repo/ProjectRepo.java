package joboonja.domain.repo;

import joboonja.domain.model.Project;
import joboonja.domain.model.Skill;

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

    public List<Project> getApplicables(List<Skill> skills) {
        List<Project> applicables = new ArrayList<>();
        for (Project p : projects) {
            if(p.skillsPointCalc(skills) >= 0)
                applicables.add(p);
        }
        return applicables;
    }
}
