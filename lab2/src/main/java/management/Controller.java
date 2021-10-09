package management;

import management.commands.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
              Path source = Paths.get(e.getMessage().split("\n")[0]);
              Path dist = Paths.get("./target/Errors/" +
                      e.getMessage().charAt(0) + e.getMessage().charAt(1) + "_" + i + ".txt");
              Files.copy(source, dist);
              Files.delete(source);
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
    FileReader fr;
    for (String file : files) {
      try {
        fr = new FileReader(pathToManagementFolder + file);
      } catch (Exception e) {
        continue;
      }
      BufferedReader reader = new BufferedReader(fr);
      String commandSpecifier;
      commandSpecifier = reader.readLine();
      if (commandSpecifier == null) {
        throw new Exception(pathToManagementFolder + file + "\n" + "Unexpected end of file");
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
          throw new Exception(
              pathToManagementFolder
                  + file
                  + "\n"
                  + "Unknown command "
                  + commandSpecifier
                  + "in file");
      }

      Files.delete(Paths.get(pathToManagementFolder + file));
    }
  }
}
