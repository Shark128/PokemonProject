import java.util.ArrayList;
import java.util.Scanner;
public class Main{
    //public static ArrayList<Pokemon> team1 = new ArrayList<>();
    //public static ArrayList<Pokemon> team2 = new ArrayList<>();

    public static Player[] players = new Player[]{new Player(), new Player()};
    //public static int currentPlayer = 0;
    public static Player winner = new Player();

    public static boolean gameOver = false;
    public static int turn = 0;
    public static ArrayList<String> data = new ArrayList<>();

    public static String gameplayType = "none";

    public static boolean[][] playerOptions = new boolean[][]{{true, true, true, true}, {true, true, true, true}};
    public static void main(String[] args){
        /**
         * IMPORTANT!!
         * IMPORTANT!!
         * In this new system, the skeleton of effects are added to attacks, and when the attacks are used, the effects
         * are properly initialized with their user and target. For the effect to activate, the pokemon must be updated.
         * IMPORTANT!!
         * IMPORTANT!!
         */





        //ArrayList<String> test = squeezeText(27, );
        //for(String x : list2) System.out.println(x.length());
        /*String text1 = "testing this thing I hope it works";
        String text2 = "veryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryLongWord extraWord";
        ArrayList<String> test = squeezeText(27, text2);
        //String[] test = getCleanedString(27, text2).split(" ");
        for(String x : test){ System.out.println(x); }*/





        //System.out.println(Main.data.toString());


        for(Pokemon x : Pokemon.pokedex){
            for(Attack y : x.attacks){
                System.out.println(y.type);
            }
        }

        for(Player x : players){
            for(Pokemon y : x.getTeam()){
                System.out.println(y.getName());
            }
        }
        Pokemon.pokedex = Pokemon.getPokedex();
        boolean doMainLoop = true;
        while(doMainLoop){
            System.out.println("THE ULTIMATE DELUXE PREMIUM BOOTLEG POKEMON GAME EXPERIENCE SIMULATOR LIMITED TIME");
            System.out.println("Modes:\n(1) Player Versus Player\n(2) Player Versus Computer");
            int choice = new Scanner(System.in).nextInt();
            if(choice == 1){
                generateTeams(3, 1);
                gameplayType = "PVP";
                gameplay();
            }
            else if(choice == 2){
                generateTeams(3, 1);
                //PVE_Gameplay();
                gameplayType = "PVE";
                gameplay();
            }
        }


    }

    public static void gameplay(){
        //Initializing everything
        System.out.print("Player 1, please enter your name: ");
        String player1Name = new Scanner(System.in).nextLine();
        players[0].setName(player1Name);
        if(gameplayType.equals("PVP")){
            System.out.print("Player 2, please enter your name: ");
            String player2Name = new Scanner(System.in).nextLine();
            players[1].setName(player2Name);
        }
        else{
            players[1].setName("THE_COMPUTER");
        }


        //Actual gameplay
        while(!gameOver){
            Main.turn++;
            String header = "[ TURN " + Main.turn + " ]";
            addData(header);
            //Variable storage
            Attack[] attacks = new Attack[]{null, null};
            Pokemon[] substitutes = new Pokemon[]{null, null};

            //Getting player input
            for(int player = 0; player <= 1; player++){


                /**PRINTING SCREEN AND GETTING USER INPUT*/
                //This makes sure that if the user is playing against the computer, the user's screen does not change
                int choice = -1;
                if(player == 0 || gameplayType.equals("PVP")){
                    String[][] screen = new String[1][1];
                    if(player == 0){ screen = getScreen(players[0], players[1]); }
                    else{ screen = getScreen(players[1], players[0]); }
                    printScreen(screen);

                    String[] options = new String[]{" (1) Attack ", " (2) Switch ", " (3) Use Item ", " (4) Forfeit "};
                    String printOptions = "Options:";
                    for(int i = 0; i < 4; i++){ if(playerOptions[player][i]) printOptions += options[i]; }
                    System.out.println(printOptions);
                    //System.out.println("Options: (1) Attack (2) Switch (3) Use Item (4) Forfeit");
                    choice = new Scanner(System.in).nextInt();
                }
                else{
                    choice = 1;
                }

                //Recording input
                if(choice == 1){
                    if(player == 0 || gameplayType.equals("PVP")){
                        attacks[player] = getAttack(players[player]);
                    }
                    else{
                        //int random = ((int) (Math.random() * 4));
                        attacks[player] = players[1].currentPokemon.attacks[((int) (Math.random() * 4))];
                    }
                    System.out.println("\n\n\n\n\nTESTESTESTESTESTES: Player " + players[player].getName() + " chose " + attacks[player].name);
                }
                else if(choice == 2){
                    substitutes[player] = getSubstitute(players[player]);
                }
                else if(choice == 3){}
                else if(choice == 4){
                    System.out.print("Are you sure you want to forfeit?");
                    String forfeit = new Scanner(System.in).nextLine();
                    if(forfeit.toLowerCase().equals("y") || forfeit.toLowerCase().equals("yes")){
                        addData(players[player].getName() + " forfeited the match!");
                        if(player == 0) winner = players[1];
                        else winner = players[0];
                        gameOver = true;
                    }
                }
            }
            if(!gameOver){
                //Performing substitutions
                for(int i = 0; i <= 1; i++){
                    if(substitutes[i] != null){
                        String message1 = players[i].getName() + ": " + players[i].currentPokemon.getName() + ", come back!";
                        String message2 = players[i].getName() + ": " + "Go! " + substitutes[i].getName() + "!";
                        addData(message1);
                        addData(message2);
                        players[i].currentPokemon = substitutes[i];
                    }
                }
                //Performing attacks
                if(attacks[0] != null || attacks[1] != null){
                    if(attacks[0] != null && attacks[1] != null){
                        System.out.println("Both attacked");
                        int first = 0;
                        if(attacks[1].priority > attacks[0].priority) first = 1;
                        else if(players[1].currentPokemon.getSpd() > players[0].currentPokemon.getSpd()) first = 1;
                        else if(Math.random() < 0.5) first = 1;

                        if(first == 0){ attacks[0].use(players[0].currentPokemon, players[1].currentPokemon); }
                        else{ attacks[1].use(players[1].currentPokemon, players[0].currentPokemon); }

                        if(first == 0 && players[1].currentPokemon.getHp() > 0){
                            attacks[1].use(players[1].currentPokemon, players[0].currentPokemon);
                        }
                        else if(first == 1 && players[0].currentPokemon.getHp() > 0){
                            attacks[0].use(players[0].currentPokemon, players[1].currentPokemon);
                        }
                    }
                    else if(attacks[0] != null){ attacks[0].use(players[0].currentPokemon, players[1].currentPokemon); }
                    else{ attacks[1].use(players[1].currentPokemon, players[0].currentPokemon); }
                }
                //UPDATING GAME
                update();
            }
        }
        printScreen(getScreen(players[0], players[1]));
    }

    public static void update(){
        //Updating basic game over
        for(int player = 0; player <= 1; player++){
            if(didPlayerLose(players[player])){
                System.out.println("A");
                if(player == 0) Main.addData(players[1].getName() + " won!");
                else Main.addData(players[0].getName() + " won!");
                gameOver = true;
                break;
            }
        }
        //Updating effects
        if(!gameOver){
            for(Pokemon x : players[0].getTeam()){ x.update(); }
            for(Pokemon x : players[1].getTeam()){ x.update(); }

            for(int player = 0; player <= 1; player++){
                if(didPlayerLose(players[player])){
                    System.out.println("B");
                    if(player == 0) Main.addData(players[1].getName() + " won!");
                    else Main.addData(players[0].getName() + " won!");
                    gameOver = true;
                    break;
                }
            }
            //Updating Options
            if(!gameOver){
                playerOptions[0] = new boolean[]{true, true, true, true};
                playerOptions[1] = new boolean[]{true, true, true, true};
                for(int player = 0; player <= 1; player++){
                    if(players[player].currentPokemon.getHp() == 0){
                        System.out.println("C");
                        //attack, switch, use item, forfeit
                        //playerOptions[player] = new boolean[]{false, true, false, true};
                        playerOptions[player][0] = false;
                        playerOptions[player][2] = false;
                    }
                    if(!players[player].currentPokemon.getCanMove()){
                        playerOptions[player][0] = false;
                    }
                }
            }
        }
    }

    public static Attack getAttack(Player player){
        System.out.print("Enter the number of the attack: ");
        int attackIndex = new Scanner(System.in).nextInt();
        Attack attack = player.currentPokemon.attacks[attackIndex - 1];
        //System.out.println(attackName);
        /*for(Attack x : player.currentPokemon.attacks) System.out.println(x.name);
        Attack attack = new Attack("", -1, -1, -1, -1, -1, "No");
        for(Attack x : player.currentPokemon.attacks){
            if(attack.name.equals(attackName)){
                System.out.println("FOUND ATTACK");
                attack = x;
                break;
            }
        }*/
        return attack;
    }

    public static Pokemon getSubstitute(Player player){
        Pokemon newPokemon = null;
        if(player.getTeam().size() > 0){
            boolean doLoop = true;
            while(doLoop){
                System.out.print("Please type the name of your substitute: ");
                String pokemonName = new Scanner(System.in).nextLine();
                for(Pokemon x : player.getTeam()){ if(x.getName().equals(pokemonName)){ newPokemon = x; break; } }
                if(newPokemon.getHp() > 0){ doLoop = false; }
                else{ System.out.println("Unable to use unconscious Pokemon."); newPokemon = null; }
            }
        }
        return newPokemon;
    }

    public static boolean didPlayerLose(Player player){
        boolean playerLost = true;
        for(Pokemon x : player.getTeam()){ if(x.getHp() > 0){ playerLost = false; break; } }
        return playerLost;
    }

    public static String[][] getScreen(Player player, Player otherPlayer){
        //Pokemon pokemon = Pokemon.pokedex.get(0);
        /*

        DATA SCREEN:
        40 < x < 55     (b1 = 40, b2 = 54)

        ATTACK SCREEN:
        rows - 10 < y
        Attacks are 10 width

        */
        int rows = 30;
        int columns = 55;
        int border = 1;

        int attackScreenY = rows - 11;
        int attackScreenX = border;
        int dataScreenY = border;
        int dataScreenX = columns - 11;
        String[][] screen = new String[rows][columns];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                screen[i][j] = " * ";
            }
        }

        for(int i = border; i < rows - border; i++){
            for(int j = border; j < columns - border; j++){
                screen[i][j] = "   ";
                if(i == attackScreenY){ screen[i][j] = " * "; } //Horizontal attack border
                if(j % 11 == 0 && i >= attackScreenY && j < 44){ screen[i][j] = " * "; } //Horizontal attack borders
                if(j == dataScreenX) screen[i][j] = " * "; //Vertical data border
                if(j > dataScreenX) screen[i][j] = " @ ";

                //Player's pokemon lists
                //if((j == border + 6) && i < attackScreenY) screen[i][j] = " * ";
                //if((j == dataScreenX - 7) && i < attackScreenY) screen[i][j] = " * ";
                if(((j == border + 6) || (j == dataScreenX - 7)) && i < attackScreenY) screen[i][j] = " * ";
                if(i == border + 1 && ((j < border + 6) || (j > dataScreenX - 7 && j < dataScreenX))) screen[i][j] = " * ";
                String header1 = players[0] + "'s Pokemon:";
            }
        }
        //Printing attacks
        for(int i = attackScreenY; i < rows; i++){
            for(int j = border; j < 44; j++){
                int localX = j - ((j / 11) * 11) - 1;
                int localY = i - attackScreenY - 1;
                if(screen[i][j].equals("   ")){
                    //LocalX is multiplied by 3 to get the start and end because every pixel of the screen is 3 chars long
                    int index;
                    int start = localX * 3;
                    int end = start + 3;

                    if(j < 11) index = 0;
                    else if(j < 22) index = 1;
                    else if(j < 33) index = 2;
                    else index = 3;

                    ArrayList<String> attackString = player.currentPokemon.attacks[index].getString();
                    if(localY < attackString.size()) screen[i][j] = attackString.get(localY).substring(start, end) + "";
                    else screen[i][j] = "   ";
                }
            }
        }

        int shift = 7;
        //Current Player's Pokemon Info
        ArrayList<String> chunk1 = player.currentPokemon.getString(true);
        for(int i = border; i < border + 3; i++){
            for(int j = border + shift; j < border + 13 + shift; j++){
                int localX = j - (border + shift);
                int localY = i - border;
                int start = localX * 3;
                int end = start + 3;
                screen[i][j] = chunk1.get(localY).substring(start, end);
            }
        }
        //Other Player's Pokemon Info
        ArrayList<String> chunk2 = otherPlayer.currentPokemon.getString(false);
        for(int i = attackScreenY - 3; i < attackScreenY; i++){
            for(int j = dataScreenX - 13 - shift; j < dataScreenX - shift; j++){
                int localX = j - (dataScreenX - 13 - shift);
                int localY = i - (attackScreenY - 3);
                int start = localX * 3;
                int end = start + 3;
                screen[i][j] = chunk2.get(localY).substring(start, end);
            }
        }
        //Data column
        for(int i = border; i < rows - border; i++){
            for(int j = dataScreenX + 1; j < columns - border; j++){
                //Updating Main.data
                for(int k = 0; k < Main.data.size(); k++){
                    String space = "                                                                 ".substring(0, 27);
                    Main.data.set(k, Main.data.get(k) + space.substring(0, 27 - Main.data.get(k).length()));
                }
                while(Main.data.size() > 28){ Main.data.remove(0); }
                //Adding data to screen
                int localX = (j - (dataScreenX + 1));
                int localY = i - border;
                int start = localX * 3;
                int end = start + 3;
                if(localY < Main.data.size()) screen[i][j] = Main.data.get(localY).substring(start, end) + "";
                else screen[i][j] = "   ";
            }
        }

        //Player 1 Pokemon List
        //Note to self: Make max length of user input 18
        String header = "Player One:       ";
        ArrayList<String> list1 = new ArrayList<>();
        for(Pokemon x : players[0].getTeam()){
            String name = x.getName();
            if(x.getHp() == 0){ name += " [F]"; }
            name += ("                  ").substring(0, 18 - name.length());
            list1.add(name);
        }
        for(int i = border; i < attackScreenY; i++){
            for(int j = border; j < border + 6; j++){
                int localX = j - border;
                int localY = i - border;
                int index = (localY / 2) - 3;
                screen[i][j] = "   ";
                if(localY == 0){ screen[i][j] = header.substring(localX * 3, (localX * 3) + 3); }
                if(localY % 2 == 0 && localY > 5){
                    if(index < list1.size()){ screen[i][j] = list1.get(index).substring(localX * 3, (localX * 3) + 3); }
                    else{ screen[i][j] = "   "; }
                }
            }
        }
        //Player 2 Pokemon List
        header = "Player Two:       ";
        if(gameplayType.equals("PVE")){ header = "Computer:         "; }
        ArrayList<String> list2 = new ArrayList<>();
        for(Pokemon x : players[1].getTeam()){
            String name = x.getName();
            if(x.getHp() == 0){ name += " [F]"; }
            name += ("                  ").substring(0, 18 - name.length());
            list2.add(name);
        }
        for(int i = border; i < attackScreenY; i++){
            for(int j = dataScreenX - 6; j < dataScreenX; j++){
                int localX = j - (dataScreenX - 6);
                int localY = i - border;
                int index = (localY / 2) - 3;
                screen[i][j] = "   ";
                if(localY == 0){ screen[i][j] = header.substring(localX * 3, (localX * 3) + 3); }
                if(localY % 2 == 0 && localY > 5){
                    if(index < list2.size()){ screen[i][j] = list2.get(index).substring(localX * 3, (localX * 3) + 3); }
                    else{ screen[i][j] = "   "; }
                }
                //screen[i][j] = "XXX";
            }
        }
        return screen;
    }

    public static ArrayList<String> squeezeText(int width, String text){
        text = getCleanedString(width, text); //Processing text so nothing is too long
        String space = "                                                                     ".substring(0, width);
        String[] list = text.split(" ");
        ArrayList<String> newList = new ArrayList<>();
        String line = "";
        int length = 0;
        for(int i = 0; i < list.length; i++){
            if(length + list[i].length() == width){
                line += (list[i]); //length += (list[i].length());
                newList.add(line);
                length = 0;
                line = "";
            }
            else if(length + list[i].length() + 1 <= width){
                line += (list[i] + " ");
                length += (list[i].length() + 1);
            }
            else{
                space = space.substring(line.length(), width);
                line += space;
                newList.add(line);
                line = list[i] + " ";
                length = (list[i].length() + 1);
            }
            space = "                                                                     ".substring(0, width);
        }

        newList.add(line + space.substring(0, width - line.length()));
        /*System.out.println("TEST");
        for(String x : newList){
            System.out.println(x);
        }*/
        return newList;
    }

    public static String getCleanedString(int width, String text){
        String[] list1 = text.split(" ");
        ArrayList<String> list2 = new ArrayList<>();
        String string = "";
        for(String x : list1){
            if(x.length() <= 27) list2.add(x);
            else{
                ArrayList<String> splitList = new ArrayList<>();
                splitList.add("");
                int length = 0;
                for(int i = 0; i < x.length(); i++){
                    int top = splitList.size() - 1;
                    if(length < width){ splitList.set(top, splitList.get(top) + x.charAt(i)); length++; }
                    else{ splitList.add(x.charAt(i) + ""); length = 1; }
                }
                for(String y : splitList){ list2.add(y); }
            }
        }
        for(String x : list2){ string += (x + " "); }
        return string;
    }

    public static void addData(String data){
        ArrayList<String> test = squeezeText(27, data);
        for(String x : test){ Main.data.add(x); }
    }

    public static void printScreen(String[][] screen){
        for(String[] x : screen){
            for(String y : x){
                System.out.print(y);
                if(y.length() > 55) System.out.println("\n\nTEST" + y.length() + "\n\n");
            }
            System.out.print("\n");
        }
    }

    public static void generateTeams(int team1Size, int team2Size){
        ArrayList<Pokemon> list = (ArrayList<Pokemon>) Pokemon.pokedex.clone();
        for(int i = 0; i < team1Size; i++){
            int index = 9999;
            while(!(index < list.size())){ index = (int) (Math.random() * (list.size() + 1)); }
            list.get(index).owner = 0; players[0].getTeam().add(list.get(index));
            list.remove(index);
        }
        for(int i = 0; i < team2Size; i++){
            int index = 9999;
            while(!(index < list.size())){ index = (int) (Math.random() * (list.size() + 1)); }
            list.get(index).owner = 1; players[1].getTeam().add(list.get(index));
            list.remove(index);
        }
        players[0].currentPokemon = players[0].getTeam().get((int) (Math.random() * players[0].getTeam().size()));
        players[1].currentPokemon = players[1].getTeam().get((int) (Math.random() * players[1].getTeam().size()));
    }
}
