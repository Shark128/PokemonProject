import java.util.ArrayList;
public class Pokemon{
    public static final String[] intToType = ("Normal,Fire,Water,Grass,Electric,Ice,Fighting,Poison,Ground,Flying," +
            "Psychic,Bug,Rock,Ghost,Dragon,Dark,Steel,Fairy").split(",");
    public static ArrayList<Pokemon> pokedex = new ArrayList<>();
    //0-Normal 1-fire 2-water 3-grass 4-electric 5-ice
    //6-fighting 7-poison 8-ground 9-flying 10-psychic
    //11-bug 12-rock 13-ghost 14-dragon 15-dark
    //16-steel 17-fairy
    public String name;
    public double[] hp;
    public double[] spd;

    public double[] atk;
    public double[] spAtk;
    public double[] def;
    public double[] spDef;
    public double[] acc = new double[]{2.0, 2.0};

    public double weight;
    public int level;
    public int[] type;

    //Effect variables
    public double atkDmgMult = 1;
    public int protectCounter = 1;
    public boolean canMove = true;
    public boolean confused = false;
    public ArrayList<Effect> effects = new ArrayList<>();
    public Attack[] attacks = new Attack[4];

    boolean fainted = false;
    public int owner = 0;

    public Pokemon(String name, double hp, double spd, double atk, double spAtk, double def, double spDef,
                   double weight, int level, int[] type){
        /*
        STRUCTURE OF VARIABLES:
        Health: 0 = base, 1 = actual
        Speed-SpecialDefense: 0 = base, 1 = numerator of stage, 2 = denominator of stage, 3 = actual
        Accuracy: 0 = numerator of stage, 1 = denominator of stage
         */
        //Level bonus
        hp += (hp * (level * 0.01));
        spd += (spd * (level * 0.01));
        atk += (atk * (level * 0.01));
        spAtk += (spAtk * (level * 0.01));
        def += (def * (level * 0.01));
        spDef += (spDef * (level * 0.01));
        //Initializing
        this.name = name;
        this.hp = new double[]{hp, hp};
        this.spd = new double[]{spd, 2, 2, spd};
        this.atk = new double[]{atk, 2, 2, atk};
        this.spAtk = new double[]{spAtk, 2, 2, spAtk};
        this.def = new double[]{def, 2, 2, def};
        this.spDef = new double[]{spDef, 2, 2, spDef};
        this.weight = weight;
        this.level = level;
        this.type = type;
    }

    public void update(){
        //Removing expired effects
        for(int i = 0; i < effects.size(); i++){
            if(effects.get(i).expirationDate == Main.turn){
                System.out.println(this.name + "'s " + effects.get(i).name + " expired");
                effects.get(i).end();
                i = -1;
            }
        }
        //Using effects
        for(Effect effect : effects){ effect.update(); }
    }

    public ArrayList<String> getString(boolean currentPokemon){
        System.out.println("TESTESTEST HP: " + this.getHp());
        int width = 39;
        String space = "                                                                        ";
        ArrayList<String> string = new ArrayList<>();
        //Title String
        int end = width - (this.getName() + "level :" + this.getLevel()).length();
        String title = this.getName() + space.substring(0, end) + "Level: " + this.getLevel();
        if(!currentPokemon) title = "Level: " + this.getLevel() + space.substring(0, end) + this.getName();
        //HP string
        int percent = (int) ((100.0 * this.getHp()) / this.getBaseHp());
        int actualHp = (int) ((30 * this.getHp()) / this.getBaseHp());
        String hp1 = (space.substring(0, 30).replace(" ", "+")).substring(0, actualHp);
        String hp2 = (space.substring(0, 30).replace(" ", "-")).substring(0, 30 - actualHp);
        //Fixing a rounding thing that affects graphics
        if(percent > 99.9){ hp1 = space.substring(0, 30).replace(" ", "+"); hp2 = ""; }
        //Type string
        String types = "Type: ";
        for(int x : this.type){ types += Pokemon.intToType[x] + " "; }
        types += space.substring(0, width).substring(0, width - types.length());
        //System.out.println(types.length());
        //hp1 = "";
        //hp2 = space.replace(" ", "@").substring(0, 30);
        //percent = 123;

        String hp3 = space.substring(0, width - (percent + " " + hp1 + hp2).length());
        String hp = hp1 + hp2 + hp3 + percent + "%";
        if(!currentPokemon) hp = percent + "%" + hp3 + hp2 + hp1;
        string.add(title);
        string.add(hp);
        string.add(types);
        //string.add(space.substring(0, width)); //Use this space later for effects
        return string;
    }




    //ACCESSORS AND MUTATORS
    //Getters
    public String getName(){ return name; }
    public double getHp(){ return hp[1]; }
    public double getBaseHp(){ return hp[0]; }
    public double getSpd(){ return spd[0] * (spd[1] / spd[2]); }
    public double getBaseSpd(){ return spd[0]; }
    public double getAtk(){ return atk[0] * (atk[1] / atk[2]); }
    public double getSpAtk(){ return spAtk[0] * (spAtk[1] / spAtk[2]); }
    public double getDef(){ return def[0] * (def[1] / def[2]); }
    public double getSpDef(){ return spDef[0] * (spDef[1] / spDef[2]); }
    public double getAcc(){ return acc[0] / acc[1]; }
    public double getWeight(){ return weight; }
    public int getLevel(){ return level; }
    public int[] getType(){ return type; }
    public boolean getCanMove(){ return canMove; }
    //Setters
    public void setHp(double hp){
        this.hp[1] = hp;
        if(hp < 0){
            this.hp[1] = 0;
            this.fainted = true;
            Main.addData(this.getName() + " fainted!");
        }
        //System.out.println(owner.name);
    }
    public void setSpd(double spd){ this.spd[3] = spd; }
    public void setAtk(double atk){ this.atk[3] = atk; }
    public void setSpAtk(double spAtk){ this.spAtk[3] = spAtk; }
    public void setDef(double def){ this.def[3] = def; }
    public void setSpDef(double spDef){ this.spDef[3] = spDef; }
    public void setAttackDamageMultiplier(double x){ this.atkDmgMult = x; }
    //Changers (for stage modification)
    public void changeSpeed(int stage){
        if(stage > 0){ if(this.spd[1] < 8){ this.spd[1]++; } }
        else{ if(this.spd[2] < 8){ this.spd[2]--; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpeed(stage); }
    }
    public void changeAttack(int stage){
        if(stage > 0){ if(this.atk[1] < 8){ this.atk[1] += stage; } }
        else{ if(this.atk[2] < 8){ this.atk[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeAttack(stage); }
    }
    public void changeSpecialAttack(int stage){
        if(stage > 0){ if(this.spAtk[1] < 8){ this.spAtk[1] += stage; } }
        else{ if(this.spAtk[2] < 8){ this.spAtk[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpecialAttack(stage); }
    }
    public void changeDefense(int stage){
        if(stage > 0){ if(this.def[1] < 8){ this.def[1] += stage; } }
        else{ if(this.def[2] < 8){ this.def[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeDefense(stage); }
    }
    public void changeSpecialDefense(int stage){
        if(stage > 0){ if(this.spDef[1] < 8){ this.spDef[1] += stage; } }
        else{ if(this.spDef[2] < 8){ this.spDef[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpecialDefense(stage); }
    }
    public void changeAccuracy(int stage){
        if(stage > 0){ if(this.acc[0] < 8){ this.acc[0] += stage; } }
        else{ if(this.acc[1] < 8){ this.acc[1] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpecialDefense(stage); }
    }

    public static ArrayList<Pokemon> getPokedex(){
        ArrayList<Pokemon> pokedex = new ArrayList<>();

        Pokemon Charmander = new Pokemon("Charmander", 39, 45, 52,
                60, 43, 50, 8.5, 10, new int[]{1});
        //Attacks: Ember, Smokescreen, Dragon_Breath, Slash
        Attack Ember = new Attack("Ember",40, 100, 6.5, 0, 1,
                "Has a 10% chance to burn the target.");
        Ember.addEffect(10, new Damage("burn"));
        Attack Smokescreen = new Attack("Smokescreen", 0, 100, 0, 0, 0,
                "Lowers the target's accuracy by 1 stage.");
        Smokescreen.addEffect(100, new ChangeStage("acc", -1));
        Attack Dragon_Breath = new Attack("Dragon Breath", 60, 100, 6.5, 0, 14,
                "Has a 30% chance to paralyze the target.");
        Dragon_Breath.addEffect(30, new Incapacitate("paralyze"));
        Attack Slash = new Attack("Slash", 70, 100, 13, 0, 0,
                "Has a higher chance for a critical hit.");
        Charmander.attacks = new Attack[]{Ember, Smokescreen, Dragon_Breath, Slash};

        Pokemon Squirtle = new Pokemon("Squirtle", 44, 43, 48,
                50, 65, 64, 9, 10, new int[]{2});
        //Attacks: Water_Gun, Withdraw, Water Pulse, Protect
        Attack Water_Gun = new Attack("Water Gun", 40, 100, 6.5, 0, 2,
                "No additional effect.");
        Attack Withdraw = new Attack("Withdraw", 0, 100, 0, 0, 2,
                "Raises the user's Defense by 1 stage.");
        Withdraw.addEffect(100, new ChangeStage("def", 1));
        Attack Water_Pulse = new Attack("Water Pulse", 60, 100, 6.5, 0, 2,
                "Has a 20% chance to confuse the target.");
        Water_Pulse.addEffect(20, new Confusion(100));
        Attack Protect = new Attack("Protect", 0, 100, 0, 4, 0,
                "Prevents moves from affecting user this turn");
        Protect.addEffect(100, new Protection()); //Note: Protection acts like an offensive attack
        Squirtle.attacks = new Attack[]{Water_Gun, Withdraw, Water_Pulse, Protect};

        Pokemon Pidgey = new Pokemon("Pidgey", 40, 56, 45, 35, 40, 35,
                4, 1, new int[]{0, 9});
//        Attacks: Sand Attack, Agility, Wing Attack, Gust
        Attack Sand_Attack = new Attack("Sand Attack", 0, 100, 0, 0, 9,
                "Lowers the target's accuracy by 1 stage.");
        Sand_Attack.addEffect(100, new ChangeStage("acc", -1));
        Attack Agility = new Attack("Agility", 0, 100, 0, 0, 10,
                "Raises the user's Speed by 2 stages.");
        Agility.addEffect(100, new ChangeStage("spd", 2));
        Attack Wing_Attack = new Attack("Wing Attack", 60, 100, 6.5, 0, 9,
                "No additional effect.");
        Attack Gust = new Attack("Gust", 40, 100, 6.5, 0, 9,
                "No additional effect.");
        Pidgey.attacks = new Attack[]{Sand_Attack,Agility,Wing_Attack,Gust};

        Pokemon Pichu = new Pokemon("Pichu", 20, 60, 40, 35, 15, 35,
                4.4, 54, new int[]{4});
//        Attacks: Tail Whip, Thunder Shock, Nuzzle, Charm
        Attack Tail_Whip = new Attack("Tail Whip", 0, 100, 0, 0, 0,
                "Lowers the target's Defense by 1 stage.");
        Tail_Whip.addEffect(100, new ChangeStage("def",-1));
        Attack Thunder_Shock = new Attack("Thunder Shock", 40, 100, 6.5, 0, 4,
                "Has a 10% chance to paralyze the target.");
        Thunder_Shock.addEffect(10, new Incapacitate("paralyze"));
        Attack Nuzzle = new Attack("Nuzzle", 20, 100, 6.5, 0, 4,
                "Has a 100% chance to paralyze the target.");
        Nuzzle.addEffect(100, new Incapacitate("paralyze"));
        Attack Charm = new Attack("Charm", 0, 100, 0, 0, 17,
                "Lowers the target's Attack by 2 stages.");
        Charm.addEffect(100, new ChangeStage("attack", -2));
        Pichu.attacks = new Attack[]{Tail_Whip, Thunder_Shock, Nuzzle, Charm};

        Pokemon Diglett = new Pokemon("Diglett", 10, 95, 55, 35, 25, 45,
                1.8, 38, new int[]{8});
//        Attacks: Scratch, Sucker Punch, Growl, Slash
        Attack Scratch = new Attack("Scratch", 40, 100, 6.5, 0, 0,
                "No additional effect.");
        Attack Sucker_Punch = new Attack("Sucker Punch", 70, 100, 6.5, 1, 15,
                "Usually moves first (priority +1).");
        Attack Growl = new Attack("Growl", 0, 100, 0, 0, 0,
                "Lowers the foe's Attack by 1.");
        Growl.addEffect(100, new ChangeStage("atk",-1));
        Diglett.attacks = new Attack[]{Scratch, Sucker_Punch, Growl, Slash};

        Pokemon Gastly = new Pokemon("Gastly", 30, 80, 35, 100, 30, 35,
                .2, 19, new int[]{7, 13});
//        Attacks: Confuse Ray, Hypnosis, Shadow Ball, Payback
        Attack Confuse_Ray = new Attack("Confuse Ray", 0, 100, 0, 0, 13,
                "Confuses the target.");
        Confuse_Ray.addEffect(100, new Confusion(100));
        Attack Hypnosis = new Attack("Hypnosis", 0, 60, 0, 0, 10,
                "Causes the target to fall asleep.");
        Hypnosis.addEffect(100, new Incapacitate("sleep"));
        Attack Shadow_Ball = new Attack("Shadow Ball", 80, 100, 6.5, 0, 13,
                "Has a 20% chance to lower the target's Special Defense by 1 stage.");
        Shadow_Ball.addEffect(20, new ChangeStage("spDef", -1));
        Attack Payback = new Attack("Payback", 50, 100, 6.5, 0, 15,
                "No additional effect.");
        Gastly.attacks = new Attack[]{Confuse_Ray, Hypnosis, Shadow_Ball, Payback};

        Pokemon Voltorb = new Pokemon("Voltorb", 40, 100, 30, 55, 50, 55,
                23, 78, new int[]{4});
//        Attacks: Eerie Impulse, Spark, Charge Beam, Screech
        Attack Eerie_Impulse = new Attack("Eerie Impulse", 0, 100, 0, 0, 4,
                "Lowers the target's Special Attack by 2 stages.");
        Eerie_Impulse.addEffect(100, new ChangeStage("spAtk",-2));
        Attack Spark = new Attack("Spark", 65, 100, 6.5, 0, 4,
                "Has a 30% chance to paralyze the target.");
        Spark.addEffect(30, new Incapacitate("paralyze"));
        Attack Charge_Beam = new Attack("Charge Beam", 50, 90, 6.5, 0, 4,
                "Has a 70% chance to raise the user's Special Attack by 1 stage.");
        Charge_Beam.addEffect(70, new ChangeStage("spAtk",1));
        Attack Screech = new Attack("Screech", 0, 85, 0, 0, 0,
                "Lowers the target's Defense by 2 stages.");
        Screech.addEffect(100, new ChangeStage("def",-2));
        Voltorb.attacks = new Attack[]{Eerie_Impulse, Spark, Charge_Beam, Screech};

        Pokemon Dratini = new Pokemon("Dratini", 41, 50, 64, 50, 45, 50,
                7.3, 45, new int[]{14});
//        Attacks: Thunder Wave, Twister, Slam, Agility
        Attack Thunder_Wave = new Attack("Thunder Wave", 0, 90, 0, 0, 4,
                "Paralyzes the target.");
        Thunder_Wave.addEffect(100, new Incapacitate("paralyze"));
        Attack Twister = new Attack("Twister", 40, 100, 6.5, 0, 14,
                "No additional effect.");
        Attack Slam = new Attack("Slam", 80, 75, 6.5, 0, 0,
                "No additional effect.");
        Dratini.attacks = new Attack[]{Thunder_Wave, Twister, Slam, Agility};

        Pokemon Slowpoke = new Pokemon("Slowpoke", 90, 15, 65, 40, 65, 40,
                79, 39, new int[]{2, 10});
//       Attacks: Growl, Water Pulse, Amnesia, Confusion
        Attack Confusion = new Attack("Confusion", 50, 100, 6.5, 0, 10,
                "Has a 10% chance to confuse the target.");
        Confusion.addEffect(10, new Confusion(100));
        Attack Amnesia = new Attack("Amnesia", 0, 100, 0, 0, 10,
                "Raises the user's Special Defense by 2 stages.");
        Amnesia.addEffect(100, new ChangeStage("spDef",2));
        Slowpoke.attacks = new Attack[]{Growl, Water_Pulse, Confusion, Amnesia};

        Pokemon Spearow = new Pokemon("Spearow", 40, 70, 60, 31, 30, 31,
                4.4, 52, new int[]{0, 9});
//        Attacks: Peck, Assurance, Agility, Growl
        Attack Peck = new Attack("Peck", 35, 100, 6.5, 0, 9,
                "No additional effect.");
        Attack Assurance = new Attack("Assurance", 60, 100, 6.5, 0,13,
                "No additional effect.");
        Spearow.attacks = new Attack[]{Peck, Assurance, Agility, Growl};

        Pokemon Sandshrew = new Pokemon("Sandshrew", 50, 40, 75, 20, 85, 30,
                26, 27, new int[]{8});
//        Attacks: Scratch, Poison Sting, Slash, Swords Dance
        Attack Poison_Sting = new Attack("Poison Sting", 15, 100, 6.5, 0, 7,
                "30% chance to poison the target.");
        Poison_Sting.addEffect(30, new Damage("poison"));
        Attack Swords_Dance = new Attack("Swords Dance", 0, 100, 0, 0, 0,
                "Raises the user's Attack by 2 stages.");
        Swords_Dance.addEffect(100, new ChangeStage("atk", 2));
        Sandshrew.attacks = new Attack[]{Scratch, Poison_Sting, Slash, Swords_Dance};

        Pokemon Zubat = new Pokemon("Zubat", 40, 55, 45, 30, 35, 40,
                16, 43, new int[]{7, 9});
//        Attacks: Supersonic, Poison Fang, Air Cutter, Confuse Ray
        Attack Supersonic = new Attack("Supersonic", 0, 55, 0, 0, 0,
                "Causes the target to become confused.");
        Supersonic.addEffect(100, new Confusion(100));
        Attack Poison_Fang = new Attack("Poison Fang", 50, 100, 6.5, 0, 7,
                "Has a 50% chance to badly poison the target.");
        Poison_Fang.addEffect(50, new Damage("badPoison"));
        Attack Air_Cutter = new Attack("Air Cutter", 60, 95, 13, 0, 9,
                "Has a higher chance for a critical hit.");
        Zubat.attacks = new Attack[]{Supersonic, Poison_Fang, Air_Cutter, Confuse_Ray};

        Pokemon MrBounds = new Pokemon("Mr. Bounds", 1234, 1234, 1234, 1234, 1234, 1234,
                2000, 99, new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
//        Attacks: Boundless Force
        Attack Boundless_Force = new Attack("Boundless Force", 1000, 5, 100, 5, 0,
                "YOU CAN'T STOP THE BOUNDLESS FORCE (unless it misses)");
        Boundless_Force.addEffect(100, new ChangeStage("acc", 2));

        pokedex.add(Charmander);
        pokedex.add(Squirtle);
        pokedex.add(Pidgey);
        pokedex.add(Pichu);
        pokedex.add(Diglett);
        pokedex.add(Gastly);
        pokedex.add(Voltorb);
        pokedex.add(Dratini);
        pokedex.add(Slowpoke);
        pokedex.add(Spearow);
        pokedex.add(Sandshrew);
        pokedex.add(Zubat);
        //pokedex.add(MrBounds);

        //Temporary measure to set type2s
        for(Pokemon x : pokedex){
            for(Attack y : x.attacks){
                y.initializeType2();
                //System.out.println("Initialized " + y.name + " to " + y.type2);
            }
        }
        return pokedex;
    }
}
