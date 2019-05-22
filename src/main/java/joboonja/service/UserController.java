package joboonja.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import joboonja.domain.Database;
import joboonja.domain.model.Endorsement;
import joboonja.domain.model.Skill;
import joboonja.domain.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    public String getEndorsements (@PathVariable(value = "user_id") String target,
                                   @RequestHeader("Authorization") String token) throws JsonProcessingException, SQLException {
        String endorser ;
        try {
            DecodedJWT jwt = JWT.decode(token);
            endorser = jwt.getClaim("username").asString() ;
        } catch (Exception ignored) {
            System.out.println("error in getEndorsementService JWT token");
            return null ;
        }
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
    public Boolean endorseUserSkill (@PathVariable(value = "user_id") String target,
                                     @RequestParam("skill") String skillName,
                                     @RequestHeader("Authorization") String token) throws SQLException {
        String endorser ;
        try {
            DecodedJWT jwt = JWT.decode(token);
            endorser = jwt.getClaim("username").asString() ;
        } catch (Exception ignored) {
            System.out.println("error in endorserUserSkillService JWT token");
            return null ;
        }
        return Database.getInstance().endorse(endorser, target, skillName);
    }
}

