import management.CommandQueue;
import management.commands.Command;
import management.Controller;
import management.Dispatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    static private Controller mainController = null;
    static private Dispatcher mainDispatcher = null;

    public static void main(String[] args) {

        System.out.println("Select the program mode\n0. Quit from program\n" +
                "1. Create commands with DialogCommandMaker\n" +
                "2. Run Controller, Dispatcher and People Service to execute commands\n" +
                "3. Performing 1 and 2 items at the same time");
        Scanner sc = new Scanner(System.in);
        int m;
        while (true) {
            try {
                m = sc.nextInt();
                if ((m >= 0) && (m <= 3)) {
                    break;
                }
                System.out.println("Incorrect number! Enter a number from 0 to 3");
            } catch (InputMismatchException ime) {
                System.out.println("Incorrect format! Enter a number from 0 to 3 to choose action");
                sc.nextLine();
            }
        }

        CommandQueue<Command> commandsForPeopleService = new CommandQueue<>();

        if (m >= 2) {
            Path pathToFolder = Paths.get("./target/Errors/OriginalFiles/");
            if (!Files.exists(pathToFolder)) {
                if (!(new File("./target/Errors/OriginalFiles/").mkdirs())) {
                    System.out.println("Can't create Error folder in ./target/Errors/OriginalFiles/");
                }else{
                    System.out.println("Error folder in ./target/Errors");
                }
            }else{ System.out.println("Error folder in ./target/Errors");}
            initControllerAndDispatcher(commandsForPeopleService);

            if (m == 2) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Enter something - to quit");
                while ((sc.next() == null)&&((mainDispatcher.isAlive()||(mainController.isAlive())))){
                }
                quitFromControllerAndDispatcher();
                return;
            }
        }

        if ((m == 1) || (m == 3)) {

            while (true) {

                DialogCommandMaker dcm = new DialogCommandMaker("./target/ManagementFolder/");
                int c = 1;
                while (c != 0) {
                    System.out.println("DialogCommandMaker path: " + dcm.getPathToManagementFolder());
                    c = dcm.showMenuAndChoose(DialogCommandMaker.availableCommands);
                    switch (c) {
                        default:
                            break;
                        case 1:
                            dcm.enterAndSetNewPath();
                            break;
                        case 2:
                            try {
                                dcm.endCommand();
                            } catch (IOException ioe) {
                                System.out.println(ioe.getMessage());
                            }
                            break;
                        case 3:
                            int c3 = dcm.showMenuAndChoose(DialogCommandMaker.createCommands);
                            switch (c3) {
                                default:
                                    break;
                                case 1:
                                    try {
                                        dcm.createStudent();
                                    } catch (IOException ioe) {
                                        System.out.println(ioe.getMessage());
                                    }
                                    break;
                                case 2:
                                    try {
                                        dcm.createTeacher();
                                    } catch (IOException ioe) {
                                        System.out.println(ioe.getMessage());
                                    }
                                    break;
                            }
                            break;
                        case 4:
                            int c4 = dcm.showMenuAndChoose(DialogCommandMaker.modifyCommands);
                            switch (c4) {
                                default:
                                    break;
                                case 1:
                                    try {
                                        dcm.modifyStudent(dcm.inputId());
                                    } catch (IOException ioe) {
                                        System.out.println(ioe.getMessage());
                                    }
                                    break;
                                case 2:
                                    try {
                                        dcm.modifyTeacher(dcm.inputId());
                                    } catch (IOException ioe) {
                                        System.out.println(ioe.getMessage());
                                    }
                                    break;
                            }
                            break;
                        case 5:
                            try {
                                dcm.deletePerson(dcm.inputId());
                            } catch (IOException ioe) {
                                System.out.println(ioe.getMessage());
                            }
                    }

                }

                if (m != 3) {
                    break;
                }

                System.out.println("Choose which element you want to call to\n0. Quit from program\n" +
                        "1. Create commands with DialogCommandMaker\n2. Reset Controller and Dispatcher");

                while (true) {
                    try {
                        c = sc.nextInt();
                        if ((c >= 0) && (c <= 2)) {
                            break;
                        }
                        System.out.println("Incorrect number! Enter a number from 0 to 2");
                    } catch (InputMismatchException ime) {
                        System.out.println("Incorrect format! Enter a number from 0 to 2 to choose action");
                        sc.nextLine();
                    }
                }

                switch (c) {
                    default:
                        quitFromControllerAndDispatcher();
                        return;
                    case 1:
                        break;
                    case 2:
                        quitFromControllerAndDispatcher();
                        initControllerAndDispatcher(commandsForPeopleService);
                }
            }
        }
    }

    static public String enterPath(int c) {
        while (true) {
            if (c == 1) {
                System.out.println("Enter path to ManagementFolder(Usually it is ./target/ManagementFolder/," +
                        " to use it press enter)");
            } else {
                System.out.println("Enter path to PersonsFolder(Usually it is ./target/Persons/, " +
                        "to use it press enter)");
            }
            Scanner sc = new Scanner(System.in);
            String outputPath = sc.nextLine();
            if (outputPath.equals("")){
                if (c == 1){
                    System.out.println("ManagementFolder is: ./target/ManagementFolder/");
                    return  "./target/ManagementFolder/";
                }else{
                    System.out.println("PersonsFolder is: ./target/ManagementFolder/");
                    return  "./target/Persons/";
                }
            }
            Path path = Paths.get(outputPath);
            if (!Files.exists(path)) {
                System.out.println("This folder doesn't exist! Create? Y/N");
                String ans = sc.nextLine();
                if ((ans.equals("Y")) || (ans.equals("y"))) {
                    if (!(new File(outputPath).mkdirs())) {
                        System.out.println("Can't create new folder");
                    } else {
                        System.out.println("The folder was created successfully");
                        return outputPath;
                    }
                } else {
                    System.out.println("Try again to enter path? Y/N");
                    ans = sc.nextLine();
                    if ((ans.equals("N")) || (ans.equals("n"))) {
                        return null;
                    }
                }
            } else {
                return outputPath;
            }
        }
    }

    static public void initControllerAndDispatcher(CommandQueue<Command> commandsForPeopleService) {
        String pathToManagementFolder = enterPath(1);
        String pathToPersonsFolder = enterPath(2);
        if ((pathToManagementFolder == null) || (pathToPersonsFolder == null)) {
            System.out.println("There is no path to the folder, " +
                    "the controller and dispatcher can't work without it");
        } else {
            try {
                mainController = new Controller(pathToManagementFolder, commandsForPeopleService);
                mainController.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            try {
                mainDispatcher = new Dispatcher(pathToPersonsFolder, commandsForPeopleService);
                mainDispatcher.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static public void quitFromControllerAndDispatcher() {
        System.out.println("Closing Controller and Dispatcher");
        if (mainController != null) {
            if (mainController.isAlive()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainController.interrupt();
            }
        }
        if (mainDispatcher != null) {
            if (mainDispatcher.isAlive()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mainDispatcher.interrupt();
            }
        }
    }

}
