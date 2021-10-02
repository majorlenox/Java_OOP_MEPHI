package Persons;

public class Teacher extends Person {

    private Subjects subject;
    private double workingHoursFrom;
    private double workingHoursTo;

    public Teacher(String fullName, int dateOfBirth, String telephoneNumber, Subjects subject, double workingHoursFrom, double workingHoursTo) throws Exception {
        super(fullName, dateOfBirth, telephoneNumber);
        this.subject = subject;
        this.workingHoursFrom = workingHoursFrom;
        this.workingHoursTo = workingHoursTo;

    }

    public Subjects getSubject() {
        return subject;
    }

    public double getWorkingHoursFrom() {
        return workingHoursFrom;
    }

    public double getWorkingHoursTo() {
        return workingHoursTo;
    }
}
