public class Player{
    public String name;
    public Pokemon[] team;

    public Player(String name, boolean randomTeam){
        this.name = name;

        if(randomTeam) generateTeam();
    }

    public void generateTeam(){

    }
}
