import java.util.ArrayList;
public class Pokemon{
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
    public void setHp(double hp){ this.hp[1] = hp; }
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
}
