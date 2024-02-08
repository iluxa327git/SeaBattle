package utils;

import entity.Match;
import entity.Player;
import entity.Ship;
import service.MapServiceImp;
import service.ShipServiceImp;
import entity.Map;
import service.TmpCheckout;

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
                    System.out.println("ХУЙ");
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
        Match match = new Match();
        MapServiceImp mapServiceImp = new MapServiceImp();

        Player player1 = new Player();
        Player player2 = new Player();

        System.out.println("Введите имя первого игрока: ");
        player1.setName(scanner.next());

        System.out.println("Ввод первого игрока: ");
        Map[] maps1 = new Map[]{TmpCheckout.getRandomField(), new Map()}; //TODO get back GetMaps()

        mapServiceImp.printMap(maps1);

        System.out.println("Введите имя второго игрока: ");
        player2.setName(scanner.next());

        System.out.println("Ввод второго игрока: ");
        Map[] maps2 = new Map[]{TmpCheckout.getRandomField(), new Map()}; //TODO get back GetMaps()

        mapServiceImp.printMap(maps2);

        match.getMatch().put(player1, maps1);
        match.getMatch().put(player2, maps2);

        boolean isGameOver = false;
        boolean isPlayer1 = true;

        int killCount1 = 0;
        int killCount2 = 0;

        while(!isGameOver){
            if(isPlayer1){
                int[] attack1 = new int[2];

                System.out.println(String.format("Ход игрока %s", player1.getName()));
                attack1 = attackCord(maps1);

                if(maps2[0].getMap()[attack1[0]][attack1[1]] == 2){
                    maps1[1].getMap()[attack1[0]][attack1[1]] = 4;
                    mapServiceImp.gamePrintMap(maps1);

                    killCount2 += 1;
                    if(killCount2 == 33) {
                        System.out.println(String.format("ПОбедил игрок: %s", player2.getName()));
                        System.out.println("ГЕЙМ ОВЕР");
                        break;
                    }
                }
                else {
                    maps1[1].getMap()[attack1[0]][attack1[1]] = 3;
                    mapServiceImp.gamePrintMap(maps1);
                    int ind;
                    System.out.println("Промах, для перехода хода введите 1: ");
                    do{
                        ind = scanner.nextInt();
                    }while(ind != 1);

                    mapServiceImp.gamePrintMap(maps2);
                    isPlayer1 = !isPlayer1;

                }
            }
            else{
                int[] attack2 = new int[2];

                System.out.println(String.format("Ход игрока %s", player2.getName()));
                attack2 = attackCord(maps2);

                if(maps1[0].getMap()[attack2[0]][attack2[1]] == 2){
                    maps2[1].getMap()[attack2[0]][attack2[1]] = 4;
                    mapServiceImp.gamePrintMap(maps2);

                    killCount2 += 1;
                    if(killCount1 == 33) {
                        System.out.println(String.format("ПОбедил игрок: %s", player1.getName()));
                        System.out.println("ГЕЙМ ОВЕР");
                        break;
                    }
                }
                else {
                    maps2[1].getMap()[attack2[0]][attack2[1]] = 3;
                    mapServiceImp.gamePrintMap(maps2);
                    int ind;
                    System.out.println("Промах, для перехода хода введите 1: ");
                    do{
                        ind = scanner.nextInt();
                    }while(ind != 1);

                    mapServiceImp.gamePrintMap(maps1);
                    isPlayer1 = !isPlayer1;

                }
            }

        }
    }
}
