package joboonja.dataLayer.Mappers;

import joboonja.dataLayer.DBCPDataSource;
import joboonja.domain.model.Bid;
import joboonja.domain.model.Project;
import joboonja.domain.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidMapper {

    private static final String COLUMNS = "userId, projectId, bidAmount";
    private UserMapper userMapper ;
    private ProjectMapper projectMapper ;

    public BidMapper(UserMapper userMapper, ProjectMapper projectMapper) throws SQLException {
        Connection con = DBCPDataSource.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Bid" + " " + "(userId CHAR(150), projectId CHAR(150)," +
                " bidAmount INTEGER, PRIMARY KEY (userId, projectId), FOREIGN KEY (userId) REFERENCES User(username)," +
                " FOREIGN KEY (projectId) REFERENCES Project(id))");

        st.close();
        con.close();

        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    private String getStatement() {
        return "SELECT userId, bidAmount" +
                " FROM Bid" +
                " WHERE projectId = ?" ;
    }

    private String insertStatement() {
        return "INSERT INTO Bid" +
                " VALUES (?,?,?)" ;
    }

    private String hasBidStatement() {
        return "SELECT " + COLUMNS +
                " FROM Bid" +
                " WHERE userId = ? AND projectId = ?" ;
    }

    private Bid convertResultSetToDomainModel(String projectId, String userId, int bidAmount) throws SQLException {
        Project project = projectMapper.get(projectId) ;
        User user = userMapper.get(userId) ;
        return new Bid(
                user,
                project,
                bidAmount,
                project.bidPointsCalc(user, bidAmount)
        );
    }

    public List<Bid> getBids(String projectID) throws SQLException {
        List<Bid> resultBid = new ArrayList<>() ;

        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(getStatement())
        ) {
            st.setString(1, projectID);
            ResultSet resultSet ;
            try {
                resultSet = st.executeQuery() ;
                while(resultSet.next()) {
                    String userId = resultSet.getString(1) ;
                    int bidAmount = resultSet.getInt(2) ;
                    resultBid.add(convertResultSetToDomainModel(projectID, userId, bidAmount)) ;
                }
                con.close();
                st.close();
                return resultBid ;
            } catch (SQLException ex) {
                System.out.println("error in BidMapper.getBid query.");
                throw ex;
            }
        }
    }

    public int add(Bid newBid) throws SQLException {
        if (hasBidded(newBid.getBiddingUser(), newBid.getProject()))
            return -1 ;
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(insertStatement())
        ) {
            st.setString(1, newBid.getBiddingUser().getUsername());
            st.setString(2, newBid.getProject().getID());
            st.setInt(3, newBid.getBidAmount());
            try {
                st.executeUpdate() ;
                con.close();
                st.close();
            } catch (SQLException ex) {
                System.out.println("error in BidMapper.add query.");
                throw ex;
            }
        }

        return 0;
    }

    private boolean hasBidded(User user, Project project) throws SQLException {
        try (Connection con = DBCPDataSource.getConnection();
             PreparedStatement st = con.prepareStatement(hasBidStatement())
        ) {
            st.setString(1, user.getUsername());
            st.setString(2, project.getID());
            ResultSet resultSet ;
            try {
                resultSet = st.executeQuery() ;
                return resultSet.next() ;
            } catch (SQLException ex) {
                System.out.println("error in BidMapper.hasBidded query.");
                throw ex;
            }
        }
    }
}