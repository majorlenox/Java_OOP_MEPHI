
import Persons.Person;
import Persons.Student;

import java.util.*;

public class PeopleService {

    static Student CreateStudent () throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the student's full name");
        String fullName = sc.nextLine();
        System.out.println("Enter the student's date of birth");
        int dateOfBirth = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the student's phone number");
        String telephoneNumber = sc.nextLine();
        System.out.print("Enter(in one line!) the subjects the student is studying (from the list of subjects)\nSubjects: ");
        for (Person.Subjects sub : Person.Subjects.values()){
            System.out.print(sub.name() + " ");
        }
        System.out.println();
        String ss = sc.nextLine();
        HashSet<Person.Subjects> studySubjects = new HashSet<>();
        ss = ss.toUpperCase();
        for (Person.Subjects sub : Person.Subjects.values()){
            if (ss.contains(sub.name())){ studySubjects.add(sub);}
        }

        System.out.println("Enter the average grades for the study Subjects, format = \"ALEGBRA 4,6\"");
        HashMap<Person.Subjects, Double> grades = new HashMap<>();
        for (Person.Subjects sub : studySubjects){
            System.out.print(sub.name()+" ");
            grades.put(sub, sc.nextDouble());
        }

        return new Student(fullName, dateOfBirth, telephoneNumber, grades, studySubjects);
    }


}
