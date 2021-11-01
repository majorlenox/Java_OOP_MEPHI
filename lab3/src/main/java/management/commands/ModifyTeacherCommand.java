package management.commands;

import persons.Person;

import java.io.BufferedReader;

public class ModifyTeacherCommand extends PersonCommand {

  private final long id;
  private Person.Subjects subject;
  private String workingHours;

  public ModifyTeacherCommand(BufferedReader reader) throws Exception {
    commandSpecifier = "MT";
    id = Integer.parseInt(reader.readLine().split(" ")[1]);
    if (id <= 0) {
      throw new Exception("Incorrect id");
    }
    String inputLine;
    while ((inputLine = reader.readLine()) != null) {
      String[] inputLineWords = inputLine.split(" ");
      switch (inputLineWords[0]) {
        case "FN":
          fullName = inputLine.substring(inputLine.indexOf(inputLineWords[1]));
          break;
        case "YB":
          yearOfBirth = Integer.parseInt(inputLineWords[1]);
          break;
        case "TN":
          telephoneNumber = inputLine.substring(inputLine.indexOf(inputLineWords[1]));
          break;
        case "ST":
          int i = 1;
          while (i <= inputLineWords.length) {
            if (!inputLineWords[i].equals("")) {
              break;
            }
            i++; // skip spaces
          }
          if (inputLineWords.length < i) {
            throw new Exception("No subject after ST");
          }
          subject = Person.Subjects.valueOf(inputLineWords[i].toUpperCase());
          break;
        case "WH":
          workingHours = inputLine.substring(inputLine.indexOf(inputLineWords[1]));
          break;
        default:
          throw new Exception("Untracked Specifier: " + inputLineWords[0]);
      }
    }
  }

  public long getId() {
    return id;
  }

  public Person.Subjects getSubject() {
    return subject;
  }

  public String getWorkingHours() {
    return workingHours;
  }
}
