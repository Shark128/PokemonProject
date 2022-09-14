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



        Pokemon CHARMANDER = new Pokemon("Charmander", 39, 45, 52,
                60, 43, 50, 8.5, 10, new int[]{1});
        //Attacks: Ember, Smokescreen, Dragon_Breath, Slash
        Attack Ember = new Attack("Ember", 40, 100, 6.5, 0, 1);
        Ember.addEffect(10, new Damage("burn"));
        Attack Smokescreen = new Attack("Smokescreen", 0, 100, 0, 0, 3);
        Smokescreen.addEffect(100, new ChangeStage("accuracy", -1));
        Attack Dragon_Breath = new Attack("Dragon Breath", 60, 100, 6.5, 0, 14);
        Dragon_Breath.addEffect(30, new Incapacitate("paralyze"));
        Attack Slash = new Attack("Slash", 70, 100, 13, 0, 0);
        CHARMANDER.attacks = new Attack[]{Ember, Smokescreen, Dragon_Breath, Slash};



        Pokemon SQUIRTLE = new Pokemon("Squirtle", 44, 43, 48,
                50, 65, 64, 9, 10, new int[]{2});
        //Attacks: Water_Gun, Withdraw, Water Pulse, Protect
        Attack Water_Gun = new Attack("Water Gun", 40, 100, 6.5, 0, 2);
        Attack Withdraw = new Attack("Withdraw", 0, 100, 0, 0, 2);
        Withdraw.addEffect(100, new ChangeStage("defense", 1));
        Attack Water_Pulse = new Attack("Water Pulse", 60, 100, 6.5, 0, 2);
        Water_Pulse.addEffect(20, new Confusion(100));
        Attack Protect = new Attack("Protect", 0, 100, 0, 4, 0);
        Protect.addEffect(100, new Protection()); //Note: Protection acts like an offensive attack
        SQUIRTLE.attacks = new Attack[]{Water_Gun, Withdraw, Water_Pulse, Protect};



        //Testing
        System.out.println("Before:");
        System.out.println("Squirtle health: " + SQUIRTLE.getHp());
        System.out.println("Charmander health: " + CHARMANDER.getHp());

        Protect.use(SQUIRTLE, CHARMANDER);
        Slash.use(CHARMANDER, SQUIRTLE);

        //Simulating passage of time
        SQUIRTLE.update();
        CHARMANDER.update();
        for(String message : data) System.out.println(message);
        turn++;
        data = new ArrayList<>();
        SQUIRTLE.update();
        CHARMANDER.update();
        for(String message : data) System.out.println(message);

        System.out.println("\nAfter:");
        System.out.println("Squirtle health: " + SQUIRTLE.getHp());
        System.out.println("Charmander health: " + CHARMANDER.getHp());
        //Printing data
    }
}
