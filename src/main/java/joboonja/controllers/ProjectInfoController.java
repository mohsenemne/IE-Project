package joboonja.controllers;


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
public class ProjectInfoController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectID = extractProjectID(request);

        Database db = Database.getInstance();
        Project project = db.getProject(projectID);

        boolean enableBid = !db.hasBidded("1", projectID);

        if(project == null)
            request.getRequestDispatcher("pageNotFound.jsp").forward(request, response);
        else{
            request.setAttribute("projectInfo", project);
            request.setAttribute("enableBid", enableBid);
            request.getRequestDispatcher("/projectInfo.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectID = extractProjectID(request);
        int bidAmount = Integer.parseInt(request.getParameter("bidAmount"));

        JSONObject bid = new JSONObject();
        bid.put("biddingUser", "1");
        bid.put("projectID", projectID);
        bid.put("bidAmount", new Long(bidAmount));

        Database db = Database.getInstance();
        try {
            db.addBid(bid);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getRequestURI());
    }

    private String extractProjectID(HttpServletRequest request){
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String projectID = tokenizer.nextToken();

        return tokenizer.hasMoreTokens() ? null : projectID;
    }
}
