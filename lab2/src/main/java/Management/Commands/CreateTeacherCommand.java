package Management.Commands;

import Persons.Person;

import java.io.BufferedReader;
import java.io.IOException;

public class CreateTeacherCommand extends PersonCommand{

    private Person.Subjects subject;
    private String workingHours;

    public CreateTeacherCommand(BufferedReader reader) throws IOException {
        commandSpecifier = "CT";
        fullName = reader.readLine();
        yearOfBirth = Integer.parseInt(reader.readLine());
        telephoneNumber = reader.readLine();
        subject = Person.Subjects.valueOf(reader.readLine().toUpperCase());
        workingHours = reader.readLine();
    }

    public Person.Subjects getSubject() {
        return subject;
    }

    public String getWorkingHours() {
        return workingHours;
    }

}
