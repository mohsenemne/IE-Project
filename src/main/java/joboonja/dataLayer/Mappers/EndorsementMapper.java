package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;
import joboonja.domain.model.Endorsement;
import joboonja.domain.model.Project;
import joboonja.domain.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EndorsementMapper {

    private static final String COLUMNS = "endorserId, endorsedId, skillName";
    private UserMapper userMapper ;

    public EndorsementMapper() throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Endorsement" + " " + "(endorserId TEXT, endorsedId TEXT," +
                " skillName TEXT, PRIMARY KEY (endorserId, endorsedId, skillName), FOREIGN KEY (endorserId) REFERENCES User(username)," +
                " FOREIGN KEY (endorsedId) REFERENCES User(username), FOREIGN KEY (skillName) REFERENCES Skill(name))");

        st.close();
        con.close();
    }

    private String getStatement() {
        return "SELECT skillName" +
                " FROM Endorsement" +
                " WHERE endorserId = ? AND endorsedId = ?" ;
    }

    private String insertStatement() {
        return "INSERT INTO Endorsement" +
                " VALUES (?,?,?)" ;
    }

    private String hasEndorsedStatement() {
        return "SELECT " + COLUMNS +
                " FROM Endorsement" +
                " WHERE endorserId = ? AND endorsedId = ? AND skillName = ?" ;
    }

    private Endorsement convertResultSetToDomainModel(String endorser, String endorsed, String skillName) throws SQLException {
        User endorserU = userMapper.get(endorser) ;
        User endorsedU = userMapper.get(endorsed) ;
        return new Endorsement(endorserU,
                endorsedU,
                skillName
        );
    }

    public List<Endorsement> getEndorsments(String endorser, String endorsed) throws SQLException {
        List<Endorsement> resultEndorsement = new ArrayList<>() ;

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            st.setString(1, endorser);
            st.setString(2, endorsed);
            ResultSet resultSet ;
            try {
                resultSet = st.executeQuery() ;
                while(resultSet.next()) {
                    String skillName = resultSet.getString(1) ;
                    resultEndorsement.add(convertResultSetToDomainModel(endorser, endorsed, skillName)) ;
                }
                return resultEndorsement ;
            } catch (SQLException ex) {
                System.out.println("error in EndorsementMapper.getEndorsement query.");
                throw ex;
            }
        }
    }

    public boolean addEndorsment(User endorser, User endorsed, String skill) throws SQLException {
        if(hasEndorsed(endorser, endorsed, skill))
            return false ;
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, endorser.getUsername());
            st.setString(2, endorsed.getUsername());
            st.setString(3, skill);
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in EndorsementMapper.add query.");
                throw ex;
            }
        }

        return true ;
    }

    private boolean hasEndorsed(User endorser, User endorsed, String skill) throws SQLException {
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(hasEndorsedStatement())
        ) {
            st.setString(1, endorser.getUsername());
            st.setString(2, endorsed.getUsername());
            st.setString(3, skill);
            ResultSet resultSet ;
            try {
                resultSet = st.executeQuery() ;
                return resultSet.next() ;
            } catch (SQLException ex) {
                System.out.println("error in EndorsementMapper.hasEndorsed query.");
                throw ex;
            }
        }
    }
}
