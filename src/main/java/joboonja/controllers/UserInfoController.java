package joboonja.controllers;

import joboonja.database.Database;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String userID = tokenizer.nextToken();

        Database db = Database.getInstance();
        String userInfo = db.getUserInfo(userID);

        if(userInfo == null)
            request.getRequestDispatcher("pageNotFound.jsp").forward(request, response);
        else{
            JSONObject jo = null;
            try {
                jo = (JSONObject) new JSONParser().parse(userInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            request.setAttribute("userInfo", jo);
            request.getRequestDispatcher("userInfo.jsp").forward(request, response);
        }
    }

}
