package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectSkillMapper {

    private static final String COLUMNS = "skillName, projectId, points";

    public ProjectSkillMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "ProjectSkill" + " " + "(skillName CHAR(150), projectId CHAR(150), points INTEGER," +
                " PRIMARY KEY (skillName, projectId), FOREIGN KEY (skillName) REFERENCES Skill(name)," +
                " FOREIGN KEY (projectId) REFERENCES Project(id))");

        st.close();
        con.close();
    }

    private String insertStatement() {
        return "INSERT INTO ProjectSkill" +
                " VALUES (?,?,?)" ;
    }

    public int add(String skillName, String projectId, int points) throws SQLException {
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, skillName);
            st.setString(2, projectId);
            st.setInt(3, points);
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in ProjectSkillMapper.add query.");
                throw ex;
            }
        }
        return 0 ;
    }
}