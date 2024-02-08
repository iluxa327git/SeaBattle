package utils;

import java.util.Random;

public class Randomizer {
    private static final Random random = new Random();

    public static int[] getRandomShipPos() {
        int[] pos = new int[2]; //x, y

        pos[0] = random.ints(0, 15).findFirst().getAsInt();
        pos[1] = random.ints(0, 15).findFirst().getAsInt();

        return pos;
    }

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

}