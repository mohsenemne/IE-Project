package joboonja.server.service;

import joboonja.database.Database;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/project")
public class ProjectsListController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Database db = Database.getInstance();
        String projectsList = db.getApplicableProjectsList("1");

        JSONArray ja = null;
        try {
            ja = (JSONArray) new JSONParser().parse(projectsList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        request.setAttribute("projectsList", ja);
        request.getRequestDispatcher("projectsList.jsp").forward(request, response);
    }
}
