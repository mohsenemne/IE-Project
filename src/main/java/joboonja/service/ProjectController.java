package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.Project;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/projects")
public class ProjectController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfProjects () throws IOException {
        Database db = Database.getInstance();
        String userName = "1" ;
        List<Project> projects = db.getApplicableProjects(userName) ;
        if (projects != null) {
            return Project.toJSONString(projects) ;
        }
        else {
            return null ;
        }
    }

    @RequestMapping(value = "/{project_id}", method = RequestMethod.GET)
    public String getProjectInfo (@PathVariable(value = "project_id") String projectID) throws IOException {

        Database db = Database.getInstance();
        Project project = db.getProject(projectID);

        if(project == null)
            return null ;
        else{
            return project.toJSONString() ;
        }
    }

    @RequestMapping(value = "/{project_id}/bids", method = RequestMethod.POST)
    public int addBid (@PathVariable(value = "project_id") String projectID,
                           @RequestParam("bidAmount") String BidAmount) throws IOException {
        int bidAmount = Integer.parseInt(BidAmount);
        Database db = Database.getInstance();

        String biddingUser = "1" ;
        int result = db.addBid(biddingUser, projectID, bidAmount);
        if(result < 0)
            if (result < -2)
                return 412 ;
            else
                return 400 ;
        else
            return 200 ;
    }

}
