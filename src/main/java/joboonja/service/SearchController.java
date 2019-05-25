package joboonja.service;


import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import joboonja.domain.Database;
import joboonja.domain.model.Project;
import joboonja.domain.model.User;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/search")
public class SearchController {
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String searchProject (@RequestHeader("Authorization") String token,
                                 @RequestParam("key") String searchKey) throws SQLException, JsonProcessingException, ParseException {
        String username = (String) ((JSONObject) new JSONParser().parse(new String(Base64.decodeBase64(JWT.decode(token).getPayload())))).get("username");
        Database db = Database.getInstance();
        return Project.toJSONString(db.searchProjects(username, searchKey));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String searchUser (@RequestParam("key") String searchKey) throws SQLException, JsonProcessingException {
        Database db = Database.getInstance();
        return User.toJSONString(db.searchUsers(searchKey));
    }
}