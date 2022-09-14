import java.util.ArrayList;
public class Attack{
    public static final double[][] typeChart = getTypeChart();
    public Pokemon user;
    public String name;
    public double power;
    public double accuracy;
    public double effectAccuracy;
    public double chanceOfCritical;
    public int priority;
    public int type;

    public boolean special;
    public boolean status;
    public Effect effect;
    public double damage = 0;
    public String result = "";

    //Normal attack constructor
    public Attack(String name, double power, double accuracy,
                  double chanceOfCritical, int priority, int type){
        //this.user = user;
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.chanceOfCritical = chanceOfCritical;
        this.priority = priority;
        this.type = type;
        this.special = (type == 1 || type == 2 || type == 3 || type == 4
                || type == 5 || type == 10 || type == 14 || type == 15);
        this.status = false;
    }
    //Status attack constructor
    public Attack(String name, double effectAccuracy, Effect effect, int type){
        //this.user = user;
        this.name = name;
        this.accuracy = 100;
        this.effectAccuracy = effectAccuracy;
        this.effect = effect;
        this.type = type;
        this.special = (type == 1 || type == 2 || type == 3 || type == 4
                || type == 5 || type == 10 || type == 14 || type == 15);
        this.status = true;
    }
    public void setEffect(double effectAccuracy, Effect effect){
        this.effectAccuracy = effectAccuracy;
        this.effect = effect;
    }
    //Sets damage
    public void initialize(Pokemon user, Pokemon opponent){
        //Initializing damage modifiers
        accuracy *= user.getAccuracy();
        int critical = 1;
        if(Math.random() * 100 < chanceOfCritical) critical = 2;
        double A = user.attack[1];
        double D = opponent.defense[1];
        if(special){
            A = user.specialAttack[1];
            D = opponent.specialDefense[1];
        }
        double STAB = 1;
        for(int opponentType : opponent.type){
            if(type == opponentType){
                STAB = 1.5;
                break;
            }
        }
        double typeMultiplier = 1;
        typeMultiplier = typeChart[this.type][opponent.type[0]];
        if(opponent.type.length == 2) typeMultiplier *= typeChart[this.type][opponent.type[1]];
        double random = (217 + (Math.random() * 38)) / 255;

        //Calculating damage (with gen 1 formula)
        damage = ((((((2 * user.level * critical) / 5.0) + 2) * power * (A / D)) / 50) + 2) * STAB * typeMultiplier * random;
        damage *= user.attackDamageMultiplier;

        //Creating result
        int percent = (int) ((damage * 100) / opponent.health[0]);
        String result = user.name + " used " + name + "!";
        String effectiveness = "But it failed!";
        if(typeMultiplier == 0.5) effectiveness = "It's not very effective...";
        if(typeMultiplier == 1) effectiveness = "It's effective!";
        if(typeMultiplier >= 2) effectiveness = "It's super effective!\n(" + opponent.name + " lost " + percent + "% of its health!)";
        if(critical == 2) effectiveness = effectiveness + "\nA critical hit!";
        result = result + "\n" + effectiveness;
        this.result = result;
    }

    //Uses attack
    public void use(Pokemon user, Pokemon opponent){
        //Initializing damage
        if(!status) initialize(user, opponent);

        if(Math.random() * 100 < accuracy){ //This is checking to see if the attack was missed
            Main.data.add(result);
            opponent.health[1] -= damage;

            //Initializing effects
            if(effect instanceof Recoil) effect.activate(user, user);
            else{
                effect.activate(user, opponent);
                //System.out.println(user.getName());
            }
        }
        else Main.data.add(this.name + " missed");
    }

    private static double[][] getTypeChart(){
        //0-Normal 1-fire 2-water 3-grass 4-electric 5-ice
        //6-fighting 7-poison 8-ground 9-flying 10-psychic
        //11-bug 12-rock 13-ghost 14-dragon 15-dark
        //16-steel 17-fairy
        //Special: water, fire, grass, electric, psychic, ice, dragon, dark
        //1,2,3,4,10,5,14,15
        double[][] list = new double[18][18];
        list[0]  = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0.0, 1.0, 1.0, 0.5, 1.0};
        list[1]  = new double[]{1.0, 0.5, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 1.0, 2.0, 1.0};
        list[2]  = new double[]{1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0};
        list[3]  = new double[]{1.0, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 0.5, 1.0};
        list[4]  = new double[]{1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0};
        list[5]  = new double[]{1.0, 0.5, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0};
        list[6]  = new double[]{2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 0.5, 0.5, 0.5, 2.0, 0.0, 1.0, 2.0, 2.0, 0.5};
        list[7]  = new double[]{1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 0.0, 2.0};
        list[8]  = new double[]{1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 2.0, 1.0};
        list[9]  = new double[]{1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0};
        list[10] = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.0, 0.5, 1.0};
        list[11] = new double[]{1.0, 0.5, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 0.5, 1.0, 2.0, 0.5, 0.5};
        list[12] = new double[]{1.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 0.5, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0};
        list[13] = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 1.0};
        list[14] = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.5, 0.0};
        list[15] = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0, 0.5, 1.0, 0.5};
        list[16] = new double[]{1.0, 0.5, 0.5, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0};
        list[17] = new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 0.5, 1.0};
        return list;
    }


}
