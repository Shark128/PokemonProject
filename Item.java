import java.util.ArrayList;
public class Item{
    public static final ArrayList<Item> itemdex = getItemdex();
    public String name;
    public Effect effect;
    public String desc;

    public Item(String name, Effect effect, String desc){
        this.name = name;
        this.effect = effect;
        this.desc = desc;
    }
    public void usePokeball(Player user, Player target){
        /*
        The pokeball for capturing is separate from all the other effects
        40% of the pokemon switching sides
        If there is 6 pokepeople on the team then the captured pokemon will disappear,
        if less than it will join the army
        */
        boolean chance = Math.floor((Math.random() * 100)) < 40;
        if(chance){
            Pokemon captured = target.currentPokemon;
            Main.addData(user.getName() + " successfully captured " + captured.getName() + "!");
            target.getTeam().remove(captured);
            if(user.getTeam().size() < 6){ user.getTeam().add(captured); }
            if(target.getTeam().size() == 0){
                Main.addData(user.getName() + " won!");
                Main.gameOver = true;
            }
        }
        else{ Main.addData(user.getName() + "'s poke ball failed to capture " + target.currentPokemon.getName() + "!"); }
    }
    
    public void use(Pokemon user, Pokemon target){ effect.activate(user, target); }
    
    public static ArrayList<Item> getItemdex(){
        ArrayList<Item> itemdex = new ArrayList<>();
        Item Poke_Ball = new Item("Poke Ball", new Effect(), "A device for catching wild Pokemon.");
        // there is no effect because Pokeball is special
        Item Confunder = new Item("Confunder", new Confusion(3), "Confuses the opponent for three turns.");
        Item Winged_Boots = new Item("Winged Boots", new ChangeStage("spd", 2),
                "Increases speed by 1 stage.");
        Item Shock_Pen = new Item("Shock Pen", new Incapacitate("paralyze"), "Paralyzes the opponent.");
        Item Scope = new Item("Scope", new ChangeStage("acc", 1), "Increases accuracy by 1 stage.");
        Item Force_Field = new Item("Force Field", new Protection(), "Protects the user.");

        itemdex.add(Poke_Ball);
        itemdex.add(Confunder);
        itemdex.add(Winged_Boots);
        itemdex.add(Shock_Pen);
        itemdex.add(Scope);
        itemdex.add(Force_Field);
        return itemdex;
    }
}
