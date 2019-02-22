package joboonja.server;

import com.sun.net.httpserver.HttpExchange;
import joboonja.Database;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;

public class UserInfoPage implements IPage {
    public void HandleRequest(HttpExchange httpExchange, String id, String applicantUser) throws IOException, ParseException {
        Database db = Database.getInstance();
        String userInfo = db.getUserInfo(id);

        JSONObject jo = (userInfo == null) ? null : (JSONObject) new JSONParser().parse(userInfo);

        String response = (userInfo == null) ? "user " + id + " not found!" :
                "<html>\n"
                        + "<head>\n"
                        + "\t<meta charset=\"UTF-8\">\n"
                        + "\t<title>User</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "\t<ul>\n"
                        + "\t\t<li>id: " + jo.get("id") + "</li>\n"
                        + "\t\t<li>first name: " + jo.get("firstName") + "</li>\n"
                        + "\t\t<li>last name: " + jo.get("lastName") + "</li>\n"
                        + "\t\t<li>jobTitle: " + jo.get("jobTitle") + "</li>\n"
                        + "\t\t<li>bio: " + jo.get("bio") + "</li>\n"
                        + "\t</ul>\n"
                        + "</body>\n"
                        + "</html>";

        JoboonjaServer.sendResponse(httpExchange, response);
    }
}