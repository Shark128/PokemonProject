import java.util.ArrayList;

public class Item {
    public String name;
    public Effect effect;
    public String desc;

    public Item(String name, Effect effect, String desc) {
        this.name = name;
        this.effect = effect;
        this.desc = desc;
    }
    public void usePokeball (Player user, Player opp) {
        // the pokeball for capturing is separate from all the other effects

//             40% of the pokemon switching sides
//            If there is 6 pokepeople on the team then the captured pokemon will disappear, if less than it will join the army
        boolean chance = Math.floor((Math.random() * 100)) < 40;
        if (user.team.size() == 6) {
            if (chance) {
                opp.team.remove(opp.currentPokemon);
                // use the method for replacement if it is made or make one myself if not
            }
        }
        else {
            if (chance) {
                Pokemon captured = opp.currentPokemon;
                opp.team.remove(captured);
                user.team.add(captured);
            }
        }
    }

    public void use (Pokemon user, Pokemon target) {
        effect.activate(user, target);
    }

    public void itemDex () {
        ArrayList<Item> itemdex = new ArrayList<Item>();
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
    }


}
