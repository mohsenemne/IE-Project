package jobunja.model;

public class Skill {
    private String name;
    private int points;

    public Skill(String name, int points){
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points ;
    }
}
