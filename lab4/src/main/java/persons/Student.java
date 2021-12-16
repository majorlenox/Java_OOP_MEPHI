package persons;

import java.util.HashMap;

public class Student extends Person {

    private HashMap<Subjects, Double> grades;

    public Student() {
    }

    public Student(String fullName, int yearOfBirth, String telephoneNumber, HashMap<Subjects, Double> grades, long id) {
        super(fullName, yearOfBirth, telephoneNumber, id);
        this.grades = grades;
    }

    public HashMap<Subjects, Double> getGrades() {
        return grades;
    }
}