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

    public boolean addEndorsment(User endorser, User target, String skill){
        if(hasEndorsed(endorser, target, skill))
            return false;
        endorsements.add(new Endorsement(endorser, target, skill));
        target.incPoint(skill);
        return true;
    }

    public boolean hasEndorsed(User endorser, User target, String skill){
        for (Endorsement e : endorsements) {
            if (e.getEndorser().equals(endorser) && e.getTarget().equals(target) && e.getSkill().equals(skill))
                return true;
        }
        return false;
    }

    public List<Endorsement> getEndorsments(String endorser, String target) {
        List<Endorsement> endorsements = new ArrayList<>();
        for(Endorsement e: this.endorsements){
            if(e.getEndorser().getUsername().equals(endorser) && e.getTarget().getUsername().equals(target)){
                endorsements.add(e);
            }
        }
        return endorsements;
    }
}
