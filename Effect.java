public class Effect{
    String name;
    public int expirationDate = 0;
    public Pokemon user;
    public Pokemon target;
    public Effect(){}
    public void activate(Pokemon user, Pokemon target){
        this.user = user;
        this.target = target;
        target.effects.add(this);
    }
    public void update(){}
    public void end(){ target.effects.remove(this); }
    /*public void recordResult(int value){
        int percent = (int) ((value * 100) / target.health[0]);
        String result = target.name + " lost " + percent + "% of its health from " + name;
        Main.data.add(result);
    }*/
}

class Protection extends Effect{
    public Protection(){ this.name = "damage nullifier"; }
    @Override
    public void activate(Pokemon user, Pokemon target){
        boolean chance = Math.random() * 100 < (1.0 / user.protectCounter) * 100;
        if(chance){
            this.expirationDate = Main.turn + 1;
            target.atkDmgMult = 0;
            user.protectCounter *= 3;
            super.activate(user, target);
            //Main.data.add(user.name + " is protected!");
            Main.addData(user.name + " is protected!");
        }
        else user.protectCounter = 1;
    }
    @Override
    public void end(){
        target.atkDmgMult = 1;
        super.end();
    }
}

class Confusion extends Effect{
    int duration;
    public Confusion(int duration){
        this.duration = duration;
        this.name = "confusion";
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        this.expirationDate = Main.turn + duration;
        user.confused = true;
        //Main.data.add(target.getName() + " is confused!");
        Main.addData(target.getName() + " is confused!");
        super.activate(user, target);
    }
    @Override
    public void end(){
        target.confused = false;
        super.end();
    }
}

class ChangeStage extends Effect{
    String stat;
    int stage;
    public ChangeStage(String stat, int stage){
        this.stat = stat;
        this.stage = stage;
        this.name = "changeStage";
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        if(stage > 0) target = user; //This assumes decreases always negative and increases always positive
        if(stat.equals("spd")){ target.changeSpeed(stage); }
        else if(stat.equals("atk")){ target.changeAttack(stage); }
        else if(stat.equals("spAtk")){ target.changeSpecialAttack(stage); }
        else if(stat.equals("def")){ target.changeDefense(stage); }
        else if(stat.equals("spDef")){ target.changeSpecialDefense(stage); }
        else if(stat.equals("acc")){ target.changeAccuracy(stage); }
        String result = user.getName() + "'s " + stat + " was ";
        if(stage > 0) result += "increased!";
        else result += "decreased!";
        //Main.data.add(result);
        Main.addData(result);
    }
}

class Damage extends Effect{ //Includes Leech, Burn, Poison, Bad Poison
    double iteration = 1;
    double denominator = 8;
    boolean badPoison;
    boolean leech;
    String command;
    public Damage(String command){
        this.expirationDate = 100;
        this.command = command;
        this.name = command;
        System.out.println("POISON TEST 1");
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        System.out.println("POISON TEST 2");
        boolean fire = false;
        boolean steelOrPoison = false;
        for(int type : target.getType()){ if(type == 1){ fire = true; break; }
        else if(type == 7 || type == 16){ steelOrPoison = true; break; } }
        if(!(fire && command.equals("burn")
                && !(steelOrPoison && (command.equals("poison") || command.equals("badPoison"))))){
            if(command.equals("burn")){ target.atkDmgMult = 0.5; }
            else if(command.equals("badPoison")){ denominator = 16; badPoison = true; }
            else if(command.equals("leech")){ leech = true; }


            String result = "";
            if(command.equals("poison") || command.equals("badPoison")) result = user.name + " was poisoned!";
            else if(command.equals("burn")) result = user.name + " was burned!";
            //Main.data.add(result);
            Main.addData(result);

            super.activate(user, target);
        }
        else{
            //Main.data.add(target.name + " is immune to " + command + "!");
            Main.addData(target.name + " is immune to " + command + "!");
        }
    }
    @Override
    public void update(){
        double value = (1.0 / denominator) * iteration;
        value *= target.getBaseHp();
        target.setHp(target.getHp() - value);
        if(leech){ user.setHp(user.getHp() + value); }
        if(badPoison) iteration++;

        int percent = (int) ((value * 100) / target.getBaseHp());
        String result = target.name + " lost " + percent + "% of its health!";
        //Main.data.add(result);
        Main.addData(result);
    }
}

class Incapacitate extends Effect{ //Includes Sleep, Paralyze, Rest
    String command;
    double chance = 100; //Separate from effect accuracy
    public Incapacitate(String status){
        this.command = status;
        this.name = "incapacitation";
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        boolean electric = false;
        for(int type : target.getType()){ if(type == 4){ electric = true; break; } }
        if(!(electric && command.equals("paralyze"))){
            if(command.equals("rest")){ expirationDate = Main.turn + 3; }
            else if(command.equals("sleep")){ expirationDate = Main.turn + ((int) (Math.random() * 7)) + 1; }
            else if(command.equals("paralyze")){ chance = 25; target.setSpd(target.getSpd() * 0.75); expirationDate = 100; }
            if(Math.random() * 100 < chance){ target.canMove = false; }
            super.activate(user, target);

            String result = "";
            if(command.equals("sleep") || command.equals("rest")){ result = target.name + " fell asleep!"; }
            else if(command.equals("paralyze")){ result = target.name + " was paralyzed!"; }
            //Main.data.add(result);
            Main.addData(result);
        }
        else{
            //Main.data.add(target.name + " is immune to paralysis!");
            Main.addData(target.name + " is immune to paralysis!");
        }
    }
    @Override
    public void end(){
        target.canMove = true;
        user.setSpd(user.getSpd());
        super.end();
    }
}

class Recoil extends Effect{
    int percent;
    Attack attack;
    public Recoil(int percent, Attack attack){
        this.percent = percent;
        this.attack = attack;
        this.name = "recoil";
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        double value = (percent * attack.damage) / 100;
        target.setHp(target.getHp() - value);
        String result = target.name + " was damaged by recoil!";
        //Main.data.add(result);
        Main.addData(result);
    }
}
