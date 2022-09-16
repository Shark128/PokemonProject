public class Item {
    public String name;
    public Effect effect;

    public Item(String name, Effect effect) {
        this.name = name;
        this.effect = effect;

    }
    public void use (Player user, Player opp) {
        // the pokeball for capturing is separate from all the other effects
        if (name.equals("Poke Ball")) {
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
        
    }
}
