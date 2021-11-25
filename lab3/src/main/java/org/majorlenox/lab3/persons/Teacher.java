package org.majorlenox.lab3.persons;

public class Teacher extends Person {

    private Subjects subject;
    private String workingHours;

    public Teacher() {
    }

    public Teacher(String fullName, int yearOfBirth, String telephoneNumber, Subjects subject, String workingHours, long id) {
        super(fullName, yearOfBirth, telephoneNumber, id);
        this.subject = subject;
        this.workingHours = workingHours;
    }

    public Subjects getSubject() {
        return subject;
    }

    public String getWorkingHours() {
        return workingHours;
    }

}
