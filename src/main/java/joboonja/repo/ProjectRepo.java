package joboonja.repo;

import joboonja.model.Project;

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
}
