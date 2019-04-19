package joboonja.service;

import joboonja.domain.Database;
import joboonja.domain.model.User;

import javax.servlet.ServletException;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfUsers () throws IOException {
        Database db = Database.getInstance() ;
        return User.toJSONString(db.getUsersList()) ;
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public String getUserInfo (@PathVariable(value = "user_id") String userID) throws IOException {
        Database db = Database.getInstance();
        User user= db.getUser(userID);

        if(user == null) {
            return "" ;
        }
        else{
            return user.toJSONString() ;
        }
    }

    @RequestMapping(value = "/{user_id}/skills", method = RequestMethod.DELETE)
    public Boolean deleteUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName) {

        return Database.getInstance().deleteSkill(skillName, userID);
    }

    @RequestMapping(value = "/{user_id}/skills", method = RequestMethod.PUT)
    public Boolean addUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName) {

        if(skillName == null)
            return false;

        return Database.getInstance().addSkill(skillName, userID);
    }

    @RequestMapping(value = "/{user_id}/skills/endorsements", method = RequestMethod.POST)
    public Boolean endorseUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("endorser") String endorser,
                                     @RequestParam("skill") String skillName) {
        return Database.getInstance().endorse(endorser, userID, skillName);
    }
}

