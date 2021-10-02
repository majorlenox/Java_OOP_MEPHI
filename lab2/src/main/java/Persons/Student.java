package Persons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Student extends Person {

    private HashSet<Subjects> studySubjects;
    private HashMap<Subjects, Double> grades;

    public Student(String fullName, int dateOfBirth, String telephoneNumber, HashMap<Subjects, Double> grades, HashSet<Subjects> studySubjects) throws Exception {
        super(fullName, dateOfBirth, telephoneNumber);
        this.grades = grades;
        this.studySubjects = studySubjects;
    }

    public HashSet<Subjects> getStudySubjects() {
        return studySubjects;
    }

    public HashMap<Subjects, Double> getGrades() {
        return grades;
    }
}
