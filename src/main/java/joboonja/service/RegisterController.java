package joboonja.service;



import joboonja.domain.Database;
import joboonja.domain.model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public boolean registerUser (@RequestBody String userStr) throws SQLException, ParseException, NoSuchAlgorithmException {
        JSONObject jo = (JSONObject) new JSONParser().parse(userStr);
        User user = new User(jo);
        return Database.getInstance().registerUser(user) ;
    }
}