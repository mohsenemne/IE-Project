package joboonja.controllers;


import joboonja.domain.Database;
import joboonja.domain.model.Project;

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
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String projectID = tokenizer.nextToken();

        Database db = Database.getInstance();
        Project project = tokenizer.hasMoreTokens() ? null : db.getProject(projectID);

        boolean enableBid = !db.hasBidded("1", projectID);

        if(project == null)
            request.getRequestDispatcher("pageNotFound.jsp").forward(request, response);
        else{
            request.setAttribute("projectInfo", project);
            request.setAttribute("enableBid", enableBid);
            request.getRequestDispatcher("/projectInfo.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Sdfg");
    }
}
