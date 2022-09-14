import java.util.ArrayList;
public class Pokemon{
    //0-Normal 1-fire 2-water 3-grass 4-electric 5-ice
    //6-fighting 7-poison 8-ground 9-flying 10-psychic
    //11-bug 12-rock 13-ghost 14-dragon 15-dark
    //16-steel 17-fairy
    public String name;
    public double[] health;
    public double[] speed;

    public double[] attack;
    public double[] specialAttack;
    public double[] defense;
    public double[] specialDefense;
    public double[] accuracy = new double[]{2.0, 2.0};

    public double weight;
    public int level;
    public int[] type;

    public double attackDamageMultiplier = 1;
    public boolean canMove = true;
    public ArrayList<Effect> effects = new ArrayList<>();



    public Pokemon(String name, double hp, double spd, double atk, double specAtk, double def, double specDef,
                   double weight, int level, int[] type){
        //Level bonus
        hp += (hp * (level * 0.01));
        spd += (spd * (level * 0.01));
        atk += (atk * (level * 0.01));
        specAtk += (specAtk * (level * 0.01));
        def += (def * (level * 0.01));
        specDef += (specDef * (level * 0.01));
        //Initializing
        this.name = name;
        this.health = new double[]{hp, hp};
        this.speed = new double[]{spd, 2, 2, spd};
        this.attack = new double[]{atk, 2, 2, atk};
        this.specialAttack = new double[]{specAtk, 2, 2, specAtk};
        this.defense = new double[]{def, 2, 2, def};
        this.specialDefense = new double[]{specDef, 2, 2, specDef};
        this.weight = weight;
        this.level = level;
        this.type = type;
    }

    public void update(){

        //Removing expired effects
        for(int i = 0; i < effects.size(); i++){
            if(effects.get(i).expirationDate == Main.turn){
                effects.get(i).end();
                i = -1;
            }
        }

        //Using effects
        for(Effect effect : effects){
            //System.out.println(effect.name);
            effect.update();
        }
    }

    public String getName(){ return name; }
    public double getHealth(){ return health[1]; }
    public double getBaseHealth(){ return health[0]; }
    public double getSpeed(){ return speed[0] * (speed[1] / speed[2]); }
    public double getAttack(){ return attack[0] * (attack[1] / attack[2]); }
    public double getSpecialAttack(){ return specialAttack[0] * (specialAttack[1] / specialAttack[2]); }
    public double getDefense(){ return defense[0] * (defense[1] / defense[2]); }
    public double getSpecialDefense(){ return specialDefense[0] * (specialDefense[1] / specialDefense[2]); }
    public double getAccuracy(){ return accuracy[0] / accuracy[1]; }
    public double getWeight(){ return weight; }
    public int getLevel(){ return level; }
    public int[] getType(){ return type; }
    public boolean getCanMove(){ return canMove; }
    public void changeSpeed(int stage){
        if(stage > 0){ if(this.speed[1] < 8){ this.speed[1]++; } }
        else{ if(this.speed[2] < 8){ this.speed[2]--; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpeed(stage); }
    }
    public void changeAttack(int stage){
        if(stage > 0){ if(this.attack[1] < 8){ this.attack[1] += stage; } }
        else{ if(this.attack[2] < 8){ this.attack[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeAttack(stage); }
    }
    public void changeSpecialAttack(int stage){
        if(stage > 0){ if(this.specialAttack[1] < 8){ this.specialAttack[1] += stage; } }
        else{ if(this.specialAttack[2] < 8){ this.specialAttack[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpecialAttack(stage); }
    }
    public void changeDefense(int stage){
        if(stage > 0){ if(this.defense[1] < 8){ this.defense[1] += stage; } }
        else{ if(this.defense[2] < 8){ this.defense[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeDefense(stage); }
    }
    public void changeSpecialDefense(int stage){
        if(stage > 0){ if(this.specialDefense[1] < 8){ this.specialDefense[1] += stage; } }
        else{ if(this.specialDefense[2] < 8){ this.specialDefense[2] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpecialDefense(stage); }
    }
    public void changeAccuracy(int stage){
        if(stage > 0){ if(this.accuracy[0] < 8){ this.accuracy[0] += stage; } }
        else{ if(this.accuracy[1] < 8){ this.accuracy[1] -= stage; } }
        if(stage > 0){ stage--; } else{ stage++; }
        if(stage != 0){ changeSpecialDefense(stage); }
    }
    public void setAttackDamageMultiplier(double x){ this.attackDamageMultiplier = x; }
    /*public void updateStages(){
        //Structure of variables:
        //Index 0 = base stat, never changed
        //Index 1/2 = num, denom of stage
        //Index 3 = actual value
        //Updating stages
        speed[3] = speed[0] * (speed[1] / speed[2]);
        attack[3] = attack[0] * (attack[1] / attack[2]);
        specialAttack[3] = specialAttack[0] * (specialAttack[1] / specialAttack[2]);
        defense[3] = defense[0] * (defense[1] / defense[2]);
        specialDefense[3] = specialDefense[0] * (specialDefense[1] / specialDefense[2]);
    }*/
}