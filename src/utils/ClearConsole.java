package utils;

public class ClearConsole {
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
