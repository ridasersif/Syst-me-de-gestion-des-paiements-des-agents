package util;

import java.util.Scanner;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }
    public static int readInt(String prompt) {
        System.out.print(prompt + ": ");
        while (!scanner.hasNextInt()) {
            System.out.print("Erreur! " + prompt + ": ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
    public static double readDouble(String prompt) {
        System.out.print(prompt + ": ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Erreur! " + prompt + ": ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
    public static boolean readYesNo(String prompt) {
        System.out.print(prompt + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}
