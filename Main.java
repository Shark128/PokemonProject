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
        Smokescreen.addEffect(100, new ChangeStage("acc", -1));
        Attack Dragon_Breath = new Attack("Dragon Breath", 60, 100, 6.5, 0, 14);
        Dragon_Breath.addEffect(30, new Incapacitate("paralyze"));
        Attack Slash = new Attack("Slash", 70, 100, 13, 0, 0);
        CHARMANDER.attacks = new Attack[]{Ember, Smokescreen, Dragon_Breath, Slash};



        Pokemon SQUIRTLE = new Pokemon("Squirtle", 44, 43, 48,
                50, 65, 64, 9, 10, new int[]{2});
        //Attacks: Water_Gun, Withdraw, Water Pulse, Protect
        Attack Water_Gun = new Attack("Water Gun", 40, 100, 6.5, 0, 2);
        Attack Withdraw = new Attack("Withdraw", 0, 100, 0, 0, 2);
        Withdraw.addEffect(100, new ChangeStage("def", 1));
        Attack Water_Pulse = new Attack("Water Pulse", 60, 100, 6.5, 0, 2);
        Water_Pulse.addEffect(20, new Confusion(100));
        Attack Protect = new Attack("Protect", 0, 100, 0, 4, 0);
        Protect.addEffect(100, new Protection()); //Note: Protection acts like an offensive attack
        SQUIRTLE.attacks = new Attack[]{Water_Gun, Withdraw, Water_Pulse, Protect};

        Pokemon PIDGEY = new Pokemon("Pidgey", 40, 56, 45, 35, 40, 35,
                4, 1, new int[]{0, 9});
//        Attacks: Sand Attack, Agility, Wing Attack, Gust
        Attack Sand_Attack = new Attack("Sand Attack", 0, 100, 0, 0, 9);
        Sand_Attack.addEffect(100, new ChangeStage("acc", -1));
        Attack Agility = new Attack("Agility", 0, 100, 0, 0, 10);
        Agility.addEffect(100, new ChangeStage("spd", 2));
        Attack Wing_Attack = new Attack("Wing Attack", 60, 100, 6.5, 0, 9);
        Attack Gust = new Attack("Gust", 40, 100, 6.5, 0, 9);
        PIDGEY.attacks = new Attack[]{Sand_Attack,Agility,Wing_Attack,Gust};

        Pokemon PICHU = new Pokemon("Pichu", 20, 60, 40, 35, 15, 35,
                4.4, 54, new int[]{4});
//        Attacks: Tail Whip, Thunder Shock, Nuzzle, Charm
        Attack Tail_Whip = new Attack("Tail Whip", 0, 100, 0, 0, 0);
        Tail_Whip.addEffect(100, new ChangeStage("def",-1));
        Attack Thunder_Shock = new Attack("Thunder Shock", 40, 100, 6.5, 0, 4);
        Thunder_Shock.addEffect(10, new Incapacitate("paralyze"));
        Attack Nuzzle = new Attack("Nuzzle", 20, 100, 6.5, 0, 4);
        Nuzzle.addEffect(100, new Incapacitate("paralyze"));
        Attack Charm = new Attack("Charm", 0, 100, 0, 0, 17);
        Charm.addEffect(100, new ChangeStage("attack", -2));
        PICHU.attacks = new Attack[]{Tail_Whip, Thunder_Shock, Nuzzle, Charm};

        Pokemon DIGLETT = new Pokemon("Diglett", 10, 95, 55, 35, 25, 45,
                1.8, 38, new int[]{8});
//        Attacks: Scratch, Sucker Punch, Growl, Slash
        Attack Scratch = new Attack("Scratch", 40, 100, 6.5, 0, 0);
        Attack Sucker_Punch = new Attack("Sucker Punch", 70, 100, 6.5, 1, 15);
        Attack Growl = new Attack("Growl", 0, 100, 0, 0, 0);
        Growl.addEffect(100, new ChangeStage("atk",-1));
        DIGLETT.attacks = new Attack[]{Scratch, Sucker_Punch, Growl, Slash};

        Pokemon GASTLY = new Pokemon("Gastly", 30, 80, 35, 100, 30, 35,
                .2, 19, new int[]{7, 13});
//        Attacks: Confuse Ray, Hypnosis, Shadow Ball, Payback
        Attack Confuse_Ray = new Attack("Confuse Ray", 0, 100, 0, 0, 13);
        Confuse_Ray.addEffect(100, new Confusion(100));
        Attack Hypnosis = new Attack("Hypnosis", 0, 60, 0, 0, 10);
        Hypnosis.addEffect(100, new Incapacitate("sleep"));
        Attack Shadow_Ball = new Attack("Shadow Ball", 80, 100, 6.5, 0, 13);
        Shadow_Ball.addEffect(20, new ChangeStage("spDef", -1));
        Attack Payback = new Attack("Payback", 50, 100, 6.5, 0, 15);
        GASTLY.attacks = new Attack[]{Confuse_Ray, Hypnosis, Shadow_Ball, Payback};

        Pokemon VOLTORB = new Pokemon("Voltorb", 40, 100, 30, 55, 50, 55,
                23, 78, new int[]{4});
//        Attacks: Eerie Impulse, Spark, Charge Beam, Screech
        Attack Eerie_Impulse = new Attack("Eerie Impulse", 0, 100, 0, 0, 4);
        Eerie_Impulse.addEffect(100, new ChangeStage("spAtk",-2));
        Attack Spark = new Attack("Spark", 65, 100, 6.5, 0, 4);
        Spark.addEffect(30, new Incapacitate("paralyze"));
        Attack Charge_Beam = new Attack("Charge Beam", 50, 90, 6.5, 0, 4);
        Charge_Beam.addEffect(70, new ChangeStage("spAtk",1));
        Attack Screech = new Attack("Screech", 0, 85, 0, 0, 0);
        Screech.addEffect(100, new ChangeStage("def",-2));
        VOLTORB.attacks = new Attack[]{Eerie_Impulse, Spark, Charge_Beam, Screech};

        Pokemon DRATINI = new Pokemon("Dratini", 41, 50, 64, 50, 45, 50,
                7.3, 45, new int[]{14});
//        Attacks: Thunder Wave, Twister, Slam, Agility
        Attack Thunder_Wave = new Attack("Thunder Wave", 0, 90, 0, 0, 4);
        Thunder_Wave.addEffect(100, new Incapacitate("paralyze"));
        Attack Twister = new Attack("Twister", 40, 100, 6.5, 0, 14);
        Attack Slam = new Attack("Slam", 80, 75, 6.5, 0, 0);
        DRATINI.attacks = new Attack[]{Thunder_Wave, Twister, Slam, Agility};

        Pokemon SLOWPOKE = new Pokemon("Slowpoke", 90, 15, 65, 40, 65, 40,
                79, 39, new int[]{2, 10});
//       Attacks: Growl, Water Pulse, Amnesia, Confusion
        Attack Confusion = new Attack("Confusion", 50, 100, 6.5, 0, 10);
        Confusion.addEffect(10, new Confusion(100));
        Attack Amnesia = new Attack("Amnesia", 0, 100, 0, 0, 10);
        Amnesia.addEffect(100, new ChangeStage("spDef",2));
        SLOWPOKE.attacks = new Attack[]{Growl, Water_Pulse, Confusion, Amnesia};

        Pokemon SPEAROW = new Pokemon("Spearow", 40, 70, 60, 31, 30, 31,
                4.4, 52, new int[]{0, 9});
//        Attacks: Peck, Assurance, Agility, Growl
        Attack Peck = new Attack("Peck", 35, 100, 6.5, 0, 9);
        Attack Assurance = new Attack("Assurance", 60, 100, 6.5, 0,13);
        SPEAROW.attacks = new Attack[]{Peck, Assurance, Agility, Growl};

        Pokemon SANDSHREW = new Pokemon("Sandshrew", 50, 40, 75, 20, 85, 30,
                26, 27, new int[]{8});
//        Attacks: Scratch, Poison Sting, Slash, Swords Dance
        Attack Poison_Sting = new Attack("Poison Sting", 15, 100, 6.5, 0, 7);
        Poison_Sting.addEffect(30, new Damage("poison"));
        Attack Swords_Dance = new Attack("Swords Dance", 0, 100, 0, 0, 0);
        Swords_Dance.addEffect(100, new ChangeStage("atk", 2));
        SANDSHREW.attacks = new Attack[]{Scratch, Poison_Sting, Slash, Swords_Dance};

        Pokemon ZUBAT = new Pokemon("Zubat", 40, 55, 45, 30, 35, 40,
                16, 43, new int[]{7, 9});
//        Attacks: Supersonic, Poison Fang, Air Cutter, Confuse Ray
        Attack Supersonic = new Attack("Supersonic", 0, 55, 0, 0, 0);
        Supersonic.addEffect(100, new Confusion(100));
        Attack Poison_Fang = new Attack("Poison Fang", 50, 100, 6.5, 0, 7);
        Poison_Fang.addEffect(50, new Damage("badPoison"));
        Attack Air_Cutter = new Attack("Air Cutter", 60, 95, 13, 0, 9);
        ZUBAT.attacks = new Attack[]{Supersonic, Poison_Fang, Air_Cutter, Confuse_Ray};

        Pokemon MRBOUNDS = new Pokemon("Mr. Bounds", 2000, 2000, 2000, 2000, 2000, 2000,
                2000, 99, new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
//        Attacks: Boundless Force
        Attack Boundless_Force = new Attack("Boundless Force", 1000, 5, 100, 5, 0);
        Boundless_Force.addEffect(100, new ChangeStage("acc", 2));



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
