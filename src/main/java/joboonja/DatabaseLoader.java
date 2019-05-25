package joboonja;

import org.json.simple.parser.JSONParser;
import joboonja.domain.Database;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class DatabaseLoader extends TimerTask {

    @Override
    public void run() {
        loadProjects();
    }

    private static String HttpResponseToString(HttpResponse response) throws IOException {
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        StringBuilder builder = new StringBuilder();

        String line;

        while ((line = bufReader.readLine()) != null) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    public void loadSkills() throws SQLException, IOException, ParseException {
        Database db = Database.getInstance();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String jsonString = HttpResponseToString(client.execute(new HttpGet("http://142.93.134.194:8000/joboonja/skill")));
        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (Object o : ja) {
            db.addSkill((String) ((JSONObject)o).get("name"));
        }
    }

    public void loadProjects() {
        try {
            Database db = Database.getInstance();
            CloseableHttpClient client = HttpClientBuilder.create().build();
            String jsonString = HttpResponseToString(client.execute(new HttpGet("http://142.93.134.194:8000/joboonja/project")));
            JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
            for (Object o : ja) {
                db.addProject((JSONObject)o);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ParseException ex){
            ex.printStackTrace();
        }
    }

    public void loadProjectsPeriodically(int period) {
        loadProjects();
        Timer time = new Timer();
        time.schedule(this, 0, period);
    }

    public void loadDataBase() throws ParseException, SQLException, IOException {
        loadSkills();
        loadProjectsPeriodically(30000);
        AuctionHandler ah = new AuctionHandler();
        Timer time = new Timer();
        time.schedule(new AuctionHandler(), 0, 12000);
    }
}