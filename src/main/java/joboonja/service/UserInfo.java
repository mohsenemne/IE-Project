package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/user/*")
public class UserInfo extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userID = extractUserID(request);

        Database db = Database.getInstance();
        User user= db.getUser(userID);

        if(user == null) {
            response.setStatus(404);
        }
        else{
            response.setStatus(200);
            response.getWriter().println(user.toJSONString());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        
        System.out.println(action);
        boolean success = false;
        if (action.equals("delete")){
            success = deleteSkill(request);
        }
        else if(action.equals("endorse")){
            success = endorseSkill(request);
        }
        else if(action.equals("addSkill")){
            success = addSkill(request);
        }

        if (success)
            response.setStatus(200);
        else
            response.setStatus(400);
    }

    private boolean deleteSkill(HttpServletRequest request){
        String userID = extractUserID(request);
        String skillName = request.getParameter("skill");
        return Database.getInstance().deleteSkill(skillName, userID);
    }

    private boolean endorseSkill(HttpServletRequest request) {
        String userID = extractUserID(request);
        String skillName = request.getParameter("skill");
        String endorser = request.getParameter("endorser");
        return Database.getInstance().endorse(endorser, userID, skillName);
    }

    private boolean addSkill(HttpServletRequest request) {
        String userID = extractUserID(request);
        String skillName = request.getParameter("skill");

        if(skillName == null)
            return false;

        return Database.getInstance().addSkill(skillName, userID);
    }

    private String extractUserID(HttpServletRequest request){
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String userID = tokenizer.nextToken();

        return tokenizer.hasMoreTokens() ? null : userID;
    }
}
