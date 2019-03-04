package joboonja.controllers;

import joboonja.domain.Database;
import joboonja.domain.model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/user/*")
public class UserInfoController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = extractUserID(request);

        Database db = Database.getInstance();
        User user= db.getUser(userID);

        if(user == null)
            request.getRequestDispatcher("pageNotFound.jsp").forward(request, response);
        else{
            request.setAttribute("visitor", db.getUser("1"));
            request.setAttribute("user", user);
            request.getRequestDispatcher("/userInfo.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action.equals("delete")){
            deleteSkill(request, response);
        }
        else if(action.equals("endorse")){
            endorseSkill(request, response);
        }
        else if(action.equals("addSkill")){
            addSkill(request, response);
        }

        response.sendRedirect(request.getRequestURI());
    }


    private void deleteSkill(HttpServletRequest request, HttpServletResponse response){
        String userID = extractUserID(request);
        String skillName = request.getParameter("skill");
        Database.getInstance().deleteSkill(skillName, userID);
    }

    private void endorseSkill(HttpServletRequest request, HttpServletResponse response) {
        String userID = extractUserID(request);
        String skillName = request.getParameter("skill");
        String endorser = request.getParameter("endorser");
        Database.getInstance().endorse(endorser, userID, skillName);
    }

    private void addSkill(HttpServletRequest request, HttpServletResponse response) {
        String userID = extractUserID(request);
        String skillName = request.getParameter("skills");

        if(skillName == null)
            return;

        Database.getInstance().addSkill(skillName, userID);
    }


    private String extractUserID(HttpServletRequest request){
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String userID = tokenizer.nextToken();

        return tokenizer.hasMoreTokens() ? null : userID;
    }
}
