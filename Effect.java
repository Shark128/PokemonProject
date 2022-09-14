public class Effect{
    String name;
    public int duration;
    public int expirationDate = 1;

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

class ChangeStage extends Effect{
    String stat;
    int stage;
    boolean increase = false;
    public ChangeStage(String stat, int stage){
        this.stat = stat;
        this.stage = stage;
        if(stage > 0) increase = true;
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        this.expirationDate = Main.turn + 1;
        if(stat.equals("speed")){ target.changeSpeed(stage); }
        else if(stat.equals("attack")){ target.changeAttack(stage); }
        else if(stat.equals("specialAttack")){ target.changeSpecialAttack(stage); }
        else if(stat.equals("defense")){ target.changeDefense(stage); }
        else if(stat.equals("specialDefense")){ target.changeSpecialDefense(stage); }
        else if(stat.equals("accuracy")){ target.changeAccuracy(stage); }
        String result = user.getName() + "'s " + stat + " was ";
        if(increase) result += "increased!";
        else result += "decreased!";
        Main.data.add(result);
    }
}

class Poison extends Effect{
    //Includes Leech, Burn, Poison
    double iteration = 1;
    double denominator;
    double attackDamageMultiplier = 1;
    boolean bad;
    boolean takeHealth;
    public Poison(double denominator, double attackDamageMultiplier, boolean bad, boolean takeHealth){
        this.denominator = denominator;
        this.attackDamageMultiplier = attackDamageMultiplier;
        this.bad = bad;
        this.takeHealth = takeHealth;
        this.expirationDate = 100;
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        target.attackDamageMultiplier = attackDamageMultiplier;
        super.activate(user, target);
    }
    @Override
    public void update(){
        double value = (1.0 / denominator) * iteration;
        value *= target.getBaseHealth();
        target.health[1] -= value;
        if(takeHealth) user.health[1] += value;
        int percent = (int) ((value * 100) / target.health[0]);
        String result = target.name + " lost " + percent + "% of its health!";
        Main.data.add(result);
        if(bad) iteration++;
    }
}

class Incapacitate extends Effect{
    String command;
    public Incapacitate(String status){
        this.command = status;
        this.name = "incapacitation";
    }
    @Override
    public void activate(Pokemon user, Pokemon target){
        if(command.equals("rest")) expirationDate = Main.turn + 2;
        else if(command.equals("sleep")) expirationDate = Main.turn + ((int) (Math.random() * 7)) + 1;
        target.canMove = false;
        super.activate(user, target);
        String result = "";
        if(command.equals("sleep") || command.equals("rest")) result = user.name + " fell asleep!";
        else if(command.equals("paralyze")) result = user.name + " was paralyzed!";
        Main.data.add(result);
    }
    @Override
    public void end(){
        target.canMove = true;
        super.end();
    }
}

class Recoil extends Effect{
    int percent;
    Attack attack;
    public Recoil(int percent, Attack attack){
        this.percent = percent;
        this.attack = attack;
        name = "recoil";
    }
    @Override //NOTE TO SELF: Figure out why this cannot function in tandem with super class's activate
    public void activate(Pokemon user, Pokemon target){
        expirationDate = Main.turn + 1;
        super.activate(user, target);
    }
    @Override
    public void update(){
        double value = (percent * attack.damage) / 100;
        target.health[1] -= value;
        int percent = (int) ((value * 100) / target.health[0]);
        String result = target.name + " was damaged by recoil!";
        Main.data.add(result);
    }
}