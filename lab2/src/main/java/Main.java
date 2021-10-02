import Persons.*;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        HashSet<Person.Subjects> studySubjects = new HashSet<Person.Subjects>();
        HashMap<Person.Subjects, Double> grades = new HashMap<Person.Subjects, Double>();
        studySubjects.add(Person.Subjects.MATHEMATICS);
        studySubjects.add(Person.Subjects.GEOGRAPHY);
        studySubjects.add(Person.Subjects.PHYSICS);
        grades.put(Person.Subjects.MATHEMATICS, 4.76);
        grades.put(Person.Subjects.GEOGRAPHY, 4.1);
        grades.put(Person.Subjects.PHYSICS, 4.88);

        try {
            Student st1 = new Student("Vova Gurianov", 1998, "79093262394", grades, studySubjects);
            st1.toJson("target/");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
