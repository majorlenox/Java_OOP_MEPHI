package org.majorlenox.lab3.persons;

public abstract class Person {

    private String fullName;
    private int yearOfBirth;
    private String telephoneNumber;
    private long id = 0;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth, String telephoneNumber, long id) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public long getID() {
        return id;
    }

    public enum Subjects {
        ALGEBRA,
        BIOLOGY,
        PAINTING,
        CHEMISTRY,
        GEOGRAPHY,
        GEOMETRY,
        HISTORY,
        LITERATURE,
        MATHEMATICS,
        MUSIC,
        PHYSICAL_EDUCATION,
        PHYSICS,
        TECHNOLOGY
    }
}
