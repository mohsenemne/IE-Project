package jobunja.repo;

import jobunja.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private List<User> users;

    public UserRepo(){
        users = new ArrayList<User>();
    }

    public int add(User newUser){
        if(this.contains(newUser.getUsername())){
            return -1;
        }
        users.add(newUser);
        return 0;
    }

    public boolean contains(String username){
        for (User u: users) {
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public User get(String username) {
        for (User u: users) {
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }
}
