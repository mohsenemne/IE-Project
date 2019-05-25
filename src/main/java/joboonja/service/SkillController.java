package joboonja.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import joboonja.domain.Database;
import joboonja.domain.model.User;
import joboonja.domain.model.Skill;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfSkills (@RequestHeader("Authorization") String token) throws IOException, SQLException, ParseException {
        String username = (String) ((JSONObject) new JSONParser().parse(new String(Base64.decodeBase64(JWT.decode(token).getPayload())))).get("username");
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