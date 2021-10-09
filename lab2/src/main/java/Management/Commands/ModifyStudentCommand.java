package Management.Commands;

import Persons.Person;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;

public class ModifyStudentCommand extends PersonCommand {

    private final int id;
    private HashSet<Person.Subjects> rSubjects;
    private HashMap<Person.Subjects, Double> grades;

    public ModifyStudentCommand(BufferedReader reader) throws Exception {
        commandSpecifier = "MS";
        id = Integer.parseInt(reader.readLine().split(" ")[1]);
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            String[] inputLineWords = inputLine.split(" ");
            switch (inputLineWords[0]) {
                case "FN":
                    fullName = inputLineWords[1];
                    break;
                case "YB":
                    yearOfBirth = Integer.parseInt(inputLineWords[1]);
                    break;
                case "TN":
                    telephoneNumber = inputLineWords[1];
                    break;
                case "RS":
                    rSubjects = new HashSet<>();
                    for (int i = 1; i < inputLineWords.length; i++) {
                        rSubjects.add(Person.Subjects.valueOf(inputLineWords[i].toUpperCase()));
                    }
                    break;
                case "GS":
                    grades = new HashMap<>();
                    while ((inputLine = reader.readLine()) != null) {
                        inputLineWords = inputLine.split(" ");
                        try {
                            grades.put(Person.Subjects.valueOf(inputLineWords[0].toUpperCase()), Double.parseDouble(inputLineWords[1]));
                        } catch (IllegalArgumentException nfe) { // grade not a double or wrong subject
                        }
                    }
                    break;
                default:
                    throw new Exception("Untracked Specifier: " + inputLineWords[0]);
            }
        }
    }

    public int getId() {
        return id;
    }

    public HashSet<Person.Subjects> getrSubjects() {
        return rSubjects;
    }

    public HashMap<Person.Subjects, Double> getGrades() {
        return grades;
    }

}
