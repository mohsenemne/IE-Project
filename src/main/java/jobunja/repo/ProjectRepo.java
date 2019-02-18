package jobunja.repo;

import jobunja.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepo {
    private List<Project> projects;

    public ProjectRepo(){
        projects = new ArrayList<Project>();
    }

    public int add(Project newProject){
        if(this.contains(newProject.getTitle())){
            return -1;
        }
        projects.add(newProject);
        return 0;
    }

    public boolean contains(String projectTitle){
        for (Project p: projects) {
            if(p.getTitle().equals(projectTitle)){
                return true;
            }
        }
        return false;
    }

    public Project get(String projectTitle) {
        for (Project p: projects) {
            if(p.getTitle().equals(projectTitle)){
                return p;
            }
        }
        return null;
    }

    public void auction(String projectTitle) {
        for (Project p: projects) {
            if(p.getTitle().equals(projectTitle)){
                p.auction();
            }
        }
    }
}
