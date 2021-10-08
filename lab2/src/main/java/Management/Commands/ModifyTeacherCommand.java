package Management.Commands;

import Persons.Person;

import java.io.BufferedReader;

public class ModifyTeacherCommand extends PersonCommand {

    private int id;
    private Person.Subjects subject;
    private String workingHours;

    public ModifyTeacherCommand(BufferedReader reader) throws Exception {
        commandSpecifier = "MT";
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
                case "ST":
                    subject = Person.Subjects.valueOf(inputLineWords[1].toUpperCase());
                    break;
                case "WH":
                    workingHours = inputLineWords[1];
                    break;
                default:
                    throw new Exception("Untracked Specifier: " + inputLineWords[0]);
            }
        }
    }

    public int getId() {
        return id;
    }

    public Person.Subjects getSubject() {
        return subject;
    }

    public String getWorkingHours() {
        return workingHours;
    }

}