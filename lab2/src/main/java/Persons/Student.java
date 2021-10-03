package Persons;

import java.util.HashMap;

public class Student extends Person {

    private HashMap<Subjects, Double> grades;

    public Student(String fullName, int yearOfBirth, String telephoneNumber, HashMap<Subjects, Double> grades) throws Exception {
        super(fullName, yearOfBirth, telephoneNumber);
        this.grades = grades;
    }

    public HashMap<Subjects, Double> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<Subjects, Double> grades) {
        this.grades = grades;
    }

}
