package joboonja.service;

import com.auth0.jwt.JWT;
import joboonja.domain.Database;
import joboonja.domain.model.Skill;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfSkills (@RequestAttribute("username") String username) throws IOException, SQLException, ParseException {
        Database db = Database.getInstance();
        List<String> skills = db.getSkills(username);
        if (skills != null) {
            return Skill.toJSONString(skills) ;
        }
        else {
            return null ;
        }
    }
}