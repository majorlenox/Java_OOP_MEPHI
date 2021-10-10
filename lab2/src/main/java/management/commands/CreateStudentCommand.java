package management.commands;

import persons.Person;

import java.io.BufferedReader;
import java.util.HashMap;

public class CreateStudentCommand extends PersonCommand {

    private final HashMap<Person.Subjects, Double> grades;

    public CreateStudentCommand(BufferedReader reader) throws Exception {
        commandSpecifier = "CS";
        fullName = reader.readLine();
        yearOfBirth = Integer.parseInt(reader.readLine());
        telephoneNumber = reader.readLine();
        String inputLine;
        String[] subjectAndGrade;
        grades = new HashMap<>();
        while ((inputLine = reader.readLine()) != null) {
            subjectAndGrade = inputLine.split(" ");
            grades.put(Person.Subjects.valueOf(subjectAndGrade[0].toUpperCase()), Double.parseDouble(subjectAndGrade[1]));
        }
    }

    public HashMap<Person.Subjects, Double> getGrades() {
        return grades;
    }

}
