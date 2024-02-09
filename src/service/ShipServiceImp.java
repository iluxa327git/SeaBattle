package service;

import entity.Map;
import entity.Ship;
import utils.Randomizer;

import java.util.Scanner;

public class ShipServiceImp implements ShipService{
    private static Scanner scanner = new Scanner(System.in);
    public Ship[] generateShip() {
        Ship[] ships = new Ship[21];

        int counter = 0;

        for (int i = 0; i < 6; ++i)
            for (int ii = 0; ii < 6 - i; ++ii) {
                Ship ship = new Ship();
                ship.setCellsCount(i + 1);
                ship.setCellsState(new boolean[i + 1]);

                ship.setPosition(new int[2]);

                ships[counter] = ship;
                counter++;
            }


        return ships;
    }

    public static int[][] vertical(int[][] map, Ship ship) {
        for(int i = 0; i < ship.getCellsCount(); ++i) {
            map[ship.getPosition()[0] + i][ship.getPosition()[1]] = 2;

            if(i == 0 && ship.getPosition()[0] != 0) {
                map[ship.getPosition()[0] + i - 1][ship.getPosition()[1]] = 1;

                if(ship.getPosition()[1] + 1 <= 15)
                    map[ship.getPosition()[0] + i - 1][ship.getPosition()[1] + 1] = 1;

                if(ship.getPosition()[1] - 1 >= 0)
                    map[ship.getPosition()[0] + i - 1][ship.getPosition()[1] - 1] = 1;
            }

            if(ship.getPosition()[1] + 1 <= 15)
                map[ship.getPosition()[0] + i][ship.getPosition()[1] + 1] = 1;

            if(ship.getPosition()[1] - 1 >= 0)
                map[ship.getPosition()[0] + i][ship.getPosition()[1] - 1] = 1;

            if(i == ship.getCellsCount() - 1 && ship.getPosition()[0] + ship.getCellsCount() <= 15) {
                map[ship.getPosition()[0] + i + 1][ship.getPosition()[1]] = 1;

                if(ship.getPosition()[1] + 1 <= 15)
                    map[ship.getPosition()[0] + i + 1][ship.getPosition()[1] + 1] = 1;

                if(ship.getPosition()[1] - 1 >= 0)
                    map[ship.getPosition()[0] + i + 1][ship.getPosition()[1] - 1] = 1;
            }
        }
        return map;
    }

    public static int[][] horizontal(int[][] map, Ship ship) {
        for (int i = 0; i < ship.getCellsCount(); ++i) {
            map[ship.getPosition()[0]][ship.getPosition()[1] + i] = 2;

            if (i == 0 && ship.getPosition()[1] != 0) {
                map[ship.getPosition()[0]][ship.getPosition()[1] + i - 1] = 1;

                if (ship.getPosition()[0] + 1 <= 15)
                    map[ship.getPosition()[0] + 1][ship.getPosition()[1] + i - 1] = 1;

                if (ship.getPosition()[0] - 1 >= 0)
                    map[ship.getPosition()[0] - 1][ship.getPosition()[1] + i - 1] = 1;
            }

            if (ship.getPosition()[0] + 1 <= 15)
                map[ship.getPosition()[0] + 1][ship.getPosition()[1] + i] = 1;

            if (ship.getPosition()[0] - 1 >= 0)
                map[ship.getPosition()[0] - 1][ship.getPosition()[1] + i] = 1;

            if (i == ship.getCellsCount() - 1 && ship.getPosition()[1] + i + 1 <= 15) {
                map[ship.getPosition()[0]][ship.getPosition()[1] + i + 1] = 1;

                if (ship.getPosition()[0] + 1 <= 15)
                    map[ship.getPosition()[0] + 1][ship.getPosition()[1] + i + 1] = 1;

                if (ship.getPosition()[0] - 1 >= 0)
                    map[ship.getPosition()[0] - 1][ship.getPosition()[1] + i + 1] = 1;
            }


        }
        return map;
    }
    public static boolean isCorrect(Ship ship, int[][] field) {
        boolean firstTry = ship.isVertical() && ship.getPosition()[0] + ship.getCellsCount() - 1 <= 15 ||
                !ship.isVertical() && ship.getPosition()[1] + ship.getCellsCount() - 1 <= 15 &&
                (ship.getPosition()[0] >= 0 && ship.getPosition()[1] >= 0);

        if(!firstTry)
            return false;

        for(int i = 0; i < ship.getCellsCount(); ++i)
            if(ship.isVertical()) {
                if(field[ship.getPosition()[0] + i][ship.getPosition()[1]] != 0)
                    return false;
            } else {
                if(field[ship.getPosition()[0]][ship.getPosition()[1] + i] != 0)
                    return false;
            }


        return true;
    }
}
