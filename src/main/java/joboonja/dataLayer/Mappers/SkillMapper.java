package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillMapper {

    private static final String COLUMNS = "name";

    public SkillMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Skill" + " " + "(name TEXT," +
                " PRIMARY KEY (name))");

        st.close();
        con.close();
    }

    private String insertStatement() {
        return "INSERT INTO Skill" +
                " VALUES (?)" ;
    }

    private String getStatement() {
        return "SELECT " + COLUMNS +
                " FROM Skill" ;
    }

    public int add(String name) throws SQLException {
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, name);
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in SkillMapper.add query.");
                throw ex;
            }
        }
        return 0 ;
    }


    public List<String> getList(List<String> userSkills) throws SQLException {
        List<String> skills = new ArrayList<>();
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery() ;
                while(resultSet.next()){
                    if(!userSkills.contains(resultSet.getString(1))){
                        skills.add(resultSet.getString(1)) ;
                    }
                }
                con.close();
                st.close();
                return skills ;
            } catch (SQLException ex) {
                System.out.println("error in SkillMapper.getSkill query.");
                throw ex;
            }
        }
    }
}
