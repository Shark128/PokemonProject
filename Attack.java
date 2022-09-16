import java.util.ArrayList;
public class Attack{
    public static final double[][] typeChart = getTypeChart();
    public String name;
    public double power;
    public double accuracy;
    public double chanceOfCritical;
    public int priority;
    public int type;


    public ArrayList<Effect> effectList = new ArrayList<>();
    public ArrayList<Integer> effectAccList = new ArrayList<>();
    //public double effectAccuracy;
    public double damage = 0;
    public String result = "";
    public String description = "";
    public String type2 = "Physical";

    //Normal attack constructor
    public Attack(String name, double power, double accuracy, double chanceOfCritical, int priority, int type, String desc){
        this.name = name;
        this.power = power;
        this.accuracy = accuracy;
        this.chanceOfCritical = chanceOfCritical;
        this.priority = priority;
        this.type = type;
        //this.special = (type == 1 || type == 2 || type == 3 || type == 4
                //|| type == 5 || type == 10 || type == 14 || type == 15);
        //this.status = false;
        description = desc;
    }

    public void addEffect(int effectAcc, Effect effect){
        this.effectAccList.add(effectAcc);
        this.effectList.add(effect);
    }

    //Sets damage
    public void initialize(Pokemon user, Pokemon opponent){
        System.out.println("THIS.TYPE: " + this.type + "    OPPONENT.TYPE: " + opponent.type[0]);
        //Initializing damage modifiers
        accuracy *= user.getAcc();
        int critical = 1;
        if(Math.random() * 100 < chanceOfCritical) critical = 2;
        double A = user.getAtk();
        double D = opponent.getDef();
        if(type2.equals("Special")){
            A = user.getSpAtk();
            D = opponent.getSpDef();
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
        damage *= user.atkDmgMult;
        if(power == 0) damage = 0;

        //Creating result
        //int percent = (int) ((damage * 100) / opponent.getBaseHp());
        /*String result = user.name + " used " + name + "!";
        String effectiveness = "But it failed!";
        if(typeMultiplier == 0.5) effectiveness = "It's not very effective...";
        if(typeMultiplier == 1) effectiveness = "It's effective!";
        if(typeMultiplier >= 2) effectiveness = "It's super effective!\n(" + opponent.name + " lost " + percent + "% of its health!)";
        if(critical == 2) effectiveness = effectiveness + "\nA critical hit!";
        result = result + "\n" + effectiveness;*/
        //if(damage != 0){ result = opponent.name + " lost " + percent + "% of their health!"; }

        ArrayList<String> result = new ArrayList<>();
        int percent = (int) ((damage * 100) / opponent.getBaseHp());
        String effectiveness = "But it failed!";
        if(typeMultiplier == 0.5) effectiveness = "It's not very effective...";
        if(typeMultiplier == 1) effectiveness = "";
        if(typeMultiplier >= 2) effectiveness = "It's super effective!\n(" + opponent.name + " lost " + percent + "% of its health!)";
        if(effectiveness.length() > 0) result.add(effectiveness);
        if(critical == 2) result.add("A critical hit!");
        result.add("(" + opponent.name + " lost " + percent + "% of their health!)");
        String result2 = "";
        for(String x : result) result2 += x + " ";
        this.result = result2;
    }

    //Uses attack
    public void use(Pokemon user, Pokemon opponent){
        //Initializing damage
        if(user.confused && Math.random() * 100 < 50) opponent = user;
        if(!type2.equals("Status")) initialize(user, opponent);

        if(Math.random() * 100 < accuracy){ //Checking to see if the attack was missed
            //Main.data.add(result);
            Main.addData(user.getName() + " used " + this.name + "!");
            Main.addData(result);
            //Main.addData("Damage dealt: " + damage);
            opponent.setHp(opponent.getHp() - damage);

            //Initializing/Using effects
            for(int i = 0; i < effectList.size(); i++){
                Effect effect = effectList.get(i);
                if(Math.random() * 100 < effectAccList.get(i)){ //Checking to see if the effect was missed
                    if(effect instanceof Recoil){ effect.activate(user, user); }
                    else{ effect.activate(user, opponent); }
                }
            }
        }
        else{
            Main.addData(this.name + " missed!");
            //Main.data.add(this.name + " missed");
        }
    }

    private static double[][] getTypeChart(){
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
    public ArrayList<String> getString(){
        int width = 30;
        String space = "                                                             ".substring(0, width);
        String type1 = (Pokemon.intToType[this.type]);
        String type2 = this.type2;
        ArrayList<String> attackString = new ArrayList<>();
        //Name
        attackString.add(" " + this.name + space.substring(0, width - this.name.length() - 1));
        //Types
        attackString.add(" " + type1 + " " + type2 + space.substring(0, width - (type1 + type2 + "  ").length()));
        //Line
        attackString.add(space.replace("   ", " * "));
        //Base Power and Accuracy
        String base_power = " Base Power: " + ((int) this.power) + space.substring(0, width - (" base power: " + ((int) this.power)).length());
        String accuracy = ((int) this.accuracy) + "%";
        if(((int) this.accuracy) == 100){ accuracy = "can't miss"; }
        accuracy = " Accuracy: " + accuracy + space.substring(0, width - (" accuracy: " + accuracy).length());
        if((int) this.power > 0){ attackString.add(base_power); }
        attackString.add(accuracy);
        //Line
        attackString.add(space.replace("   ", " * "));
        //Description
        ArrayList<String> description = Main.squeezeText(width - 2, this.description);
        for(String x : description){ attackString.add(" " + x + " "); }
        //for(int i = 0; i < 5; i++) attackString.add("                                                        ");

        /*for(String x : attackString){
            System.out.println(x);
        }*/
        return attackString;
    }

    public void initializeType2(){
        boolean stop = false;
        for(Effect x : effectList){ if((x instanceof ChangeStage)){ stop = true; this.type2 = "Status"; break; } }
        if(!stop) if(effectList.size() > 0) this.type2 = "Special";
    }
}
