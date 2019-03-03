package joboonja.controllers;

import joboonja.database.Database;
import joboonja.database.model.Project;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/project")
public class ProjectsListController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Database db = Database.getInstance();
        List<Project> apllicableProjects = db.getApplicableProjects("1");

        request.setAttribute("projectsList", apllicableProjects);
        request.getRequestDispatcher("projectsList.jsp").forward(request, response);
    }
}
