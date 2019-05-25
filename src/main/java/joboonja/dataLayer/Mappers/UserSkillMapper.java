package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSkillMapper {

    private static final String COLUMNS = "userId, skillName";

    public UserSkillMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "UserSkill" + " " + "(userId CHAR(150), skillName CHAR(150)," +
                " PRIMARY KEY (userId, skillName), FOREIGN KEY (userId) REFERENCES User(username)," +
                " FOREIGN KEY (skillName) REFERENCES Skill(name))");

        st.close();
        con.close();
    }


    private String insertStatement() {
        return "INSERT INTO UserSkill" +
                " VALUES (?,?)" ;
    }

    private String deleteStatement() {
        return "DELETE" +
                " FROM UserSkill" +
                " WHERE userId = ? AND skillName = ?" ;
    }


    public int add(String userId, String skillName) throws SQLException {
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, userId);
            st.setString(2, skillName);
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.add query.");
                throw ex;
            }
        }
        return 0 ;
    }

    public int delete(String userId, String skillName) throws SQLException {
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(deleteStatement())
        ) {
            st.setString(1, userId);
            st.setString(2, skillName);
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.delete query.");
                throw ex;
            }
        }
        return 0 ;
    }



}