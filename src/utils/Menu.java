package utils;

import entity.Match;
import entity.Player;
import entity.Ship;
import entity.enums.Role;
import service.MapService;
import service.ShipService;
import entity.Map;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private static boolean checkPosition(String[] pos){
        for(String check : pos)
            if(!check.matches("[-+]?\\d+"))
                return  false;

        return true;
    }

    public static Map[] generateMaps(){
        Map[] maps = new Map[]{MapService.generateMap(ShipService.generateShip()), new Map()};

        String[] inputPosition = new String[2];

        int[] position;

        for(Ship ship : maps[0].getShips()){
            do{
                position = new int[2];

                do {
                    System.out.println("Enter the number of the first cell of the ship: ");

                    for (int i = 0; i < 2; i++)
                        inputPosition[i] = scanner.next();

                    if(!checkPosition(inputPosition))
                        System.out.println("Invalid input");
                }while(!checkPosition(inputPosition));

                position[0] = Integer.valueOf(inputPosition[0])-1;
                position[1] = Integer.valueOf(inputPosition[1])-1;

                ship.setPosition(position);

                String choice;

                do {
                    System.out.println("Down or right(1/2)?");
                    choice = scanner.next();

                    switch (choice){
                        case "1":
                            ship.setVertical(true);
                            break;

                        case "2":
                            ship.setVertical(false);
                            break;

                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                }while(!(choice.equals("1") || choice.equals("2")));

                if(position[0] < 0 || position[1] < 0 || !ShipService.isCorrect(ship, maps[0].getMap()))
                    System.out.println("Invalid input");

            }while(position[0] < 0 || position[1] < 0 || !ShipService.isCorrect(ship, maps[0].getMap()));

            if (ship.isVertical())
                maps[0].setMap(ShipService.vertical(maps[0].getMap(), ship));
            else
                maps[0].setMap(ShipService.horizontal(maps[0].getMap(), ship));

            MapService.gamePrintMap(maps);
        }

        return maps;
    }

    public static int[] attackCord(int[][] map){
        String[] inputAttack = new String[2];

        int[] attack = new int[2];

        do {
            do {
                System.out.println("Enter attack coordinates: ");

                for (int i = 0; i < 2; i++)
                    inputAttack[i] = scanner.next();

                if(!checkPosition(inputAttack))
                    System.out.println("Invalid input");

                if(map[Integer.valueOf(inputAttack[0])-1][Integer.valueOf(inputAttack[1])-1] == 3 || map[Integer.valueOf(inputAttack[0])-1][Integer.valueOf(inputAttack[1])-1] == 4)
                    System.out.println("This cell has already been attacked");

            }while(!checkPosition(inputAttack) || map[Integer.valueOf(inputAttack[0])-1][Integer.valueOf(inputAttack[1])-1] == 3 || map[Integer.valueOf(inputAttack[0])-1][Integer.valueOf(inputAttack[1])-1] == 4);

            attack[0] = Integer.valueOf(inputAttack[0])-1;
            attack[1] = Integer.valueOf(inputAttack[1])-1;


            if(isUnCorrectPos(attack))
                System.out.println("Incorrect coordinates entered");
        }while(isUnCorrectPos(attack)) ;

        return attack;
    }

    private static boolean isUnCorrectPos(int[] attack) {
        return attack[0] < 0 || attack[0] > 15 || attack[1] < 0 || attack[1] > 15;
    }

    public static void game(){
        String choice;

        do {
            System.out.println("Playing with a friend/Playing with a bot(1/2)");
            choice = scanner.next();

            switch (choice){
                case "1":
                    multiplayer();
                    break;

                case "2":
                    singleplayer();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
        }while(!(choice.equals("1") || choice.equals("2")));
    }

    private static void singleplayer(){
        Match match = new Match();

        Player player = new Player();
        player.setRole(Role.USER);

        Player bot = new Player("Bot", Role.BOT);

        System.out.println("Enter the first player's name: ");
        player.setName(scanner.next());

        System.out.println("Random field/Manual field(1/2)");
        String choice;

        do{
            choice = scanner.next();

            switch (choice){
                case "1":
                    match.getMatch().put(player, new Map[]{MapService.getRandomMap(), new Map()});
                    MapService.gamePrintMap(match.getMatch().get(player));
                    break;

                case "2":
                    match.getMatch().put(player, generateMaps());
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }

        } while (!(choice.equals("1") || choice.equals("2")));

        match.getMatch().put(bot, new Map[]{MapService.getRandomMap(), new Map()});

        boolean isGameOver = false;
        boolean isPlayer = true;

        int firstPlayerKilledCells = 0;
        int secondPlayerKilledCells = 0;

        int[] attack;

        while(!isGameOver){
            if(isPlayer){
                System.out.printf("Player move: %s%n", player.getName());
                attack = attackCord(match.getMatch().get(player)[1].getMap());

                if(match.getMatch().get(bot)[0].getMap()[attack[0]][attack[1]] == 2){
                    match.getMatch().get(player)[1].getMap()[attack[0]][attack[1]] = 4;

                    ClearConsole.clearConsole();
                    MapService.gamePrintMap(match.getMatch().get(player));
                    System.out.println("GOTTEN!");

                    firstPlayerKilledCells += 1;

                    if(firstPlayerKilledCells == 56) {
                        System.out.printf("Player won: %s%n", bot.getName());
                        System.out.println("GAME OVER");
                        break;
                    }
                }
                else {
                    ClearConsole.clearConsole();

                    match.getMatch().get(player)[1].getMap()[attack[0]][attack[1]] = 3;
                    MapService.gamePrintMap(match.getMatch().get(player));

                    System.out.println("Miss, to move forward, enter something: ");

                    scanner.next();

                    ClearConsole.clearConsole();

                    isPlayer = false;
                }
            }
            else{
                attack = Randomizer.getRandomShipPos();

                System.out.printf("Player move: %s%n", bot.getName());

                if(match.getMatch().get(player)[0].getMap()[attack[0]][attack[1]] == 2){
                    match.getMatch().get(player)[0].getMap()[attack[0]][attack[1]] = 4;

                    ClearConsole.clearConsole();

                    System.out.println("THE BOT HAS GOT IT!");

                    MapService.gamePrintMap(match.getMatch().get(player));

                    secondPlayerKilledCells += 1;

                    if(secondPlayerKilledCells == 56) {
                        System.out.printf("Player won: %s%n", player.getName());
                        System.out.println("GAME OVER");
                        break;
                    }
                }
                else {
                    ClearConsole.clearConsole();

                    match.getMatch().get(player)[0].getMap()[attack[0]][attack[1]] = 3;

                    System.out.println("BOT MISSED!");

                    MapService.gamePrintMap(match.getMatch().get(player));

                    isPlayer = true;
                }
            }
        }
    }

    private static void  multiplayer(){
        Match match = new Match();

        Player firstPlayer = new Player();
        Player secondPlayer = new Player();

        System.out.println("Enter the first player's name: ");
        firstPlayer.setName(scanner.next());

        System.out.println("Random field/Manual field(1/2)");
        String choice;

        do{
            choice = scanner.next();

            switch (choice){
                case "1":
                    match.getMatch().put(firstPlayer, new Map[]{MapService.getRandomMap(), new Map()});
                    MapService.gamePrintMap(match.getMatch().get(firstPlayer));
                    break;

                case "2":
                    match.getMatch().put(firstPlayer, generateMaps());
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }

        } while (!(choice.equals("1") || choice.equals("2")));

        System.out.println("Enter something: ");
        scanner.next();

        ClearConsole.clearConsole();

        System.out.println("Enter the second player's name: ");
        secondPlayer.setName(scanner.next());

        System.out.println("Random field/Manual field(1/2)");

        do{
            choice = scanner.next();

            switch (choice){
                case "1":
                    match.getMatch().put(secondPlayer, new Map[]{MapService.getRandomMap(), new Map()});
                    MapService.gamePrintMap(match.getMatch().get(secondPlayer));
                    break;

                case "2":
                    match.getMatch().put(secondPlayer, generateMaps());
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (!(choice.equals("1") || choice.equals("2")));

        System.out.println("Enter something: ");
        scanner.next();

        ClearConsole.clearConsole();

        boolean isGameOver = false;
        boolean isPlayer1 = true;

        int firstPlayerKilledCells = 0;
        int secondPlayerKilledCells = 0;

        int[] attack;

        while(!isGameOver){
            if(isPlayer1){
                ClearConsole.clearConsole();

                System.out.printf("Player move: %s%n", firstPlayer.getName());

                MapService.gamePrintMap(match.getMatch().get(firstPlayer));

                attack = attackCord(match.getMatch().get(firstPlayer)[1].getMap());

                if(match.getMatch().get(secondPlayer)[0].getMap()[attack[0]][attack[1]] == 2){
                    match.getMatch().get(firstPlayer)[1].getMap()[attack[0]][attack[1]] = 4;
                    match.getMatch().get(secondPlayer)[0].getMap()[attack[0]][attack[1]] = 4;

                    MapService.gamePrintMap(match.getMatch().get(firstPlayer));
                    System.out.println("GOTTEN!");

                    firstPlayerKilledCells += 1;

                    if(firstPlayerKilledCells == 56) {
                        ClearConsole.clearConsole();

                        System.out.printf("Player won: %s%n", firstPlayer.getName());
                        System.out.println("GAME OVER");
                        break;
                    }
                }
                else {
                    ClearConsole.clearConsole();

                    match.getMatch().get(firstPlayer)[1].getMap()[attack[0]][attack[1]] = 3;
                    match.getMatch().get(secondPlayer)[0].getMap()[attack[0]][attack[1]] = 3;
                    MapService.gamePrintMap(match.getMatch().get(firstPlayer));

                    System.out.println("Miss, to move forward, enter something: ");
                    scanner.next();

                    ClearConsole.clearConsole();

                    MapService.gamePrintMap(match.getMatch().get(secondPlayer));
                    isPlayer1 = false;
                }
            }
            else{
                ClearConsole.clearConsole();

                System.out.printf("Player move: %s%n", secondPlayer.getName());

                MapService.gamePrintMap(match.getMatch().get(secondPlayer));

                attack = attackCord(match.getMatch().get(secondPlayer)[1].getMap());

                if(match.getMatch().get(firstPlayer)[0].getMap()[attack[0]][attack[1]] == 2){
                    match.getMatch().get(secondPlayer)[1].getMap()[attack[0]][attack[1]] = 4;
                    match.getMatch().get(firstPlayer)[0].getMap()[attack[0]][attack[1]] = 4;

                    MapService.gamePrintMap(match.getMatch().get(secondPlayer));
                    System.out.println("GOTTEN!");

                    secondPlayerKilledCells += 1;

                    if(secondPlayerKilledCells == 56) {
                        ClearConsole.clearConsole();

                        System.out.printf("Player won: %s%n", secondPlayer.getName());
                        System.out.println("GAME OVER");
                        break;
                    }
                }
                else {
                    match.getMatch().get(secondPlayer)[1].getMap()[attack[0]][attack[1]] = 3;
                    match.getMatch().get(firstPlayer)[0].getMap()[attack[0]][attack[1]] = 3;

                    ClearConsole.clearConsole();

                    MapService.gamePrintMap(match.getMatch().get(secondPlayer));

                    System.out.println("Miss, to move forward, enter something: ");
                    scanner.next();

                    MapService.gamePrintMap(match.getMatch().get(firstPlayer));
                    isPlayer1 = true;

                }
            }
        }
    }
}
