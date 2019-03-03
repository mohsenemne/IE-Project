package joboonja.server.service;


import joboonja.database.Database;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

@WebServlet("/project/*")
public class ProjectInfoController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String id = tokenizer.nextToken();

        Database db = Database.getInstance();
        String projectInfo = db.getProjectInfo(id);

        if(projectInfo == null)
            request.getRequestDispatcher("pageNotFound.jsp").forward(request, response);
        else{
            JSONObject jo = null;
            try {
                jo = (JSONObject) new JSONParser().parse(projectInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            request.setAttribute("projectInfo", jo);
            request.getRequestDispatcher("projectInfo.jsp").forward(request, response);
        }
    }
}
