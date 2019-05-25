package joboonja;

import joboonja.domain.Database;
import joboonja.domain.model.Project;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;
import java.util.List;

import static joboonja.domain.Database.getInstance;

public class AuctionHandler extends TimerTask {

    @Override
    public void run() {
        try{
            Database db = getInstance();
            List<Project> projects = db.getProjects();
            for(Project p: projects){
                if (p.getWinner() == null && p.getDeadline() < new Date().getTime())
                    db.auction(p);
            }
        } catch (SQLException ex){
            System.out.println("error in auction handler");
        }
    }


}