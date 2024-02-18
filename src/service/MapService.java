package service;

import entity.Map;
import entity.Ship;
import utils.Randomizer;

public class MapService {

    public static Map getRandomMap() {
        Map map = new Map();

        int counter = 0;

        for (int i = 5; i >= 0; --i)
            for (int ii = 0; ii < 6 - i; ++ii) {
                Ship ship = new Ship();

                ship.setCellsCount(i + 1);
                ship.setCellsState(new boolean[i + 1]);
                ship.setPosition(Randomizer.getRandomShipPos());
                ship.setVertical(Randomizer.getRandomBoolean());

                map.getShips()[counter] = ship;

                counter++;
            }

        for (Ship ship : map.getShips()) {
            while (!ShipService.isCorrect(ship, map.getMap())) {
                ship.setPosition(Randomizer.getRandomShipPos());
                ship.setVertical(Randomizer.getRandomBoolean());
            }

            if (ship.isVertical())
                map.setMap(ShipService.vertical(map.getMap(), ship));
            else
                map.setMap(ShipService.horizontal(map.getMap(), ship));
        }

        return map;
    }

    public static Map generateMap(Ship[] ships){
        Map map = new Map();
        map.setShips(ships);
        return map;
    }

    public static void gamePrintMap(Map[] map){
        Map myMap = map[0];
        Map enemyMap =  map[1];

        System.out.println("          YOU                   ENEMY\n");

        for(int i = 1; i <= 16; ++i) {
            if(i < 10)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");

            for(int j = 1; j <= 16; ++j)
                if(myMap.getMap()[i-1][j-1] == 2)
                    System.out.print("#");
                else
                    System.out.print("O");

            if(i < 10)
                System.out.print("    " + i + "  ");
            else
                System.out.print("    " + i + " ");

            for(int j = 1; j <= 16; ++j)
                if(enemyMap.getMap()[i-1][j-1] == 3)
                    System.out.print("*");
                else if(enemyMap.getMap()[i-1][j-1] == 4)
                    System.out.print("X");
                else
                    System.out.print("O");

            System.out.println();
        }

        System.out.println("   1234567891111111       1234567891111111");
        System.out.println("            0123456                0123456");
    }
}

