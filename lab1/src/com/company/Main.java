package com.company;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String line;
        Scanner input = new Scanner(System.in);

        GreetingLine();

        while (true){
            line = input.nextLine();
            if (line.length() > 17) { System.out.println("Input line too long, try again"); continue; }
            if (line.equals("--h")){ Main.Help(); continue;}
            if (line.equals("q")) { System.out.println("======= Exiting the program ======="); break;}

            LinkedList<Double> numbers;
            numbers = StrAdd.First2NumbersInStr(line);

            // 1- 3 options
            switch(numbers.size()){
                case 1:

                    System.out.printf("=>square = %.3f, cubic root of the square = %.3f\n", Math.pow(numbers.get(0), 2), Math.cbrt(Math.pow(numbers.get(0), 2)));

                    break;

                case 2:

                    if ((Math.abs(numbers.get(1)) < 0.0000000001)){
                        System.out.println("=>Division by zero! Try again.");
                        break;
                    }

                    System.out.printf("=>quotient = %.3f\n", numbers.get(0)/numbers.get(1));

                    break;
                case 0:

                    char[] ch_line = line.toCharArray();
                    Arrays.sort(ch_line);
                    System.out.print("\"");
                    System.out.print(ch_line);
                    System.out.println("\"");

                    break;
                default:
                    System.err.println("StrAdd.First2NumbersInStr(String str) - return more than 2 numbers!");
            }

        }

    }

    // Messages
    static void Help(){
        final String HELP_MESSAGE = "=== LAB1 - by Gurianov Vladimir ===\n"+
                                    " This program has several functions:\n"+
                                    " * Enter a number    -> you will get its square and the cubic root of the square of a number,\n" +
                                    " * Enter two numbers -> the first will be divided by the second\n" +
                                    " * Enter a string    -> it will be sorted(UNICODE in ascending order), and the number of unique characters in the string will be output\n" +
                                    " \"q\" for quit from application\n" +
                                    "\n----------";
        System.out.println(HELP_MESSAGE);
    }

    static void GreetingLine(){
        final String ENTRY_MESSAGE = "=== LAB1 - by Gurianov Vladimir ===\n" +
                                     " Type \"--h\" for help, and \"q\" for quit from application\n" +
                                     "===================================";
        System.out.println(ENTRY_MESSAGE);
    }


}
