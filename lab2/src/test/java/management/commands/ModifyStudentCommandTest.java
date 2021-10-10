package management.commands;

import org.junit.jupiter.api.Test;
import persons.Person;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ModifyStudentCommandTest {

    @Test
    void test() throws Exception {
        String path = "./src/test/Commands/";
        String specifier = "MS\n";
        String id = "ID 213\n";
        String fullName = "FN example Name\n";
        String telephoneNumber = "TN 79049432\n";
        String yearOfBirth = "YB 1960\n";
        String rSubjects = "RS algebra biology physics\n";
        String workingHours = "GS\nalgebra 1.2";

        File in1 = new File(path + "in1.txt");
        FileWriter out = new FileWriter(in1);

        HashSet<Person.Subjects> rSubs = new HashSet<>();
        rSubs.add(Person.Subjects.PHYSICS);
        rSubs.add(Person.Subjects.ALGEBRA);
        rSubs.add(Person.Subjects.BIOLOGY);

        HashMap<Person.Subjects, Double> grades = new HashMap<>();
        grades.put(Person.Subjects.ALGEBRA, 1.2);

        out.write(specifier + id + fullName + telephoneNumber + yearOfBirth + rSubjects + workingHours);
        out.close();
        BufferedReader reader = new BufferedReader(new FileReader(path + "in1.txt"));
        reader.readLine();  // Command specifier
        ModifyStudentCommand mSC = new ModifyStudentCommand(reader);
        assertAll("Should return address of Oracle's headquarter",
                () -> assertEquals("MS", mSC.getCommandSpecifier()),
                () -> assertEquals(213, mSC.getId()),
                () -> assertEquals("example Name", mSC.getFullName()),
                () -> assertEquals(1960, mSC.getYearOfBirth()),
                () -> assertTrue(assertRSubs(rSubs, mSC.getrSubjects())),
                () -> assertTrue(assertGrades(grades, mSC.getGrades()))
        );

    }

    private boolean assertRSubs(HashSet<Person.Subjects> rSubs1, HashSet<Person.Subjects> rSubs2){
        if ((rSubs1 == null)&&(rSubs2 == null)) { return true; }
        if ((rSubs1 == null)||(rSubs2 == null)) { return false; }
        for (Person.Subjects sub : rSubs1){
         if (!rSubs2.contains(sub)){
             return false;
         }
        }
        return rSubs1.size() == rSubs2.size();
    }

    private boolean assertGrades(HashMap<Person.Subjects, Double> grades1, HashMap<Person.Subjects, Double> grades2){
        if ((grades1 == null)&&(grades2 == null)) { return true; }
        if ((grades1 == null)||(grades2 == null)) { return false; }
        for (Person.Subjects sub : grades1.keySet()){
            if (!Objects.equals(grades1.get(sub), grades2.get(sub))){
                return false;
            }
        }
        return grades1.size() == grades2.size();
    }

}