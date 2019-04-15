package joboonja.service;

import com.sun.tools.internal.jxc.ap.Const;
import joboonja.domain.Database;
import joboonja.domain.model.Project;
import joboonja.domain.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/project")
public class ProjectController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfProjects () throws ServletException, IOException {
        Database db = Database.getInstance();
        String userName = "1" ;
        List<Project> projects = db.getApplicableProjects(userName) ;
        if (projects != null) {
//            response.getWriter().println(Project.toJSONString(projects));
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
//            response.setStatus(404);
            return null ;
        else{
//            response.setStatus(200);
//            response.getWriter().println(project.toJSONString());
            return project.toJSONString() ;
        }
    }

    @RequestMapping(value = "/{project_id}/add_bid", method = RequestMethod.POST)
    public int addBid (@PathVariable(value = "project_id") String projectID,
                           @RequestParam("bidAmount") String BidAmount) throws IOException {
        int bidAmount = Integer.parseInt(BidAmount);
        Database db = Database.getInstance();

        String biddingUser = "1" ;
        int result = db.addBid(biddingUser, projectID, bidAmount);
        if(result < 0)
            if (result < -2)
//                response.setStatus(412);
                return 412 ;
            else
//                response.setStatus(400);
                return 400 ;
        else
//            response.setStatus(200);
            return 200 ;
    }

}
