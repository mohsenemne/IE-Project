package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.Bid;
import joboonja.domain.model.Project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/projects")
public class ProjectController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfProjects (@RequestAttribute("username") String username) throws IOException, SQLException, ParseException {
        Database db = Database.getInstance();
        List<Project> projects = db.getApplicableProjects(username) ;
        if (projects != null) {
            return Project.toJSONString(projects) ;
        }
        else {
            return null ;
        }
    }

    @RequestMapping(value = "/{project_id}", method = RequestMethod.GET)
    public String getProjectInfo (@PathVariable(value = "project_id") String projectID) throws IOException, SQLException {

        Database db = Database.getInstance();
        Project project = db.getProject(projectID);

        if(project == null)
            return null ;
        else{
            return project.toJSONString() ;
        }
    }

    @RequestMapping(value = "/{project_id}/bids", method = RequestMethod.GET)
    public String getBids (@PathVariable(value = "project_id") String projectID) throws IOException, SQLException {
        Database db = Database.getInstance();
        List<Bid> bids = db.getBids(projectID);

        if(bids == null)
            return null ;
        else{
            return Bid.toJSONString(bids) ;
        }
    }

    @RequestMapping(value = "/{project_id}/bids", method = RequestMethod.PUT)
    public int addBid (@RequestAttribute("username") String biddingUser,
                       @PathVariable(value = "project_id") String projectID,
                       @RequestParam("bidAmount") String BidAmount) throws SQLException, ParseException {
        int bidAmount = Integer.parseInt(BidAmount);
        Database db = Database.getInstance();

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