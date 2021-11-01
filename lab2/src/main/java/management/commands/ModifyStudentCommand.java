package management.commands;

import persons.Person;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;

public class ModifyStudentCommand extends PersonCommand {

  private final long id;
  private HashSet<Person.Subjects> rSubjects;
  private HashMap<Person.Subjects, Double> grades;

  public ModifyStudentCommand(BufferedReader reader) throws Exception {
    commandSpecifier = "MS";
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
        case "RS":
          rSubjects = new HashSet<>();
          for (int i = 1; i < inputLineWords.length; i++) {
            if (!inputLineWords[i].equals("")) {
              rSubjects.add(Person.Subjects.valueOf(inputLineWords[i].toUpperCase()));
            }
          }
          if (rSubjects.isEmpty()) {
            throw new Exception("No remove Subjects after RS");
          }
          break;
        case "GS":
          grades = new HashMap<>();
          while ((inputLine = reader.readLine()) != null) {
            inputLineWords = inputLine.split(" ");
            try {
              grades.put(
                  Person.Subjects.valueOf(inputLineWords[0].toUpperCase()),
                  Double.parseDouble(inputLineWords[1]));
            } catch (IllegalArgumentException nfe) { // grade not a double or wrong subject
              throw new Exception("Incorrect format of grades");
            }
          }
          if (grades.isEmpty()) {
            throw new Exception("No grades after GS");
          }
          break;
        default:
          throw new Exception("Untracked Specifier: " + inputLineWords[0]);
      }
    }
  }

  public long getId() {
    return id;
  }

  public HashSet<Person.Subjects> getrSubjects() {
    return rSubjects;
  }

  public HashMap<Person.Subjects, Double> getGrades() {
    return grades;
  }

  private String deleteSpaces(String iStr) {
    StringBuffer oStr = new StringBuffer();
    char[] iStrChars = iStr.toCharArray();
    for (int i = 0; i < iStr.length(); i++) {
      if (iStrChars[i] != ' ') {
        oStr.append(iStrChars[i]);
      }
    }
    return oStr.toString();
  }
}
