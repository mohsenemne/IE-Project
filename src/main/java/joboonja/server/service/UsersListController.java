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

@WebServlet("/user")
public class UsersListController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database db = Database.getInstance();
        String usersList = db.getUsersList();

        JSONArray ja = null;
        try {
            ja = (JSONArray) new JSONParser().parse(usersList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        request.setAttribute("usersList", ja);
        request.getRequestDispatcher("usersList.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }

}
