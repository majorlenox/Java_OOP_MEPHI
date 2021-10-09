package Management;

import Management.Commands.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Controller extends Thread {

    private final String pathToManagementFolder;
    private final CommandQueue<Command> commandsForPeopleService;

    @Override
    public void run() {
        System.out.println("Controller started");
        while (true) {
            try {
                try {
                    updateAndAddCommands();
                } catch (FileNotFoundException fNFE) {
                    System.err.println(fNFE.getMessage() + " - alien file. Controller was stopped");
                    return;
                } catch (Exception e) {
                    System.err.println(e.getMessage() + " - Controller was stopped");
                    return;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }


    public Controller(String pathToManagementFolder, CommandQueue<Command> commandsForPeopleService) {
        this.pathToManagementFolder = pathToManagementFolder;
        this.commandsForPeopleService = commandsForPeopleService;
    }

    private void updateAndAddCommands() throws Exception {

        String[] files = new File(pathToManagementFolder).list();
        if (files == null) {
            return;
        }
        for (String file : files) {
            FileReader fr = new FileReader(pathToManagementFolder + file);
            BufferedReader reader = new BufferedReader(fr);
            String commandSpecifier;
            commandSpecifier = reader.readLine();
            if (commandSpecifier == null) {
                throw new Exception("Unexpected end of file " + pathToManagementFolder + file);
            }

            switch (commandSpecifier) {
                case "CS":
                    commandsForPeopleService.offer(new CreateStudentCommand(reader));
                    break;
                case "CT":
                    commandsForPeopleService.offer(new CreateTeacherCommand(reader));
                    break;
                case "EN":
                    commandsForPeopleService.offer(new EndCommand());
                    Files.delete(Paths.get(pathToManagementFolder + file));
                    throw new Exception("end command");
                case "MS":
                    commandsForPeopleService.offer(new ModifyStudentCommand(reader));
                    break;
                case "MT":
                    commandsForPeopleService.offer(new ModifyTeacherCommand(reader));
                    break;
                case "DP":
                    commandsForPeopleService.offer(new DeletePersonCommand(reader));
                    break;
                default:
                    throw new Exception("Unknown command " + commandSpecifier + "in file:" + pathToManagementFolder + file);
            }

            Files.delete(Paths.get(pathToManagementFolder + file));
        }


    }

}

