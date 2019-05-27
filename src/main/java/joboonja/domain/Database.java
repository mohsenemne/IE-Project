package joboonja.domain;

import joboonja.dataLayer.Mappers.*;
import joboonja.domain.model.*;
//import joboonja.domain.repo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class Database {
    private static Database db = null;
    private UserMapper userMapper ;
    private ProjectMapper projectMapper ;
    private BidMapper bidMapper ;
    private SkillMapper skillMapper ;
    private EndorsementMapper endorsementMapper ;
    private ProjectSkillMapper projectSkillMapper ;
    private UserSkillMapper userSkillMapper ;

//    private ProjectRepo projects;
//    private UserRepo users;
//    private BidRepo bids;
//    private SkillsRepo skills;
//    private EndorsementRepo endorsements;

    private Database() throws SQLException {
        userMapper = new UserMapper();
        projectMapper = new ProjectMapper(userMapper);
        bidMapper = new BidMapper(userMapper, projectMapper);
        skillMapper = new SkillMapper() ;
        endorsementMapper = new EndorsementMapper(userMapper) ;
        projectSkillMapper = new ProjectSkillMapper() ;
        userSkillMapper = new UserSkillMapper() ;
    }

    public static Database getInstance() throws SQLException
    {
        if (db == null)
            db = new Database();

        return db;
    }

    public boolean registerUser(User newUser) throws SQLException, NoSuchAlgorithmException {
        List<Skill> skills = newUser.getSkills() ;
        String username = newUser.getUsername() ;
        if(userMapper.add(newUser) < 0)
            return false;
        for(Skill s: skills){
            System.out.println("add user skill");
            userSkillMapper.add(username, s.getName());
        }
        return true;
    }

    public boolean loginUser(User newUser) throws SQLException, NoSuchAlgorithmException {
        if(userMapper.get(newUser.getUsername(), newUser.getPassword()) != null){
            return true ;
        }
        return false ;
    }

    public void auction(Project p){
        User winner = p.auction();
        if(winner != null){
            projectMapper.setWinner(p.getID(), winner.getUsername());
        }
    }

    public int addProject(JSONObject jo) throws SQLException {
        String id = (String) jo.get("id");
        String title = (String) jo.get("title");
        String description = (String) jo.get("description");
        String imageURL = (String) jo.get("imageUrl");
        List<Skill> requiredSkills = Skill.parseSkills((JSONArray)jo.get("skills"));
        int budget = (int)(long)jo.get("budget");
        long deadline = (long)jo.get("deadline");
        long creationDate = (long)jo.get("creationDate");

        Project newProject = new Project(id, title, description, imageURL, requiredSkills, budget, deadline, creationDate, null);
        if(projectMapper.add(newProject) < 0)
            return -1;
        for(Skill s: requiredSkills){
            System.out.println("add project skill");
            projectSkillMapper.add(s.getName(), id, s.getPoints());
        }
        return 0;
    }

    public int addBid(String biddingUser, String projectID, int bidAmount) throws SQLException {
        User user = userMapper.get(biddingUser);
        if(user == null)
            return -1;

        Project project = projectMapper.get(projectID);
        if(project == null)
            return -2;

        int points = project.bidPointsCalc(user, bidAmount);
        if(points < 0)
            return points - 2;

        Bid newBid = new Bid(user, project, bidAmount, points);
        if(bidMapper.add(newBid) == 0)
            project.addBid(newBid);

        return 0;
    }

    public int addSkill(String skillName) throws SQLException  {
        return skillMapper.add(skillName);
    }

    public User getUser(String username) throws SQLException {
        return userMapper.get(username);
    }

    public Project getProject(String projectID) throws SQLException {
        return projectMapper.get(projectID);
    }

    public List<Project> getProjects() throws SQLException {
        return projectMapper.getList();
    }

    public List<Project> getApplicableProjects(String username) throws SQLException {
        User user = userMapper.get(username);
        if(user == null)
            return null;
        return projectMapper.getApplicables(user.getSkills());
    }

    public List<User> getUsersList() throws SQLException {
        return userMapper.getList();
    }

//    public boolean hasBidded(String username, String projectID) {
//        User user = users.get(username);
//        Project project = projects.get(projectID);
//
//        return bids.hasBidded(user, project);
//    }
//
//    public boolean hasEndorsed(User endorser, User target, String skill){
//        return endorsements.hasEndorsed(endorser, target, skill);
//    }

    public boolean endorse(String endorser, String target, String skill) throws SQLException {
        User endrsr = userMapper.get(endorser);
        if (endrsr == null)
            return false;
        User trgt = userMapper.get(target);
        if (trgt == null)
            return false;
        trgt.incPoint(skill);
        return endorsementMapper.addEndorsment(endrsr, trgt, skill);
    }

    public boolean deleteSkill(String skillName, String username) throws SQLException {
        User u = userMapper.get(username);
        if (u == null)
            return false;
        if(u.deleteSkill(skillName)){
            userSkillMapper.delete(username, skillName);
            return true;
        }
        return false;
    }

    public boolean addSkill(String skillName, String username) throws SQLException {
        User u = userMapper.get(username);
        if (u == null)
            return false;
        if(u.addSkill(skillName)){
            userSkillMapper.add(username, skillName);
            return true;
        }
        return false;
    }

    public List<String> getSkills(String username) throws SQLException {
        User u = userMapper.get(username);
        return skillMapper.getList(Skill.getNames(u.getSkills()));
    }

    public List<Bid> getBids(String projectID) throws SQLException {
        return bidMapper.getBids(projectID);
    }

    public List<Endorsement> getEndorsements(String endorser, String target) throws SQLException {
        return endorsementMapper.getEndorsments(endorser, target);
    }

    public List<Project> searchProjects(String username, String searchKey) throws SQLException {
        User user = userMapper.get(username);
        if(user == null)
            return null;
        return projectMapper.searchProjects(user.getSkills(), searchKey);
    }

    public List<User> searchUsers(String searchKey) throws SQLException {
        return userMapper.searchUsers(searchKey);
    }
}