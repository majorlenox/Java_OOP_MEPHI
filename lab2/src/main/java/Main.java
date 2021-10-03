import Persons.Student;
import Persons.Teacher;

public class Main {
    public static void main(String[] args) {

        try {
            Student st1 = PeopleService.createStudent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Teacher tr1 = PeopleService.createTeacher();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
