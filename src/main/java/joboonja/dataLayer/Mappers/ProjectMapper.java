package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;
import joboonja.domain.model.Bid;
import joboonja.domain.model.Project;
import joboonja.domain.model.Skill;
import joboonja.domain.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectMapper {

    private static final String COLUMNS = "id, title, description, imageURL, budget, deadline, creationDate, winner";
    private Map<String, Project> loadedMap = new HashMap<>();
    private UserMapper userMapper ;

    public ProjectMapper(UserMapper userMapper) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Project" + " " + "(id CHAR(150) PRIMARY KEY, title CHAR(150)," +
                " description CHAR(150), imageURL CHAR(150), budget INTEGER, deadline LONG, creationDate LONG, winner CHAR(100))");

        st.close();
        con.close();
        this.userMapper = userMapper;
    }

    private String getStatement() {
        return "SELECT " + COLUMNS +
                " FROM Project " +
                "WHERE id = ?" ;
    }

    private String getSkillStatement() {
        return "SELECT skillName, points" +
                " FROM ProjectSkill" +
                " WHERE projectId = ?" ;
    }

    private String getBidStatement() {
        return "SELECT userId, bidAmount" +
                " FROM Bid" +
                " WHERE projectId = ?" ;
    }

    private String insertStatement() {
        return "INSERT IGNORE INTO Project" +
                " VALUES (?,?,?,?,?,?,?,?)" ;
    }

    private String getProjectListStatement() {
        return "SELECT " + COLUMNS +
                " FROM Project" +
                " ORDER BY creationDate DESC" ;
    }

    private String getSearchStatement() {
        return "SELECT " + COLUMNS +
                " FROM Project" +
                " WHERE title LIKE ? OR description LIKE ?" +
                " ORDER BY creationDate DESC";
    }

    private String setWinnerStatement() {
        return "UPDATE Project " +
                "SET winner = ? " +
                "WHERE id = ?" ;
    }

    private List<Skill> getSkill(String id) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getSkillStatement())
        ) {
            st.setString(1, id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()) {
                    String skillName = resultSet.getString(1) ;
                    int point = resultSet.getInt(2) ;
                    Skill addSkill = new Skill(skillName, point) ;
                    skills.add(addSkill) ;
                }
                con.close();
                st.close();
                return skills ;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getSkill query.");
                throw ex;
            }
        }
    }

    private List<Bid> getBid(Project p) throws SQLException {
        List<Bid> bids = new ArrayList<>( );
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getBidStatement())
        ) {
            st.setString(1, p.getID());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery() ;
                while (resultSet.next()) {
                    String biddingUser = resultSet.getString(1) ;
                    int bidAmount = resultSet.getInt(2) ;
                    User u = userMapper.get(biddingUser);
                    int points = p.bidPointsCalc(u, bidAmount);
                    bids.add(new Bid(u, p ,bidAmount, points));
                }
                return bids ;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getBid query.");
                throw ex;
            }
        }
    }

    User getUser(String username) throws SQLException {
        return userMapper.get(username);

    }

    private Project convertResultSetToDomainModel (ResultSet rs, String id) throws SQLException {
        return new Project(rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                getSkill(id),
                rs.getInt(5),
                rs.getLong(6),
                rs.getLong(7),
                getUser(rs.getString(8))
        ) ;
    }

    public Project get(String id) throws SQLException{
        Project result = loadedMap.get(id);
        if (result != null)
            return result;

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            st.setString(1, id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                Project projectResult = convertResultSetToDomainModel(resultSet, id) ;
                projectResult.setBids(getBid(projectResult));
                loadedMap.put(id, projectResult) ;
                con.close();
                st.close();
                return projectResult;
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.get query.");
                throw ex;
            }
        }
    }

    private boolean contains(String id) throws SQLException {
        if (loadedMap.get(id) != null) {
            return true;
        }

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            st.setString(1, id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return resultSet.next() ;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.contains query.");
                throw ex;
            }
        }
    }

    public int add(Project newProject) throws SQLException {
        if(this.contains(newProject.getID())){
            return -1;
        }

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, newProject.getID());
            st.setString(2, newProject.getTitle());
            st.setString(3, newProject.getDescription());
            st.setString(4, newProject.getImageURL());
            st.setInt(5, newProject.getBudget());
            st.setLong(6, newProject.getDeadline());
            st.setLong(7, newProject.getCreationDate());
            st.setString(8, null);
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.add query.");
                throw ex;
            }
        }
        return 0 ;
    }

    private List<Project> filter(List<Project> projectList, List<Skill> skills) {
        List<Project> applicableList = new ArrayList<>() ;
        for (Project p:projectList){
            if(p.skillsPointCalc(skills)>=0){
                applicableList.add(p) ;
            }
        }
        return applicableList;
    }


    public List<Project> getApplicables(List<Skill> skills) throws SQLException {
        List<Project> projectList = getList() ;
        return filter(projectList, skills) ;
    }

    private List<Project> executeListQuery(PreparedStatement st) throws SQLException {
        List<Project> resultProjectList = new ArrayList<>();
        ResultSet resultSet = st.executeQuery() ;
        while(resultSet.next()) {
            Project p = convertResultSetToDomainModel(resultSet, resultSet.getString(1));
            p.setBids(getBid(p));
            resultProjectList.add(p) ;
        }

        return resultProjectList;
    }

    public List<Project> getList() throws SQLException {
        List<Project> resultProjectList;
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getProjectListStatement())
        ) {
            try {
                resultProjectList = executeListQuery(st);
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getList query.");
                throw ex;
            }
        }
        return resultProjectList ;
    }

    public List<Project> searchProjects(List<Skill> skills, String searchKey) throws SQLException {
        List<Project> resultProjectList ;
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getSearchStatement())
        ) {
            st.setString(1, "%"+searchKey+"%");
            st.setString(2, "%"+searchKey+"%");
            try {
                resultProjectList = executeListQuery(st);
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.searchProjects query.");
                throw ex;
            }
        }

        return filter(resultProjectList, skills);
    }

    public void setWinner(String projectID, String winner){
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(setWinnerStatement())
        ) {
            System.out.println(winner);
            st.setString(1, winner);
            st.setString(2, projectID);
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.setWinner query.");
                throw ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
