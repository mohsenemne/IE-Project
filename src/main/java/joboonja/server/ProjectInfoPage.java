package joboonja.server;

import com.sun.net.httpserver.HttpExchange;
import joboonja.Database;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;

public class ProjectInfoPage implements IPage{
    public void HandleRequest(HttpExchange httpExchange, String id, String applicantUser) throws IOException, ParseException {
        Database db = Database.getInstance();
        String projectInfo = db.getProjectInfo(id);

        JSONObject jo = (projectInfo == null) ? null : (JSONObject) new JSONParser().parse(projectInfo);

        String response = (projectInfo == null) ? "project " + id + " not found!" :
                "<html>\n"
                        + "<head>\n"
                        + "\t<meta charset=\"UTF-8\">\n"
                        + "\t<title>Project</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "\t<ul>\n"
                        + "\t\t<li>id: " + jo.get("id") + "</li>\n"
                        + "\t\t<li>title: " + jo.get("title") + "</li>\n"
                        + "\t\t<li>description: " + jo.get("description") + "</li>\n"
                        + "\t\t<li>imageUrl: " + jo.get("imageUrl") + "</li>\n"
                        + "\t\t<li>budget: " + jo.get("budget") + "</li>\n"
                        + "\t</ul>\n"
                        + "</body>\n"
                        + "</html>";

        JoboonjaServer.sendResponse(httpExchange, response);
    }
}
