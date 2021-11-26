package org.majorlenox.lab3.structures;

import org.majorlenox.lab3.persons.Person;

import java.util.ArrayList;
import java.util.HashMap;

public class ModifiedData {

    public final long id;
    public final String fullName;
    public final int yearOfBirth;
    public final String telephoneNumber;
    public final Person.Subjects subject;
    public final String workingHours;
    public final HashMap<Person.Subjects, Double> newGrades;
    public final ArrayList<Person.Subjects> removeGrades;

    public ModifiedData(long id, String fullName, int yearOfBirth, String telephoneNumber, Person.Subjects subject,
                        String workingHours,
                        HashMap<Person.Subjects, Double> newGrades,
                        ArrayList<Person.Subjects> removeGrades) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.subject = subject;
        this.workingHours = workingHours;
        this.newGrades = newGrades;
        this.removeGrades = removeGrades;
    }

}
