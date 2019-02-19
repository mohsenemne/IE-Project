import javafx.util.Pair;

import java.io.IOException;
import java.util.Scanner;

import jobunja.model.*;
import jobunja.repo.*;

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

    private static ProjectRepo projects = new ProjectRepo();
    private static UserRepo users = new UserRepo();
    private static BidRepo bids = new BidRepo();
    private static SkillsRepo skills = new SkillsRepo();

    public static void main(String[] args) throws ParseException, IOException {
        loadData();
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    register(commandData);
                    break;
                case "addProject":
                    addProject(commandData);
                    break;
                case "bid":
                    bid(commandData);
                    break;
                case "auction":
                    auction(commandData);
                    isFinished = true;
                    break;
            }
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
            String skillName = (String) ((JSONObject)o).get("name");
            skills.add(skillName);
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<String, String>(command.substring(0, spaceIndex), command.substring(spaceIndex+1));
    }

    private static void register(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        User newUser = new User(jo);
        if(users.add(newUser) < 0){
            System.out.println("Username " + newUser.getUsername() + " is already taken!");
        }
    }

    private static void addProject(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Project newProject = new Project(jo);
        if(projects.add(newProject) < 0){
            System.out.println("projectID " + newProject.getID() + " is already taken!");
        }
    }

    private static void bid(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);

        User biddingUser = users.get((String) jo.get("biddingUser"));
        if(biddingUser == null){
            System.out.println("User " + jo.get("biddingUser") + " doesn't exist!");
            return;
        }

        Project project = projects.get((String) jo.get("projectID"));
        if(project == null){
            System.out.println("Project " + jo.get("projectID") + " doesn't exist!");
            return;
        }

        int bidAmount = (int)(long)jo.get("bidAmount");
        int skillsPoint = project.skillsPoint(biddingUser.getSkills()) ;
        int offerPoint = project.getBudget() - bidAmount ;
        if(skillsPoint<0 || offerPoint<0){
            System.out.println("Your offer doesn't satisfy project's requirements!");
            return;
        }

        Bid newBid = new Bid(biddingUser, project, bidAmount, skillsPoint + offerPoint);
        if(bids.add(newBid) == 0){
            project.addBid(newBid);
        }
    }

    private static void auction(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Project project = projects.get((String) jo.get("projectID"));
        User winner = project.auction();
        System.out.println("winner: " + winner.getUsername());
    }
}