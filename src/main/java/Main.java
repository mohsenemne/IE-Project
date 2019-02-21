import javafx.util.Pair;

import java.io.IOException;
import java.util.Scanner;

import joboonja.model.*;
import joboonja.repo.*;

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
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) throws ParseException, IOException {
        loadData();
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

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
        }
    }

    private static void loadData() throws ParseException, IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        loadProjects(client);
        loadSkills(client);
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

    private static void loadProjects(CloseableHttpClient client) throws ParseException, IOException {
        String jsonString = getRequestTo("http://142.93.134.194:8000/joboonja/project", client);
        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (Object o : ja) {
            Project project = new Project((JSONObject) o);
            projects.add(project);
        }
    }

    private static void loadSkills(CloseableHttpClient client) throws ParseException, IOException {
        String jsonString = getRequestTo("http://142.93.134.194:8000/joboonja/skill", client);
        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (Object o : ja) {
            skills.add((JSONObject)o);
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<String, String>(command.substring(0, spaceIndex), command.substring(spaceIndex+1));
    }

//    private static void auction(String jsonString) throws ParseException {
//        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
//        Project project = projects.get((String) jo.get("projectID"));
//        User winner = project.auction();
//        System.out.println("winner: " + winner.getUsername());
//    }
}