package joboonja.server;

import com.sun.net.httpserver.HttpExchange;
import joboonja.Database;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;

public class ProjectsListPage implements IPage{
    public void HandleRequest(HttpExchange httpExchange, String id, String applicantUser) throws IOException, ParseException {
        Database db = Database.getInstance();
        String projectsList = db.getApplicableProjectsList(applicantUser);
        JSONArray ja = (JSONArray) new JSONParser().parse(projectsList);
        String response =
                "<html>"
                        + "<head>\n"
                        + "\t<meta charset=\"UTF-8\">\n"
                        + "\t<title>Projects</title>\n"
                        + "\t<style>\n"
                        + "\t\ttable {\n"
                        + "\t\t\ttext-align: center;\n"
                        + "\t\t\tmargin: 0 auto;\n"
                        + "\t\t}\n"
                        + "\t\ttd {\n"
                        + "\t\t\tmin-width: 300px;\n"
                        + "\t\t\tmargin: 5px 5px 5px 5px;\n"
                        + "\t\t\ttext-align: center;\n"
                        + "\t\t}\n"
                        + "\t</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "\t<table>\n"
                        + "\t\t<tr>\n"
                        + "\t\t\t<th>id</th>\n"
                        + "\t\t\t<th>title</th>\n"
                        + "\t\t\t<th>budget</th>\n"
                        + "\t</tr>\n";

        for(Object o : ja){
            JSONObject jo = (JSONObject)o;
            response += "\t\t<tr>\n"
                    + "\t\t\t<td>" + jo.get("id") + "</td>\n"
                    + "\t\t\t<td>" + jo.get("title") + "</td>\n"
                    + "\t\t\t<td>" + jo.get("budget") + "</td>\n"
                    + "\t\t\t</tr>\n";
        }

        response += "\t</table>\n"
                + "</body>\n"
                + "</html>\n";

        JoboonjaServer.sendResponse(httpExchange, response);
    }
}
