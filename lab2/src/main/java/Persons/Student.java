package Persons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Student extends Person {

    private Set<Subjects> studySubjects;
    private HashMap<Subjects, Double> grades;

    public Student(String fullName, int dateOfBirth, String telephoneNumber, HashMap<Subjects, Double> grades, Set<Subjects> studySubjects) throws Exception {
        super(fullName, dateOfBirth, telephoneNumber);
        this.grades = grades;
        this.studySubjects = studySubjects;
    }

    public Set<Subjects> getStudySubjects() {
        return studySubjects;
    }

    public HashMap<Subjects, Double> getGrades() {
        return grades;
    }
}
