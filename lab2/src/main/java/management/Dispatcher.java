package management;

import management.commands.*;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Dispatcher extends Thread {

    private final String pathToPersonsFolder;
    private final CommandQueue<Command> commandsForPeopleService;

    @Override
    public void run() {
        System.out.println("Dispatcher started");
        while (true) {
            try {
                updateAndPullOutCommands();
            } catch (Exception e) {

                if (e.getMessage().equals("end command")) {
                    System.out.println("end command - Controller stopped");
                    return;
                }
                int i = 1;
                while (true) {
                    try {
                        if (i == 100) {
                            break;
                        }
                        File errFile = new File("./target/Errors/" + e.getMessage().charAt(0) +
                                e.getMessage().charAt(1) + "_" + i + ".txt");
                        FileWriter fw = new FileWriter(errFile);
                        fw.write(e.getMessage());
                        fw.close();
                        break;
                    } catch (Exception ex) {
                        i++;
                    }
                }
                System.err.println(e.getMessage());




               if (e.getMessage().equals("File with this ID doesn't exists!") ||
                       (e.getMessage().equals("Person doesn't exist in IDs.txt!"))||
                       (e.getMessage().equals("Attempt to modify the teacher using the no-teacher modify command"))||
                       (e.getMessage().equals("Attempt to modify the student using the no-student modify command"))
               ) { System.err.println(e.getMessage() + " - Dispatcher still working"); continue; }
               System.err.println(e.getMessage() + " - Dispatcher was stopped");
               System.err.println(commandsForPeopleService.size() + " commands lost");
               return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public Dispatcher(String pathToPersonsFolder, CommandQueue<Command> commandsForPeopleService) {
        this.pathToPersonsFolder = pathToPersonsFolder;
        this.commandsForPeopleService = commandsForPeopleService;
    }

    private void updateAndPullOutCommands() throws Exception {
        PeopleService plService = new PeopleService(pathToPersonsFolder, pathToPersonsFolder); // ids in Persons Folder
        while (commandsForPeopleService.size()!=0){
            Command cmd = commandsForPeopleService.poll();
            plService.processTheCommand(cmd);
        }
    }


}

