package joboonja.controllers;

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
public class UserInfoController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String context = tokenizer.nextToken();
        String userID = tokenizer.nextToken();

        Database db = Database.getInstance();
        User user= db.getUser(userID);

        if(user == null)
            request.getRequestDispatcher("pageNotFound.jsp").forward(request, response);
        else{
            request.setAttribute("userInfo", user);
            request.getRequestDispatcher("userInfo.jsp").forward(request, response);
        }
    }
}
