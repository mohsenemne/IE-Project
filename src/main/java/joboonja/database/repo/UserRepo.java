package joboonja.database.repo;

import joboonja.database.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private List<User> users;

    public UserRepo(){
        users = new ArrayList<>();
    }

    public int add(User newUser){
        if(this.contains(newUser.getUsername())){
            return -1;
        }
        users.add(newUser);
        return 0;
    }

    private boolean contains(String username){
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

    public String getJsonList() {
        String info = "[";
        for (User u : users) {
            char str = info.charAt(info.length() - 1);
            if(str != ',' && str != '['){
                info += ",";
            }
            info += "{\"id\":\"" + u.getUsername() + "\",\"name\":\"" + u.getName()
                    + "\",\"jobTitle\":" + u.getJobTitle() + "}";
        }
        info += "]";
        return info;
    }
}
