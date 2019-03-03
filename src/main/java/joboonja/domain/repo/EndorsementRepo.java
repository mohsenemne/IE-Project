package joboonja.domain.repo;

import joboonja.domain.model.Endorsement;
import joboonja.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class EndorsementRepo {
    List<Endorsement> endorsements;

    public EndorsementRepo(){
        endorsements = new ArrayList<>();
    }

    public void addEndorsment(User endorser, User target, String skill){
        endorsements.add(new Endorsement(endorser, target, skill));
        target.incPoint(skill);
    }

    public boolean hasEndorsed(User endorser, User target, String skill){
        for (Endorsement e : endorsements) {
            if (e.getEndorser().equals(endorser) && e.getTarget().equals(target) && e.getSkill().equals(skill))
                return true;
        }
        return false;
    }
}
