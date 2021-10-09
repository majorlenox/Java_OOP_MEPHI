package Management;

import DAO.CachedPeopleDAO;
import DAO.Dao;
import DAO.PeopleDAO;
import Management.Commands.*;
import Persons.Person;
import Persons.Student;
import Persons.Teacher;

import java.util.HashMap;
import java.util.HashSet;

public class PeopleService {

    private String pathToDirectory;
    private String pathToIdsFile;
    private Dao psDao;

    public PeopleService(String pathToDirectory, String pathToIdsFile){
        psDao = new PeopleDAO(pathToIdsFile, pathToDirectory);  // optional, use PeopleDAO or CachedPeopleDAO
        this.pathToDirectory = pathToDirectory;
        this.pathToIdsFile = pathToIdsFile;
    }

    public void processTheCommand(Command cmd) throws Exception {

        switch (cmd.getCommandSpecifier()){
            case "EN":
                throw new Exception("end command");
            case "CS":
                CreateStudentCommand cSC = (CreateStudentCommand) cmd;
                Student student = new Student(cSC.getFullName(), cSC.getYearOfBirth(), cSC.getTelephoneNumber(),
                        cSC.getGrades());
                psDao.writePerson(student);
                break;
            case "CT":
                CreateTeacherCommand cTC = (CreateTeacherCommand) cmd;
                Teacher teacher = new Teacher(cTC.getFullName(), cTC.getYearOfBirth(), cTC.getTelephoneNumber(),
                        cTC.getSubject(), cTC.getWorkingHours());
                psDao.writePerson(teacher);
                break;
            case "MS":
                ModifyStudentCommand mSC = (ModifyStudentCommand) cmd;
                Person tempPerson = psDao.readPerson(mSC.getId());
                if (tempPerson.getClass() != Student.class){
                    throw new Exception("Attempt to modify the student using the no-student modify command");
                }
                Student student1 = (Student) tempPerson;
                if (mSC.getFullName()!=null) {
                    student1.setFullName(mSC.getFullName());
                }
                if (mSC.getYearOfBirth()!=0) {
                    student1.setYearOfBirth(mSC.getYearOfBirth());
                }
                if (mSC.getTelephoneNumber()!=null) {
                    student1.setTelephoneNumber(mSC.getTelephoneNumber());
                }
                if (mSC.getrSubjects()!=null){
                    removeStudentsSubjects(student1, mSC.getrSubjects());
                }
                if (mSC.getGrades()!=null){
                    addStudentsGrades(student1, mSC.getGrades());
                }
                psDao.writePerson(student1);
                break;
            case "MT":
                ModifyTeacherCommand mTC = (ModifyTeacherCommand) cmd;
                Person tempPerson1 = psDao.readPerson(mTC.getId());
                if (tempPerson1.getClass() != Teacher.class){
                    throw new Exception("Attempt to modify the teacher using the no-teacher modify command");
                }
                Teacher teacher1 = (Teacher) tempPerson1;
                if (mTC.getFullName()!=null) {
                    teacher1.setFullName(mTC.getFullName());
                }
                if (mTC.getYearOfBirth()!=0) {
                    teacher1.setYearOfBirth(mTC.getYearOfBirth());
                }
                if (mTC.getTelephoneNumber()!=null) {
                    teacher1.setTelephoneNumber(mTC.getTelephoneNumber());
                }
                if (mTC.getSubject()!=null) {
                    teacher1.setSubject(mTC.getSubject());
                }
                if (mTC.getSubject()!=null) {
                    teacher1.setWorkingHours(mTC.getWorkingHours());
                }
                psDao.writePerson(teacher1);
                break;
            case "DP":
                DeletePersonCommand dPC = (DeletePersonCommand) cmd;
                psDao.deletePerson(dPC.getId());
                break;

            default:
                throw new Exception("Untracked Specifier: " + cmd.getCommandSpecifier());
        }

    }

    public void ShowPeopleId(){
        if (psDao.getClass() == CachedPeopleDAO.class){
            for (Integer id : ((CachedPeopleDAO)psDao).getPeopleById().keySet()){
             System.out.println("ID " + id + ": " + ((CachedPeopleDAO)psDao).getPeopleById().get(id).getFullName());
            }
        }else{
            System.out.println("Can't show people by id");
        }
    }

    private void removeStudentsSubjects(Student student1, HashSet<Person.Subjects> rSubjects){
        for (Person.Subjects sub : student1.getGrades().keySet()){
            if (rSubjects.contains(sub)){
                student1.getGrades().remove(sub);
            }
        }
    }

    private void addStudentsGrades(Student student1, HashMap<Person.Subjects, Double> grades){
        for (Person.Subjects sub : grades.keySet()) {
            if (student1.getGrades().containsKey(sub)) {
                student1.getGrades().remove(sub);
            }
            student1.getGrades().put(sub, grades.get(sub));
        }

    }

}
