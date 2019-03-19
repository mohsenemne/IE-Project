package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UsersList extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database db = Database.getInstance();
        List<User> users = db.getUsersList();

        request.setAttribute("usersList", users);
        request.getRequestDispatcher("usersList.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }

}
