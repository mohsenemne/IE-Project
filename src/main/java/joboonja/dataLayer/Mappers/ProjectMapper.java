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

    private static final String COLUMNS = "id, title, description, imageURL, budget, deadline, winner";
    private Map<String, Project> loadedMap = new HashMap<>();
    private UserMapper userMapper ;

    public ProjectMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Project" + " " + "(id TEXT PRIMARY KEY, title TEXT," +
                " description TEXT, imageURL TEXT, budget INTEGER, deadline LONG, winner TEXT)");

        st.close();
        con.close();
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

//    private String getBidStatement() {
//        return "SELECT userId, bidAmount, points" +
//                " FROM Bid" +
//                " WHERE projectId = ?" ;
//    }

    private String insertStatement() {
        return "INSERT INTO Project" +
                " VALUES (?,?,?,?,?,?,?)" ;
    }

    private String applicableStatement() {
        return "SELECT projectId" +
                " FROM ProjectSkill" +
                " WHERE skillName = ? AND points <= ?" ;
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
                return skills ;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getSkill query.");
                throw ex;
            }
        }
    }

//    private List<Bid> getBid(String id) throws SQLException {
//        List<Bid> bids = new ArrayList<>( );
//        try (Connection con = DBCPDataSource.getConnection();
//             PreparedStatement st = con.prepareStatement(getBidStatement())
//        ) {
//            st.setString(1, id);
//            ResultSet resultSet;
//            try {
//                resultSet = st.executeQuery() ;
//                while (!resultSet.next()) {
//                    String biddingUser = resultSet.getString(1) ;
//                    int bidAmount = resultSet.getInt(2) ;
//                    int points = resultSet.getInt(3) ;
//                    Bid addBid = new Bid(userMapper.get(biddingUser), id ,bidAmount, points) ;
//                }
//                return bids ;
//            } catch (SQLException ex) {
//                System.out.println("error in ProjectMapper.getBid query.");
//                throw ex;
//            }
//        }
//    }

    private Project convertResultSetToDomainModel (ResultSet rs, String id) throws SQLException {
        return new Project(rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                getSkill(id),
                rs.getInt(5),
                rs.getLong(6)
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
                loadedMap.put(id, projectResult) ;
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
            st.setString(7, newProject.auction().getUsername());
            try {
                st.executeUpdate() ;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.add query.");
                throw ex;
            }
        }
        return 0 ;
    }


    public List<Project> getApplicables(List<Skill> skills) throws SQLException {
        List<Project> applicables = new ArrayList<>();
        for (Skill s:skills) {
            try (Connection con = DBCPDataSource.getConnection();
                 PreparedStatement st = con.prepareStatement(applicableStatement())
            ) {
                st.setString(1, s.getName());
                st.setInt(2, s.getPoints());
                ResultSet resultSet;
                try {
                    resultSet = st.executeQuery() ;
                    while (resultSet.next()) {
                        String tempProjectId = resultSet.getString(1) ;
                        Project tempProject = get(tempProjectId) ;
                        if (tempProject.skillsPointCalc(skills) >= 0)
                            applicables.add(tempProject) ;
                    }
                } catch (SQLException ex) {
                    System.out.println("error in ProjectMapper.applicable query.");
                    throw ex;
                }
            }
        }
        return applicables ;
    }
}
