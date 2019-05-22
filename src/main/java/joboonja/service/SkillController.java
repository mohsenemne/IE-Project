package joboonja.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import joboonja.domain.Database;
import joboonja.domain.model.User;
import joboonja.domain.model.Skill;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/skills")
public class SkillController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfSkills (@RequestHeader("Authorization") String token) throws IOException, SQLException {
        Database db = Database.getInstance();
        String userName ;
        try {
            DecodedJWT jwt = JWT.decode(token);
            userName = jwt.getClaim("username").asString() ;
        } catch (Exception ignored) {
            System.out.println("error in getListOfSkillsService JWT token");
            return null;
        }
        List<String> skills = db.getSkills(userName);
        if (skills != null) {
            return Skill.toJSONString(skills) ;
        }
        else {
            return null ;
        }
    }
}
