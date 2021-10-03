
import DAO.CachedPeopleDAO;
import DAO.Dao;
import DAO.PeopleDAO;
import Persons.Person;
import Persons.Student;
import Persons.Teacher;

import java.util.*;

public class PeopleService {

    private static Dao psDao;

    static Student createStudent () throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the student's full name");
        String fullName = sc.nextLine();
        System.out.println("Enter the student's year of birth");
        int yearOfBirth = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the student's phone number");
        String telephoneNumber = sc.nextLine();

        System.out.println("Enter the average grades for the study Subjects, format = \"ALGEBRA 4,6\"");
        HashMap<Person.Subjects, Double> grades = new HashMap<>();
        // Change with no studySubjects
        for (Person.Subjects sub : studySubjects){
            System.out.print(sub.name()+" ");
            double grade;
            while (true) {
                try {
                    grade = sc.nextDouble();
                    break;
                }catch (InputMismatchException ime){
                    System.out.println("Incorrect format! Try again\n for example: \"1,25\"");
                    sc.nextLine();
                }
            }
            grades.put(sub, grade);
        }
        sc.nextLine();
        Student student = new Student(fullName, yearOfBirth, telephoneNumber, grades);

        checkAndSetPsDao();

        psDao.writePerson(student);

        return student;
    }

    static Teacher createTeacher () throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the teacher's full name");
        String fullName = sc.nextLine();
        System.out.println("Enter the teacher's year of birth");
        int yearOfBirth = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the teacher's phone number");
        String telephoneNumber = sc.nextLine();
        System.out.print("Enter which subject the teacher teaches\nSubjects: ");
        for (Person.Subjects sub : Person.Subjects.values()){
            System.out.print(sub.name() + " ");
        }
        System.out.println();
        Person.Subjects subject = enterTeachersSubject();
        System.out.println("Enter the teacher's working hours, example 8:10 - 17:05");
        String workingHours = sc.nextLine();
        Teacher teacher = new Teacher(fullName, yearOfBirth, telephoneNumber, subject, workingHours);
        checkAndSetPsDao();
        psDao.writePerson(teacher);
        return teacher;
    }

    static Person getAndChangePersonFromId(int Id)  {
        checkAndSetPsDao();
        Person person;
        try {
            person = psDao.readPerson(Id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        int c;
        Scanner sc = new Scanner(System.in);
        while ((c = changeMenu(person)) != 0){

            switch (c){
                case 1:
                    System.out.println("Enter new full name:");
                    person.setFullName(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Enter new year of birth:");
                    person.setYearOfBirth(sc.nextInt());
                    break;
                case 3:
                    System.out.println("Enter new telephone number:");
                    person.setTelephoneNumber(sc.nextLine());
                    break;
                case 4:
                    if (person.getClass() == Student.class){
                        updateListOfSubjects((Student)person);
                    }else{
                        if (person.getClass() == Teacher.class){
                         Person.Subjects sub = enterTeachersSubject();
                         ((Teacher) person).setSubject(sub);
                        }
                    }
                case 5:
                    if (person.getClass() == Student.class){
                        updateGrades((Student)person);
                    }else{
                        if (person.getClass() == Teacher.class){
                            Person.Subjects sub = enterTeachersSubject();
                            ((Teacher) person).setSubject(sub);
                        }
                    }
            }

        }

        return person;
    }

    private static void checkAndSetPsDao(){
        if (psDao == null){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the path to the file ids.txt (if it is not there, then it will be created)");
            String pathToIdsfile = sc.nextLine();
            System.out.println("Enter the path to the person's file storage location");
            String pathToDirectory = sc.nextLine();
            System.out.println("Choose an implementation of peopleDAO\n1 - PeopleDAO\n2 - CachedPeopleDAO");
            if (sc.nextInt() == 2){
                psDao = new CachedPeopleDAO(pathToIdsfile, pathToDirectory);
            } else {
                psDao = new PeopleDAO(pathToIdsfile, pathToDirectory);
            }
        }
    }

    private static int changeMenu(Person person){
        showPerson(person);
        if (person.getClass() == Student.class){
            System.out.println("Select what you want to change, or save changes");
            System.out.println("0 - Save changes and quit\n1 - Full name\n2 - Year of birth\n3 - Telephone number\n" +
                    "4 - List of subjects\n" + "5 - Grades");
        }else{
            System.out.println("Select what you want to change, or save changes");
            System.out.println("0 - Save changes and quit\n1 - Full name\n2 - Year of birth\n3 - Telephone number\n" +
                    "4 - Subject\n5 - Working from ");
        }
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void showPerson(Person person) {
        if (person.getClass() == Student.class) {
            System.out.print("Full name: " + person.getFullName() + "\nYear of birth:" + person.getYearOfBirth() +
                    "\nTelephone number: " + person.getTelephoneNumber() + "\nID: " + person.getID() + "\nGrades :");
            int i = 0;  //line break
            for (Person.Subjects sub : ((Student) person).getStudySubjects()) {
                System.out.print(sub + " " + ((Student) person).getGrades().get(sub));
                if (i % 3 == 0) {
                    System.out.println();
                }
                i++;
            }
            System.out.println();
        } else {
            if (person.getClass() == Teacher.class) {
                System.out.println("Full name: " + person.getFullName() + "\nYear of birth:" + person.getYearOfBirth() +
                        "\nTelephone number: " + person.getTelephoneNumber() + "\nID: " + person.getID() +
                        "\nSubject: " + ((Teacher) person).getSubject() + "\nWorking hours: "
                        + ((Teacher) person).getWorkingHours());
            }
        }
    }

    private static void updateListOfSubjects(Student student){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter(in one line!) the subjects the student is studying (from the list" +
                " of subjects)\nSubjects: ");
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
        student.setStudySubjects(studySubjects);

        // delete and add new positions in grades
        boolean f = false;
        for (Person.Subjects sub : Person.Subjects.values()){
            if ((student.getGrades().get(sub) == null)&&(studySubjects.contains(sub))){
                student.getGrades().put(sub, 0.0);
                f = true;
            }
            if ((student.getGrades().get(sub) != null)&&(!studySubjects.contains(sub))){
                student.getGrades().remove(sub);
            }
        }
        if (f) {System.out.println("The student has ungraded subjects! Add grades with option \"5 - Grades\" in menu");}
    }

    private static Person.Subjects enterTeachersSubject(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter which subject the teacher teaches\nSubjects: ");
        for (Person.Subjects sub : Person.Subjects.values()){
            System.out.print(sub.name() + " ");
        }
        System.out.println();
        String subject;
        while (true) {
            subject = sc.nextLine();
            try {
                Person.Subjects.valueOf(subject.toUpperCase());
                break;
            } catch (IllegalArgumentException iae) {
                System.out.println("There was an error in entering the subject, check if there is such an subject " +
                        "and enter it again");
            }
        }
        return Person.Subjects.valueOf(subject.toUpperCase());
    }

    private static void updateGrades(Student student){

        Scanner sc = new Scanner(System.in);

        System.out.println("Select the subject for which you want to change the grade, or type 0 - quit");
        int i = 1;
        System.out.println("0 - quit");
        for (Person.Subjects sub : student.getStudySubjects()){
            System.out.println(i + " - " + sub.name() + " " + student.getGrades().get(sub));
            i++;
        }
        int c;
        while ((c = sc.nextInt()) != 0){
            System.out.print( student.getStudySubjects() + " ");
            student.getStudySubjects();
            sc.nextLine();
            int i = 1;
            System.out.println("0 - quit");
            for (Person.Subjects sub : student.getStudySubjects()){
                System.out.println(i + " - " + sub.name() + " " + student.getGrades().get(sub));
                i++;
            }
        }


    }

}
