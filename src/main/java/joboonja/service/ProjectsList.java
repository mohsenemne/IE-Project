package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.Project;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/project")
public class ProjectsList extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Database db = Database.getInstance();
        List<Project> applicableProjects = db.getApplicableProjects("1");
        request.setAttribute("projectsList", applicableProjects);
        request.getRequestDispatcher("projectsList.jsp").forward(request, response);
    }
}
