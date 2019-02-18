import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jobunja.model.*;
import jobunja.repo.*;

import org.apache.http.client.ClientProtocolException;
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

    private static ProjectRepo projects;
    private static UserRepo users;
    private static BidRepo bids;
    private static SkillsRepo skills;

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

    private static String getRequestTo(CloseableHttpClient client, String address) throws IOException {
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
        String jsonString = getRequestTo(client, "http://142.93.134.194:8000/joboonja/project");
        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (int i=0; i<ja.size(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            Project project = createProject(jo);
            projects.add(project);
        }
    }

    private static void loadSkills(CloseableHttpClient client) throws ParseException, IOException {
        String jsonString = getRequestTo(client, "http://142.93.134.194:8000/joboonja/skill");
        JSONArray ja = (JSONArray) new JSONParser().parse(jsonString);
        for (int i=0; i<ja.size(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            String skillName = (String) jo.get("name");
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
        User newUser = createUser(jo);
        if(users.add(newUser) < 0){
            System.out.println("Username " + newUser.getUsername() + " is already taken!");
        }
    }

    private static void addProject(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Project newProject = createProject(jo);
        if(projects.add(newProject) < 0){
            System.out.println("projectID " + newProject.getID() + " is already taken!");
        }
    }

    private static void bid(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Bid newBid = createBid(jo);
        User biddingUser = newBid.getBiddingUser();
        Project project = newBid.getProject();

        if(biddingUser == null){
            System.out.println("User " + newBid.getBiddingUser().getUsername() + " doesn't exist!");
            newBid = null;
        }
        if(project == null){
            System.out.println("Project " + newBid.getProject().getID() + " doesn't exist!");
            newBid = null;
        }
        if(newBid != null){
            if(bids.add(newBid) == 0){
                newBid.getProject().addBid(newBid);
            }
        }
    }

    private static void auction(String jsonString) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(jsonString);
        Project project = projects.get((String) jo.get("projectID"));
        User winner = project.auction();
        System.out.println("winner: " + winner.getUsername());
    }

    private static List<Skill> parseSkills(JSONArray skillsJA) {
        List<Skill> skills = new ArrayList<Skill>();
        for (int i=0; i<skillsJA.size(); i++) {
            JSONObject skillJO = (JSONObject) skillsJA.get(i);
            String skillName = (String) skillJO.get("name");
            int skillPoints = (int) (long) skillJO.get("points");

            skills.add(new Skill(skillName, skillPoints));
        }
        return skills;
    }

    private static User createUser(JSONObject jo) {
        String username = (String) jo.get("username");
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");
        String jobTitle = (String) jo.get("jobTitle");
        List<Skill> skills = parseSkills((JSONArray)jo.get("skills"));
        String bio = (String) jo.get("bio");

        return new User(username, firstName, lastName, jobTitle, skills, bio);
    }

    private static Project createProject(JSONObject jo) {
        String id = (String) jo.get("id");
        String title = (String) jo.get("title");
        String description = (String) jo.get("description");
        String imageURL = (String) jo.get("imageURL");
        List<Skill> skills = parseSkills((JSONArray)jo.get("skills"));
        int budget = (int)(long)jo.get("budget");
        long deadline = (long)jo.get("deadline");

        return new Project(id, title, description, imageURL, skills, budget, deadline);
    }

    private static Bid createBid(JSONObject jo) {
        String biddingUser = (String) jo.get("biddingUser");
        String projectID = (String) jo.get("projectID");
        int bidAmount = (int)(long)jo.get("bidAmount");

        User user = users.get(biddingUser);
        Project project = projects.get(projectID);

        return new Bid(user, project, bidAmount);
    }
}