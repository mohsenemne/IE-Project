package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;
import joboonja.domain.model.Skill;
import joboonja.domain.model.User;


import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;

public class UserMapper {

    private static final String COLUMNS = "username, password, firstName, lastName, jobTitle, bio, profilePictureURL";
    private Map<String, User> loadedMap = new HashMap<>();


    public UserMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "User" + " " + "(username CHAR(10) PRIMARY KEY, password CHAR(100) ,firstName CHAR(150)," +
                " lastName CHAR(150), jobTitle CHAR(250), bio CHAR(250), profilePictureURL CHAR(200))");

        st.close();
        con.close();
    }


    private String getStatement(boolean pass) {
        return "SELECT " + COLUMNS +
                " FROM User" +
                " WHERE username = ?" + (pass?" AND password = ?":"");
    }

    private String insertStatement() {
        return "INSERT INTO User" +
                " VALUES (?,?,?,?,?,?,?)" ;
    }

    private String getSkillNameStatement() {
        return "SELECT skillName" +
                " FROM UserSkill" +
                " WHERE userId = ?" ;
    }

    private String getSkillPointStatement() {
        return "SELECT COUNT(*) AS cnt" +
                " FROM Endorsement" +
                " WHERE endorsedId = ? AND skillName = ?" ;
    }

    private String getUserListStatement() {
        return "SELECT " + COLUMNS +
                " FROM User" ;
    }

    private String getSearchStatement() {
        return "SELECT " + COLUMNS +
                " FROM User" +
                " WHERE firstName LIKE ? OR lastName LIKE ?" ;
    }

    private String encrypt(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(data.getBytes());
        return new String(messageDigest.digest());
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
                con.close();
                st.close();
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
                while(resultSet.next()){
                    String skillName = resultSet.getString(1) ;
                    int point = getSkillPoint(username, skillName) ;
                    Skill addSkill = new Skill(skillName, point) ;
                    skills.add(addSkill) ;
                }
                con.close();
                st.close();
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
                rs.getString(5),
                getSkill(username),
                rs.getString(6),
                rs.getString(7)
        );
    }

    private User executeGetQuery(Connection con, PreparedStatement st, String username) throws SQLException {
        ResultSet resultSet = st.executeQuery();
        resultSet.next();
        User userResult = convertResultSetToDomainModel(resultSet, username) ;
        loadedMap.put(username, userResult) ;
        con.close();
        st.close();
        return userResult;
    }

    public User get(String username) throws SQLException {
        if(username == null)
            return null;
        User result = loadedMap.get(username);
        if (result != null)
            return result;

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement(false))
        ) {
            st.setString(1, username);
            try {
                return executeGetQuery(con, st, username);
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.get query.");
                throw ex;
            }
        }
    }

    public User get(String username, String password) throws SQLException, NoSuchAlgorithmException {
        User result = loadedMap.get(username);
        String encryptedPass = encrypt(password) ;
        if (result != null)
            if(result.getPassword().equals(encryptedPass))
                return result;
            else
                return null;
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement(true))
        ) {
            st.setString(1, username);
            st.setString(2, encryptedPass);
            try {
                return executeGetQuery(con, st, username);
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.getLog query.");
                throw ex;
            }
        }
    }

    private boolean contains(String username) throws SQLException {
        if (loadedMap.get(username) != null) {
            return true;
        }

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement(false))
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


    public int add(User newUser) throws SQLException, NoSuchAlgorithmException {
        if(this.contains(newUser.getUsername())){
            return -1;
        }

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, newUser.getUsername());
            st.setString(2, encrypt(newUser.getPassword()));
            st.setString(3, newUser.getFirstName());
            st.setString(4, newUser.getLastName());
            st.setString(5, newUser.getJobTitle());
            st.setString(6, newUser.getBio());
            st.setString(7, newUser.getProfilePictureURL());
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.add query.");
                throw ex;
            }
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("error in encryption.");
            throw ex;
        }

        return 0;
    }

    private List<User> executeListQuery(Connection con, PreparedStatement st) throws SQLException {
        List<User> resultUserList = new ArrayList<>();
        ResultSet resultSet = st.executeQuery();
        while(resultSet.next()) {
            resultUserList.add(convertResultSetToDomainModel(resultSet, resultSet.getString(1))) ;
        }
        con.close();
        st.close();
        return resultUserList;
    }

    public List<User> getList() throws SQLException {
        List<User> resultUserList = new ArrayList<>();
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getUserListStatement())
        ) {
            try {
                resultUserList = executeListQuery(con, st);
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.getList query.");
                throw ex;
            }
        }
        return resultUserList ;
    }

    public List<User> searchUsers(String searchKey) throws SQLException {
        List<User> resultUserList ;
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getSearchStatement())
        ) {
            st.setString(1, "%"+searchKey+"%");
            st.setString(2, "%"+searchKey+"%");
            try {
                resultUserList = executeListQuery(con, st);
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.searchProjects query.");
                throw ex;
            }
        }

        return resultUserList;
    }
}