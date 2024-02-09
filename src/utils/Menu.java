package utils;

import entity.Match;
import entity.Player;
import entity.Ship;
import entity.enums.Role;
import service.MapServiceImp;
import service.ShipServiceImp;
import entity.Map;

import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static final ShipServiceImp shipServiceImp = new ShipServiceImp();
    private static final MapServiceImp mapServiceImp = new MapServiceImp();

    public static Map[] GenerateMaps(){
        Map[] maps = new Map[]{mapServiceImp.generateMap(shipServiceImp.generateShip()), new Map()};

        int[] position = new int[2];

        for(Ship ship : maps[0].getShips()){
            do{
                System.out.println("Введите номер первой клетки корабля: ");

                for (int j = 0; j < 2; j++)
                    position[j] = scanner.nextInt()-1;

                System.out.println("Вниз или вправо(1/2)?");
                ship.setPosition(position);

                if (scanner.nextInt() == 1)
                    ship.setVertical(true);

                if(!ShipServiceImp.isCorrect(ship, maps[0].getMap()))
                    System.out.println("Неврено введены координаты");
            }while(position[0] < 0 || position[1] < 0 || !ShipServiceImp.isCorrect(ship, maps[0].getMap()));

            if (ship.isVertical())
                maps[0].setMap(ShipServiceImp.vertical(maps[0].getMap(), ship));
            else
                maps[0].setMap(ShipServiceImp.horizontal(maps[0].getMap(), ship));

            mapServiceImp.printMap(maps);
        }
        return maps;
    }

    public static int[] attackCord(Map[] maps){
        int[] attack = new int[2];

        do {
            System.out.println("Введите координаты атаки: ");

            for (int i = 0; i < 2; i++) {
                attack[i] = scanner.nextInt() - 1;
            }
            if(attack[0] < 0  || attack[0] > 15 || attack[1] < 0 || attack[1] > 15)
                System.out.println("Неверно введены координаты");
        }while(attack[0] < 0  || attack[0] > 15 || attack[1] < 0 || attack[1] > 15) ;

        return attack;

    }

    public static void game(){

        MapServiceImp mapServiceImp = new MapServiceImp();

        int gameMod;

        do {
            System.out.println("Игра с другом/Игра с ботом(1/2)");
            gameMod = scanner.nextInt();

            switch (gameMod){
                case 1:
                    multiplayer();



                    break;

                case 2:
                    singleplayer();
                    break;

                default:
                    System.out.println("XYZ");
                    break;
            }
        }while(gameMod != 1 && gameMod != 0);

        /*switch(gameMod){
            case 1:
                Player player1 = new Player();
                Player player2 = new Player();

                //1
                System.out.println("Введите имя первого игрока: ");
                player1.setName(scanner.next());

                System.out.println("Поле рандомное/Поле вручную(1/2)");
                int mod1 = scanner.nextInt();

                Map[] maps1 = new Map[2];

                do {
                    switch (mod1) {
                        case 1:
                            System.out.println("Ввод первого игрока: ");
                            maps1 = new Map[]{MapServiceImp.getRandomMap(), new Map()};
                        case 2:
                            System.out.println("Ввод первого игрока: ");
                            maps1 = GenerateMaps();
                        default:
                            System.out.println("Неверный ввод");
                    }

                    mapServiceImp.printMap(maps1);

                }while(mod1 != 0 && mod1 != 1);

                //2
                System.out.println("Введите имя второго игрока: ");
                player1.setName(scanner.next());

                System.out.println("Поле рандомное/Поле вручную(1/2)");
                int mod2 = scanner.nextInt();

                Map[] maps2 = new Map[2];

                do {
                    switch (mod2) {
                        case 1:
                            System.out.println("Ввод первого игрока: ");
                            maps2 = new Map[]{MapServiceImp.getRandomMap(), new Map()};
                        case 2:
                            System.out.println("Ввод первого игрока: ");
                            maps2 = GenerateMaps();
                        default:
                            System.out.println("Неверный ввод");
                    }

                    mapServiceImp.printMap(maps1);

                }while(mod2 != 0 && mod2 != 1);

                match.getMatch().put(player1, maps1);
                match.getMatch().put(player2, maps2);









        //Player bot = new Player();
        //Map[] mapsBot = new Map[2];
        //mapsBot = new Map[]{MapServiceImp.getRandomMap(), new Map()};
        }*/
    }

    private static void singleplayer(){
        Match match = new Match();

        Player player = new Player();
        player.setRole(Role.USER);

        Player bot = new Player("Bot", Role.BOT);

        System.out.println("Введите имя первого игрока: ");
        player.setName(scanner.next());

        System.out.println("Поле рандомное/Поле вручную(1/2)");
        int choice;

        do{
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    match.getMatch().put(player, new Map[]{MapServiceImp.getRandomMap(), new Map()});
                    mapServiceImp.printMap(match.getMatch().get(player));

                    break;

                case 2:
                    match.getMatch().put(player, GenerateMaps());
                    break;

                default:
                    System.out.println("Неверный ввод");
                    break;
            }

        } while (!(choice == 1 || choice == 2));

        match.getMatch().put(bot, new Map[]{MapServiceImp.getRandomMap(), new Map()});

        boolean isGameOver = false;
        boolean isPlayer = true;

        int firstPlayerKilledCells = 0;
        int secondPlayerKilledCells = 0;

        while(!isGameOver){
            if(isPlayer){
                System.out.println(String.format("Ход игрока %s", player.getName()));
                int[] attack1 = attackCord(match.getMatch().get(player));

                if(match.getMatch().get(bot)[0].getMap()[attack1[0]][attack1[1]] == 2){
                    match.getMatch().get(player)[1].getMap()[attack1[0]][attack1[1]] = 4;
                    mapServiceImp.gamePrintMap(match.getMatch().get(player));
                    System.out.println("ПОПАЛ!");

                    firstPlayerKilledCells += 1;
                    if(firstPlayerKilledCells == 56) {
                        System.out.println(String.format("Победил игрок: %s", bot.getName()));
                        System.out.println("ГЕЙМ ОВЕР");
                        break;
                    }
                }
                else {
                    match.getMatch().get(player)[1].getMap()[attack1[0]][attack1[1]] = 3;
                    mapServiceImp.gamePrintMap(match.getMatch().get(player));

                    System.out.println("Промах, для перехода хода введите что-нибудь: ");

                    scanner.next();

                    isPlayer = !isPlayer;
                }
            }
            else{
                int[] attack2 = Randomizer.getRandomShipPos();

                System.out.println(String.format("Ход игрока: %s", bot.getName()));

                if(match.getMatch().get(player)[0].getMap()[attack2[0]][attack2[1]] == 2){
                    match.getMatch().get(bot)[1].getMap()[attack2[0]][attack2[1]] = 4;
                    System.out.println("БОТ ПОПАЛ!");

                    secondPlayerKilledCells += 1;
                    if(secondPlayerKilledCells == 56) {
                        System.out.println(String.format("ПОбедил игрок: %s", player.getName()));
                        System.out.println("ГЕЙМ ОВЕР");
                        break;
                    }
                }
                else {
                    match.getMatch().get(bot)[1].getMap()[attack2[0]][attack2[1]] = 3;


                    System.out.println("БОТ ПРОМАЗАЛ!");
                    mapServiceImp.gamePrintMap(match.getMatch().get(player));

                    isPlayer = !isPlayer;

                }
            }

        }

        //Игра с ботом
    }

    private static void  multiplayer(){
        Match match = new Match();

        Player firstPlayer = new Player();
        Player secondPlayer = new Player();

        //1
        System.out.println("Введите имя первого игрока: ");
        firstPlayer.setName(scanner.next());

        System.out.println("Поле рандомное/Поле вручную(1/2)");
        int choice;

        do{
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    match.getMatch().put(firstPlayer, new Map[]{MapServiceImp.getRandomMap(), new Map()});
                    mapServiceImp.printMap(match.getMatch().get(firstPlayer));
                    break;

                case 2:
                    match.getMatch().put(firstPlayer, GenerateMaps());
                    break;

                default:
                    System.out.println("Неверный ввод");
                    break;
            }

        } while (!(choice == 1 || choice == 2));

        //2
        System.out.println("Введите имя второго игрока: ");
        secondPlayer.setName(scanner.next());

        System.out.println("Поле рандомное/Поле вручную(1/2)");

        do{
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    match.getMatch().put(secondPlayer, new Map[]{MapServiceImp.getRandomMap(), new Map()});
                    mapServiceImp.printMap(match.getMatch().get(secondPlayer));
                    break;

                case 2:
                    match.getMatch().put(secondPlayer, GenerateMaps());
                    break;

                default:
                    System.out.println("Неверный ввод");
                    break;
            }
        } while (!(choice == 1 || choice == 2));

        boolean isGameOver = false;
        boolean isPlayer1 = true;

        int firstPlayerKilledCells = 0;
        int secondPlayerKilledCells = 0;

        while(!isGameOver){
            if(isPlayer1){
                int[] attack1 = new int[2];

                System.out.println(String.format("Ход игрока %s", firstPlayer.getName()));
                attack1 = attackCord(match.getMatch().get(firstPlayer));

                if(match.getMatch().get(secondPlayer)[0].getMap()[attack1[0]][attack1[1]] == 2){
                    match.getMatch().get(firstPlayer)[1].getMap()[attack1[0]][attack1[1]] = 4;
                    mapServiceImp.gamePrintMap(match.getMatch().get(firstPlayer));
                    System.out.println("ПОПАЛ!");

                    firstPlayerKilledCells += 1;
                    if(firstPlayerKilledCells == 56) {
                        System.out.println(String.format("ПОбедил игрок: %s", secondPlayer.getName()));
                        System.out.println("ГЕЙМ ОВЕР");
                        break;
                    }
                }
                else {
                    match.getMatch().get(firstPlayer)[1].getMap()[attack1[0]][attack1[1]] = 3;
                    mapServiceImp.gamePrintMap(match.getMatch().get(firstPlayer));
                    int ind;
                    System.out.println("Промах, для перехода хода введите 1: ");
                    do{
                        ind = scanner.nextInt();
                    }while(ind != 1);

                    mapServiceImp.gamePrintMap(match.getMatch().get(secondPlayer));
                    isPlayer1 = !isPlayer1;

                }
            }
            else{
                int[] attack2 = attackCord(match.getMatch().get(secondPlayer));

                System.out.println(String.format("Ход игрока: %s", secondPlayer.getName()));

                if(match.getMatch().get(firstPlayer)[0].getMap()[attack2[0]][attack2[1]] == 2){
                    match.getMatch().get(secondPlayer)[1].getMap()[attack2[0]][attack2[1]] = 4;
                    mapServiceImp.gamePrintMap(match.getMatch().get(secondPlayer));

                    secondPlayerKilledCells += 1;
                    if(secondPlayerKilledCells == 56) {
                        System.out.println(String.format("ПОбедил игрок: %s", firstPlayer.getName()));
                        System.out.println("ГЕЙМ ОВЕР");
                        break;
                    }
                }
                else {
                    match.getMatch().get(secondPlayer)[1].getMap()[attack2[0]][attack2[1]] = 3;
                    mapServiceImp.gamePrintMap(match.getMatch().get(secondPlayer));
                    int ind;
                    System.out.println("Промах, для перехода хода введите 1: ");
                    do{
                        ind = scanner.nextInt();
                    }while(ind != 1);

                    mapServiceImp.gamePrintMap(match.getMatch().get(firstPlayer));
                    isPlayer1 = !isPlayer1;

                }
            }

        }

    }
}
