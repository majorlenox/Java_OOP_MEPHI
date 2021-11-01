package management;

import management.commands.Command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

public class Dispatcher extends Thread {

  private final String pathToPersonsFolder;
  private final CommandQueue<Command> commandsForPeopleService;

  public Dispatcher(String pathToPersonsFolder, CommandQueue<Command> commandsForPeopleService) {
    this.pathToPersonsFolder = pathToPersonsFolder;
    this.commandsForPeopleService = commandsForPeopleService;
  }

  @Override
  public void run() {
    System.out.println("Dispatcher started");
    while (true) {
      try {
        updateAndPullOutCommands();
      } catch (Exception e) {
        if ("end command".equals(e.getMessage())) {
          System.out.println("end command - Dispatcher stopped");
          return;
        }
        if (!Files.exists(Paths.get("./target/Errors/OriginalFiles/"))) {
          if (!(new File("./target/Errors/OriginalFiles/").mkdirs())) {
            System.out.println("Can't create Error folder in ./target/Errors/OriginalFiles/");
          }
        }
        // handleException()
        //   moveToErrorFolder();
        //   generateErrorDescriptionFile();
        //   deleteOriginalFile();
        if (e.getMessage().split("\n")[0].equals("Unknown type of Person in file ")) {
          try {
            String pathToFile = e.getMessage().split("\n")[1];
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
            Calendar cl = Calendar.getInstance();
            if (i == 0) {
              errFile = new File("./target/Errors/" + fileName.split("\\.")[0] + "_Err.txt");
              FileWriter fw = new FileWriter(errFile);
              fw.write(e.getMessage().split("\n")[0] + fileName + "\nTime: " + cl.getTime());
              fw.close();
            } else {
              errFile =
                  new File(
                      "./target/Errors/" + fileName.split("\\.")[0] + "(" + i + ")" + "_Err.txt");
              FileWriter fw = new FileWriter(errFile);
              fw.write(
                  e.getMessage().split("\n")[0]
                      + fileName.split("\\.")[0]
                      + "("
                      + i
                      + ")"
                      + "."
                      + fileName.split("\\.")[1]
                      + "\nTime: "
                      + cl.getTime());
              fw.close();
            }
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
          System.err.println("Dispatcher: New error in ./target/Errors");
          continue;
        }

        int i = 1;
        while (Files.exists(
            Paths.get(
                "./target/Errors/"
                    + e.getMessage().charAt(0)
                    + e.getMessage().charAt(1)
                    + "_"
                    + i
                    + ".txt"))) {
          i++;
        }
        File errFile =
            new File(
                "./target/Errors/"
                    + e.getMessage().charAt(0)
                    + e.getMessage().charAt(1)
                    + "_"
                    + i
                    + ".txt");
        try {
          FileWriter fw = new FileWriter(errFile);
          Calendar cl = Calendar.getInstance();
          fw.write(e.getMessage() + "\nTime: " + cl.getTime());
          fw.close();
        } catch (Exception efw) {
          efw.printStackTrace();
        }
        System.err.println("Dispatcher: New error in ./target/Errors");
        continue;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }
  }

  private void updateAndPullOutCommands() throws Exception {
    PeopleService plService =
        new PeopleService(pathToPersonsFolder, pathToPersonsFolder); // ids in Persons Folder
    while (commandsForPeopleService.size() != 0) {
      Command cmd = commandsForPeopleService.poll();
      plService.processTheCommand(cmd);
    }
  }
}
