import java.io.IOException;

import joboonja.database.Database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {
//    private static Scanner scanner = new Scanner(System.in);
//    private static boolean isFinished = false;

    public static void main(String[] args) throws Exception {
        Database db = Database.getInstance();
        loadData(db);

        String userInfo = "{\"username\":\"1\",\"firstName\":\"علی\",\"lastName\":\"شریف‌زاده\",\"jobTitle\":\"برنامه‌نویس وب\","
                + "\"skills\":[{\"name\":\"HTML\",\"point\":5},{\"name\":\"Javascript\",\"point\"4},"
                + "{\"name\":\"C++\",\"point\":2},{\"name\":\"Java\",\"point\":3}],\"bio\":"
                + "\"" + "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"
                + "\"}";
        db.registerUser((JSONObject) new JSONParser().parse(userInfo));
//        JoboonjaServer server = new JoboonjaServer();
//        server.startServer();
        // should create and start server here...

//        while (!isFinished) {
//            Pair<String, String> commandParts = getCommandParts();
//            String commandName = commandParts.getKey();
//            String commandData = commandParts.getValue();

//            switch (commandName) {
//                case "register":
//                    register(commandData);
//                    break;
//                case "addProject":
//                    addProject(commandData);
//                    break;
//                case "bid":
//                    bid(commandData);
//                    break;
//                case "auction":
//                    auction(commandData);
//                    isFinished = true;
//                    break;
//            }
//        }
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
            db.addSkill((JSONObject)o);
        }
    }

//    private static Pair<String, String> getCommandParts() {
//        String command = scanner.nextLine();
//        int spaceIndex = command.indexOf(" ");
//        return new Pair<String, String>(command.substring(0, spaceIndex), command.substring(spaceIndex+1));
//    }

//    private static void auction(String jsonString) throws ParseException {
//        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
//        Project project = projects.get((String) jo.get("projectID"));
//        User winner = project.auction();
//        System.out.println("winner: " + winner.getUsername());
//    }
}