package service;

import entity.Map;
import entity.Ship;

public class MapServiceImp implements MapService{

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

