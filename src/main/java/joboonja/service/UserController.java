package joboonja.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import joboonja.domain.Database;
import joboonja.domain.model.Endorsement;
import joboonja.domain.model.Skill;
import joboonja.domain.model.User;

import java.io.IOException;
import java.util.List;

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


    @RequestMapping(value = "/{user_id}/skills/endorsements", method = RequestMethod.GET)
    public String getEndorsments (@PathVariable(value = "user_id") String target) throws JsonProcessingException {
        String endorser = "1";
        if(endorser.equals(target)){
            return null;
        }

        Database db = Database.getInstance();
        List<Endorsement> endorsements = db.getEndorsments(endorser, target);
        return Skill.toJSONString(Endorsement.getSkillNames(endorsements));
    }


    @RequestMapping(value = "/{user_id}/skills", method = RequestMethod.PUT)
    public Boolean addUserSkill (@PathVariable(value = "user_id") String userID, @RequestParam("skill") String skillName) {

        if(skillName == null)
            return false;

        return Database.getInstance().addSkill(skillName, userID);
    }

    @RequestMapping(value = "/{user_id}/skills/endorsements", method = RequestMethod.POST)
    public Boolean endorseUserSkill (@PathVariable(value = "user_id") String target,
                                     @RequestParam("skill") String skillName) {
        String endorser = "1";
        return Database.getInstance().endorse(endorser, target, skillName);
    }
}

