package application;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    public static final String ENTRY_MESSAGE[] = {
            "Choose which framework you want to use:",
            "SpringData JPA",
            "Spring JDBC",
    };

    public static final String MAIN_MESSAGE[] = {
            "--- Main options ---",
            "Create Person",
            "Show Persons",
            "Update Person",
            "Delete Person",
    };

    public static int showAndChooseOption(final String[] message){
        System.out.println(message[0]);
        if ((Arrays.equals(message, MAIN_MESSAGE))||(Arrays.equals(message, ENTRY_MESSAGE))){
            System.out.println("0. - Close application");
        }else{
            System.out.println("0. - Quit");
        }

        for (int i = 1; i < message.length; i++){
            System.out.println(i + ". - " + message[i]);
        }

        int c;
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Choose option:");
            try {
                c = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect input. Type number from 0 to " + message.length);
                continue;
            }
            if ((c >= 0)&&(c <= message.length)){
                break;
            }else{
                System.out.println("Incorrect input. Type number from 0 to " + message.length);
            }
        }

        return c;
    }

}
