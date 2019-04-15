package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.StringTokenizer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfUsers () throws ServletException, IOException {
        Database db = Database.getInstance() ;
//        response.setStatus(200);
//        response.getWriter().println(User.toJSONString(db.getUsersList()));
        return User.toJSONString(db.getUsersList()) ;
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public String getUserInfo (@PathVariable(value = "user_id") String userID) throws IOException {
//        String userID = extractUserID(request);
        Database db = Database.getInstance();
        User user= db.getUser(userID);

        if(user == null) {
//            response.setStatus(404);
            return "" ;
        }
        else{
//            response.setStatus(200);
//            response.getWriter().println(user.toJSONString());
            return user.toJSONString() ;
        }
    }

    @RequestMapping(value = "/{user_id}/delete_skill", method = RequestMethod.DELETE)
    public Boolean deleteUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName)
            throws IOException {

        return Database.getInstance().deleteSkill(skillName, userID);
    }

    @RequestMapping(value = "/{user_id}/add_skill", method = RequestMethod.POST)
    public Boolean addUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName)
        throws IOException {

        if(skillName == null)
            return false;

        return Database.getInstance().addSkill(skillName, userID);
    }

    @RequestMapping(value = "/{user_id}/endorse_skill", method = RequestMethod.POST)
    public Boolean endorseUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("endorser") String endorser,
                                     @RequestParam("skill") String skillName) throws IOException {
        return Database.getInstance().endorse(endorser, userID, skillName);
    }
}

