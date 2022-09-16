import java.util.ArrayList;
public class Player{
    public String name;
    public ArrayList<Pokemon> team = new ArrayList<>();
    public Pokemon currentPokemon = null;

    public Player(){}

    public void setName(String name){ this.name = name; }
    public void setTeam(ArrayList<Pokemon> team){ this.team = team; }
    public String getName(){ return this.name; }
    public ArrayList<Pokemon> getTeam(){ return this.team; }
}
