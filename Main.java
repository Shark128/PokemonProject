import java.util.ArrayList;
public class Main{
    public static int turn = 0;
    public static ArrayList<String> data = new ArrayList<>();

    public static void main(String[] args){
        /**
         * IMPORTANT!!
         * IMPORTANT!!
         * In this new system, the skeleton of effects are added to attacks, and when the attacks are used, the effects
         * are properly initialized with their user and target. For the effect to activate, the pokemon must be updated.
         * IMPORTANT!!
         * IMPORTANT!!
         */


        Pokemon BULBASAUR = new Pokemon("Bulbasaur", 45, 45, 49,
                65, 49, 65, 6.9, 10, new int[]{3, 7});
        //Attacks
        Attack Double_Edge = new Attack("Double-Edge", 120, 100, 6.5, -1, 0);
        Double_Edge.setEffect(100, new Recoil(33, Double_Edge));
        Attack Leech_Seed = new Attack("Leech Seed", 90, new Poison(8.0, 1, false, true), 3);
        Attack Sleep_Powder = new Attack("Sleep Powder", 75, new Incapacitate("sleep"), 3);



        Pokemon CHARMANDER = new Pokemon("Charmander", 39, 45, 52,
                60, 43, 50, 8.5, 10, new int[]{1});
        //Attacks
        Attack Ember = new Attack("Ember", 40, 100, 6.5, -1, 1);
        Ember.setEffect(10, new Poison(16.0, 0.5, false, false));
        Attack Smokescreen = new Attack("Smokescreen", 100, new ChangeStage("accuracy", -1), 0);







        //Testing
        System.out.println(BULBASAUR.getAccuracy());
        Smokescreen.use(CHARMANDER, BULBASAUR);
        System.out.println(BULBASAUR.getAccuracy());


        //Simulating passage of time
        BULBASAUR.update();
        CHARMANDER.update();
        for(String message : data) System.out.println(message);
        turn++;
        data = new ArrayList<>();
        BULBASAUR.update();
        CHARMANDER.update();
        for(String message : data) System.out.println(message);
        System.out.println(CHARMANDER.getHealth());
        System.out.println(BULBASAUR.getHealth());
        //Printing data

    }

    public static void initializePokemon(){

    }






}
