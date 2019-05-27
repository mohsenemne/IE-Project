package joboonja.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import joboonja.domain.Database;
import joboonja.domain.model.Endorsement;
import joboonja.domain.model.Skill;
import joboonja.domain.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfUsers () throws IOException, SQLException {
        Database db = Database.getInstance() ;
        return User.toJSONString(db.getUsersList()) ;
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public String getUserInfo (@PathVariable(value = "user_id") String userID) throws IOException, SQLException {
        Database db = Database.getInstance();
        User user = db.getUser(userID);

        if(user == null) {
            return "" ;
        }
        else{
            return user.toJSONString() ;
        }
    }

    @RequestMapping(value = "/{user_id}/skills", method = RequestMethod.DELETE)
    public Boolean deleteUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName) throws SQLException {

        return Database.getInstance().deleteSkill(skillName, userID);
    }


    @RequestMapping(value = "/{user_id}/skills/endorsements", method = RequestMethod.GET)
    public String getEndorsements (@RequestAttribute("username") String endorser,
                                   @PathVariable(value = "user_id") String target) throws JsonProcessingException, SQLException, ParseException {
        if(endorser.equals(target)){
            return null;
        }

        Database db = Database.getInstance();
        List<Endorsement> endorsements = db.getEndorsements(endorser, target);
        return Skill.toJSONString(Endorsement.getSkillNames(endorsements));
    }


    @RequestMapping(value = "/{user_id}/skills", method = RequestMethod.PUT)
    public Boolean addUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName) throws SQLException {

        if(skillName == null)
            return false;

        return Database.getInstance().addSkill(skillName, userID);
    }

    @RequestMapping(value = "/{user_id}/skills/endorsements", method = RequestMethod.POST)
    public Boolean endorseUserSkill (@RequestAttribute("username") String endorser,
                                     @PathVariable(value = "user_id") String target,
                                     @RequestParam("skill") String skillName) throws SQLException, ParseException {
        return Database.getInstance().endorse(endorser, target, skillName);
    }
}