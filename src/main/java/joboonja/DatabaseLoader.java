package joboonja;

import joboonja.domain.Database;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class DatabaseLoader {

    static void contextInitialized() {
        Database db = Database.getInstance();
        try {
            loadData(db);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userInfo = "{\"username\":\"1\",\"firstName\":\"صادق\",\"lastName\":\"حایری\",\"jobTitle\":\"گیک\","
                + "\"skills\":[{\"name\":\"HTML\",\"point\":5},{\"name\":\"Javascript\",\"point\"4},"
                + "{\"name\":\"C\",\"point\":2},{\"name\":\"Java\",\"point\":3}],\"bio\":"
                + "\"" + "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"
                + "\"}";
        try {
            db.registerUser((JSONObject) new JSONParser().parse(userInfo));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userInfo = "{\"username\":\"2\",\"firstName\":\"محمدرضا\",\"lastName\":\"کیانی\",\"jobTitle\":\"چیف تی‌ای\","
                + "\"skills\":[{\"name\":\"Django\",\"point\":2},{\"name\":\"CSS\",\"point\"4},"
                + "{\"name\":\"Python\",\"point\":5}],\"bio\":\""
                + "پرواز را به خاطر بسپار؛ پرنده مردنی است."
                + "\"}";
        try {
            db.registerUser((JSONObject) new JSONParser().parse(userInfo));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userInfo = "{\"username\":\"3\",\"firstName\":\"آبتین\",\"lastName\":\"باطنی\",\"jobTitle\":\"توسعه‌دهنده‌ی فرانت‌اند\","
                + "\"skills\":[{\"name\":\"Java\",\"point\":4},{\"name\":\"Javascript\",\"point\"2},"
                + "{\"name\":\"React\",\"point\":3},{\"name\":\"Node.js\",\"point\":6},{\"name\":\"Linux\",\"point\":8}],\"bio\":"
                + "\"" + "خوشش بیاد خودش میاد، منت نکش."
                + "\"}";
        try {
            db.registerUser((JSONObject) new JSONParser().parse(userInfo));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void loadData(Database db) throws ParseException, IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        loadProjects(client, db);
        loadSkills(client, db);
    }

    private static String getRequestTo(String address, CloseableHttpClient client) throws IOException {
        HttpGet request = new HttpGet(address);
        HttpResponse response = client.execute(request);

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

    private static void loadProjects(CloseableHttpClient client, Database db) throws ParseException, IOException {
        String jsonString = getRequestTo("http://142.93.134.194:8000/joboonja/project", client);
        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (Object o : ja) {
            db.addProject((JSONObject)o);
        }
    }

    private static void loadSkills(CloseableHttpClient client, Database db) throws ParseException, IOException {
        String jsonString = getRequestTo("http://142.93.134.194:8000/joboonja/skill", client);

        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (Object o : ja) {
            db.addSkill((String) ((JSONObject)o).get("name"));
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
