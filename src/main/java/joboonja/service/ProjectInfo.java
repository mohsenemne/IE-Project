package joboonja.service;


import joboonja.domain.Database;
import joboonja.domain.model.Project;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/project/*")
public class ProjectInfo extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectID = extractProjectID(request);

        Database db = Database.getInstance();
        Project project = db.getProject(projectID);

        if(project == null)
            response.setStatus(404);
        else{
            response.setStatus(200);
            response.getWriter().println(project.toJSONString());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectID = extractProjectID(request);
        int bidAmount = Integer.parseInt(request.getParameter("bidAmount"));
        String biddingUser = request.getParameter("applicantUser");

        Database db = Database.getInstance();

        int result = db.addBid(biddingUser, projectID, bidAmount);
        if(result < 0)
            if (result < -2)
                response.setStatus(412);
            else
               response.setStatus(400);
        else
            response.setStatus(200);
    }

    private String extractProjectID(HttpServletRequest request){
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String projectID = tokenizer.nextToken();

        return tokenizer.hasMoreTokens() ? null : projectID;
    }
}