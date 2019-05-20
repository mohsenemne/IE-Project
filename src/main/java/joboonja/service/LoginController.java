package joboonja.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import joboonja.domain.Database;
import joboonja.domain.model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

import static java.util.Collections.emptyList;


@RestController
@RequestMapping("/login")
public class LoginController {
    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "", value = HttpStatus.FORBIDDEN)
    class WrongUsernameOrPassword extends RuntimeException {}

    @RequestMapping(value = "" , method = RequestMethod.POST)
    public String loginUser (@RequestBody String userStr) throws SQLException, ParseException, NoSuchAlgorithmException {
        JSONObject jo = (JSONObject) new JSONParser().parse(userStr);
        joboonja.domain.model.User user = new User(jo);
        try{
            if(Database.getInstance().loginUser(user)){
                return creatToken(user)  ;
            }
            else{
                throw new WrongUsernameOrPassword();
            }
        } catch (SQLException ex){
            throw new WrongUsernameOrPassword();
        }
    }

    private String creatToken(User user) {
        String token = "not valid";
        try {
            Algorithm algorithm = Algorithm.HMAC256("joboonja");
            token = JWT.create()
                    .withIssuer("auth0").withClaim("username", user.getUsername()).sign(algorithm);

        } catch (JWTCreationException ignored){
        }

        return token;
    }
}
