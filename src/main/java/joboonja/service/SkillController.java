package joboonja.service;

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
    public String getListOfSkills () throws IOException, SQLException {
        Database db = Database.getInstance();
        String userName = "1" ;
        List<String> skills = db.getSkills(userName);
        if (skills != null) {
            return Skill.toJSONString(skills) ;
        }
        else {
            return null ;
        }
    }
}
