package management;

import management.commands.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

public class Controller extends Thread {

  private final String pathToManagementFolder;
  private final CommandQueue<Command> commandsForPeopleService;

  public Controller(String pathToManagementFolder, CommandQueue<Command> commandsForPeopleService) {
    this.pathToManagementFolder = pathToManagementFolder;
    this.commandsForPeopleService = commandsForPeopleService;
  }

  @Override
  public void run() {
    System.out.println("Controller started");
    while (true) {
      try {
        try {
          updateAndAddCommands();
        } catch (Exception e) {
          if ("end command".equals(e.getMessage())) {
            System.out.println("end command - Controller stopped");
            return;
          }
          try {
            if (!Files.exists(Paths.get("./target/Errors/OriginalFiles/"))) {
              if (!(new File("./target/Errors/OriginalFiles/").mkdirs())) {
                System.out.println("Can't create Error folder in ./target/Errors/OriginalFiles/");
              }
            }
            String pathToFile = e.getMessage().split("\n")[0];
            Path source = Paths.get(pathToFile);
            String fileName = pathToFile.split("/")[pathToFile.split("/").length - 1];
            int i = 0;
            while (true) {
              Path dist;
              try {
                if (i == 0) {
                  dist = Paths.get("./target/Errors/OriginalFiles/" + fileName);
                } else {
                  dist =
                      Paths.get(
                          "./target/Errors/OriginalFiles/"
                              + fileName.split("\\.")[0]
                              + "("
                              + i
                              + ")"
                              + "."
                              + fileName.split("\\.")[1]);
                }
                Files.copy(source, dist);
                break;
              } catch (Exception e1) {
                i++;
              }
            }
            Files.delete(source);
            File errFile;
            if (i == 0) {
              errFile = new File("./target/Errors/" + fileName.split("\\.")[0] + "_Err.txt");
            } else {
              errFile =
                  new File(
                      "./target/Errors/" + fileName.split("\\.")[0] + "(" + i + ")" + "_Err.txt");
            }
            FileWriter fw = new FileWriter(errFile);
            Calendar cl = Calendar.getInstance();
            fw.write(e.getMessage().substring(pathToFile.length() + 1) + "\nTime: " + cl.getTime());
            fw.close();
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
          System.err.println("Controller: New error in ./target/Errors");
          continue;
        }
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }
    }
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
        throw new Exception(pathToManagementFolder + file + "\nUnexpected end of file: " + file);
      }

      switch (commandSpecifier) {
        case "CS":
          try {
            commandsForPeopleService.offer(new CreateStudentCommand(reader));
          } catch (Exception e) {
            throw new Exception(pathToManagementFolder + file + "\n" + e.getMessage());
          }
          break;
        case "CT":
          try {
            commandsForPeopleService.offer(new CreateTeacherCommand(reader));
          } catch (Exception e) {
            throw new Exception(pathToManagementFolder + file + "\n" + e.getMessage());
          }
          break;
        case "EN":
          commandsForPeopleService.offer(new EndCommand());
          Files.delete(Paths.get(pathToManagementFolder + file));
          throw new Exception("end command");
        case "MS":
          try {
            commandsForPeopleService.offer(new ModifyStudentCommand(reader));
          } catch (Exception e) {
            throw new Exception(pathToManagementFolder + file + "\n" + e.getMessage());
          }
          break;
        case "MT":
          try {
            commandsForPeopleService.offer(new ModifyTeacherCommand(reader));
          } catch (Exception e) {
            throw new Exception(pathToManagementFolder + file + "\n" + e.getMessage());
          }
          break;
        case "DP":
          try {
            commandsForPeopleService.offer(new DeletePersonCommand(reader));
          } catch (Exception e) {
            throw new Exception(pathToManagementFolder + file + "\n" + e.getMessage());
          }
          break;
        default:
          throw new Exception(
              pathToManagementFolder + file + "\n" + "Unknown command " + commandSpecifier);
      }

      Files.delete(Paths.get(pathToManagementFolder + file));
    }
  }
}
