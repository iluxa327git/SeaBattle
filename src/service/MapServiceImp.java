package service;

import entity.Map;
import entity.Ship;
import utils.Randomizer;


public class MapServiceImp implements MapService{

    public static Map getRandomMap() {
        Map map = new Map();

        int counter = 0;

        for (int i = 0; i < 6; ++i)
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
            while (!ShipServiceImp.isCorrect(ship, map.getMap())) {
                ship.setPosition(Randomizer.getRandomShipPos());
                ship.setVertical(Randomizer.getRandomBoolean());
            }

            if (ship.isVertical())
                map.setMap(ShipServiceImp.vertical(map.getMap(), ship));

            else
                map.setMap(ShipServiceImp.horizontal(map.getMap(), ship));

        }

        return map;
    }

    public Map generateMap(Ship[] ships){
        Map map = new Map();
        map.setShips(ships);
        return map;
    }

    public void printMap(Map[] map){
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
                else if(myMap.getMap()[i-1][j-1] == 1)
                    System.out.print("*");
                else
                    System.out.print("O");


            if(i < 10)
                System.out.print("    " + i + "  ");
            else
                System.out.print("    " + i + " ");

            for(int j = 1; j <= 16; ++j)
                if(enemyMap.getMap()[i-1][j-1] == 1)
                    System.out.print("X");
                else if(enemyMap.getMap()[i-1][j-1] == 2)
                    System.out.print("/");
                else
                    System.out.print("O");


            System.out.println();
        }

        System.out.println("\n   ABCDEFGHIJKLMNOP       ABCDEFGHIJKLMNOP");
    }

    public void gamePrintMap(Map[] map){
        Map myMap = map[0];
        Map enemyMap =  map[1];

        System.out.println("          YOU                   ENEMY\n");


        for(int i = 1; i <= 16; ++i) {
            if(i < 10)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");

            for(int j = 1; j <= 16; ++j)
                if(myMap.getMap()[i-1][j-1] == 1)
                    System.out.print("*");
                else if(myMap.getMap()[i-1][j-1] == 2)
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

        System.out.println("\n   ABCDEFGHIJKLMNOP       ABCDEFGHIJKLMNOP");
    }
}

