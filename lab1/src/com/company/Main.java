package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String line;
        Scanner input = new Scanner(System.in);

        greetingLine();

        while (true) {

            line = input.nextLine();   // Warning: NoSuchElementsException

            if (line.length() > 17) {
                System.out.println("Input line is too long, try again");
                continue;
            }
            if ("--h".equals(line)) {
                Main.help();
                continue;
            }
            if ("q".equals(line)) {
                System.out.println("======= Exiting the program =======");
                break;
            }

            List<Double> numbers = StrAddUtils.first2NumbersInStr(line);

            // 1- 3 options
            switch (numbers.size()) {
                case 0:
                    char[] ch_line = line.toCharArray();
                    HashSet<Character> charUnique = new HashSet<Character>();

                    for (char ch : ch_line){
                           charUnique.add(ch);
                    }

                    Arrays.sort(ch_line);
                    System.out.print("\"");
                    System.out.print(ch_line);
                    System.out.print("\"");
                    System.out.println(" The number of unique characters in the string = " + charUnique.size());

                    break;
                case 1:
                    System.out.printf("=>square = %.3f, cubic root of the square = %.3f\n", Math.pow(numbers.get(0), 2),
                            Math.cbrt(Math.pow(numbers.get(0), 2)));
                    break;
                case 2:
                    if ((Math.abs(numbers.get(1)) < 0.000000001)) {
                        System.out.println("=>Division by zero! Try again.");
                        break;
                    }
                    System.out.printf("=>quotient = %.3f\n", numbers.get(0) / numbers.get(1));
                    break;
                default:
                    System.err.println("StrAdd.First2NumbersInStr(String str) - return more than 2 numbers!");
            }

        }

    }

    // Messages
    static private void help() {
        final String HELP_MESSAGE = "=== LAB1 - by Gurianov Vladimir ===\n" +
                " This program has several functions:\n" +
                " * Enter a number    -> you will get its square and the cubic root of the square of a number,\n" +
                " * Enter two numbers -> the first will be divided by the second\n" +
                " * Enter a string    -> it will be sorted(UNICODE in ascending order), and the number of unique characters in the string will be output\n" +
                " \"q\" for quit from application\n" +
                "\n----------";
        System.out.println(HELP_MESSAGE);
    }

    static private void greetingLine() {
        final String ENTRY_MESSAGE = "=== LAB1 - by Gurianov Vladimir ===\n" +
                " Type \"--h\" for help, and \"q\" for quit from application\n" +
                "===================================";
        System.out.println(ENTRY_MESSAGE);
    }


}
