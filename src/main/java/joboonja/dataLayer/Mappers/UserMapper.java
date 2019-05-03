package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;
import joboonja.domain.model.Skill;
import joboonja.domain.model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper {

    private static final String COLUMNS = "username, firstName, lastName, jobTitle, bio, profilePictureURL";
    private Map<String, User> loadedMap = new HashMap<>();


    public UserMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "User" + " " + "(username TEXT PRIMARY KEY, firstName TEXT," +
                " lastName TEXT, jobTitle TEXT, bio TEXT, profilePictureURL TEXT)");

        st.close();
        con.close();
    }


    private String getStatement() {
        return "SELECT " + COLUMNS +
                " FROM User" +
                " WHERE username = ?";
    }

    private String insertStatement() {
        return "INSERT INTO User" +
                " VALUES (?,?,?,?,?,?)" ;
    }

    private String getSkillNameStatement() {
        return "SELECT skillName, point" +
                " FROM UserSkill" +
                " WHERE userId = ?" ;
    }

    private String getSkillPointStatement() {
        return "SELECT COUNT(*) AS cnt" +
                " FROM Endorsement" +
                " WHERE endorsedId = ? AND skillName = ?" ;
    }

    private int getSkillPoint(String username, String skillName) throws SQLException {

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getSkillPointStatement())
        ) {
            st.setString(1, username);
            st.setString(2, skillName);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()){
                    return  resultSet.getInt("cnt") ;
                }
                return 0 ;
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.getSkillPoint query.");
                throw ex;
                }
        }
    }

    private List<Skill> getSkill(String username) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getSkillNameStatement())
        ) {
            st.setString(1, username);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while(!resultSet.next()){
                    String skillName = resultSet.getString(1) ;
                    int point = getSkillPoint(username, skillName) ;
                    Skill addSkill = new Skill(skillName, point) ;
                    skills.add(addSkill) ;
                }
                return skills ;
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.getSkill query.");
                throw ex;
            }
        }
    }


    private User convertResultSetToDomainModel(ResultSet rs, String username) throws SQLException {
        return new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                getSkill(username),
                rs.getString(5),
                rs.getString(6)
        );
    }


    public User get(String username) throws SQLException {
        User result = loadedMap.get(username);
        if (result != null)
            return result;

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            st.setString(1, username);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                User userResult = convertResultSetToDomainModel(resultSet, username) ;
                loadedMap.put(username, userResult) ;
                return userResult;
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.get query.");
                throw ex;
            }
        }
    }

    private boolean contains(String username) throws SQLException {
        if (loadedMap.get(username) != null) {
            return true;
        }

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            st.setString(1, username);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return resultSet.next() ;
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.contains query.");
                throw ex;
            }
        }
    }


    public int add(User newUser) throws SQLException{
        if(this.contains(newUser.getUsername())){
            return -1;
        }

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, newUser.getUsername());
            st.setString(2, newUser.getFirstName());
            st.setString(3, newUser.getLastName());
            st.setString(4, newUser.getJobTitle());
            st.setString(5, newUser.getBio());
            st.setString(6, newUser.getProfilePictureURL());
            try {
                st.executeUpdate() ;
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.add query.");
                throw ex;
            }
        }

        return 0;
    }

}
