package joboonja.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import joboonja.domain.Database;
import joboonja.domain.model.Project;
import joboonja.domain.model.User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/search")
public class SearchController {
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String searchProject (@RequestParam("key") String searchKey) throws SQLException, JsonProcessingException {
        Database db = Database.getInstance();
        return Project.toJSONString(db.searchProjects("1", searchKey));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String searchUser (@RequestParam("key") String searchKey) throws SQLException, JsonProcessingException {
        Database db = Database.getInstance();
        return User.toJSONString(db.searchUsers(searchKey));
    }
}
