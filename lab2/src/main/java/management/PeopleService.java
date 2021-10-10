package management;

import dao.CachedPeopleDAO;
import dao.Dao;
import dao.PeopleDAO;
import management.commands.*;
import persons.Person;
import persons.Student;
import persons.Teacher;

import java.util.HashMap;
import java.util.HashSet;

public class PeopleService {

    private final Dao psDao;

    public PeopleService(String pathToDirectory, String pathToIdsFile){
        psDao = new PeopleDAO(pathToIdsFile, pathToDirectory);  // optional, use PeopleDAO or CachedPeopleDAO
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
                Person tempPerson;
                try{
                    tempPerson = psDao.readPerson(mSC.getId());
                }catch (Exception e){
                    if (e.getMessage().contains("ID")){
                        throw new Exception(e.getMessage() + "\nCommand:\n" + toStringFromCommand(cmd));
                    }
                    throw e;
                }
                if (tempPerson.getClass() != Student.class){
                    throw new Exception("Attempt to modify the teacher using the no-teacher modify command\n"
                            + "\nTeacher:\n" + toStringPerson(tempPerson) +"\nCommand:\n" + toStringFromCommand(cmd));
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
                Person tempPerson1;
                try{
                    tempPerson1 = psDao.readPerson(mTC.getId());
                }catch (Exception e){
                    if (e.getMessage().contains("ID")){
                        throw new Exception(e.getMessage() + "\nCommand:\n" + toStringFromCommand(cmd));
                    }
                    throw e;
                }
                if (tempPerson1.getClass() != Teacher.class){
                    throw new Exception("Attempt to modify the student using the no-student modify command\n"
                            + "\nStudent:\n" + toStringPerson(tempPerson1) + "\nCommand:\n" + toStringFromCommand(cmd));
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
                try{
                psDao.deletePerson(dPC.getId());
                }catch (Exception e1){
                    throw new Exception(e1.getMessage() + "\nCommand:\n" + toStringFromCommand(cmd));
                }
                break;

            default:
                throw new Exception("Untracked Specifier: " + cmd.getCommandSpecifier() + "\nCommand:\n"
                        + cmd.toString());
        }

    }

    public void ShowPeopleId(){
        if (psDao.getClass() == CachedPeopleDAO.class){
            for (Long id : ((CachedPeopleDAO)psDao).getPeopleById().keySet()){
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
            student1.getGrades().remove(sub);
            student1.getGrades().put(sub, grades.get(sub));
        }
    }

    private String toStringFromCommand(Command cmd){
        StringBuffer str = new StringBuffer();
        switch (cmd.getCommandSpecifier()){
            case "MS":
                ModifyStudentCommand mSC = (ModifyStudentCommand) cmd;
                str.append(mSC.getCommandSpecifier());
                str.append("\nID ");
                str.append(mSC.getId());
                if (mSC.getFullName()!=null){
                    str.append("\nFN ");
                    str.append(mSC.getFullName());
                }
                if (mSC.getYearOfBirth()!=0){
                    str.append("\nYB ");
                    str.append(mSC.getYearOfBirth());
                }
                if (mSC.getTelephoneNumber()!=null){
                    str.append("\nTN ");
                    str.append(mSC.getTelephoneNumber());
                }
                if (mSC.getrSubjects()!=null){
                    str.append("\nRS ");
                    for (Person.Subjects sub : mSC.getrSubjects()){
                     str.append(sub);
                     str.append(" ");
                    }
                }
                if (mSC.getGrades()!=null){
                    str.append("\nGS");
                    for (Person.Subjects sub : mSC.getGrades().keySet()){
                        str.append("\n");
                        str.append(sub);
                        str.append(" ");
                        str.append(mSC.getGrades().get(sub));
                    }
                }
                break;
            case "MT":
                ModifyTeacherCommand mTC = (ModifyTeacherCommand) cmd;
                str.append(mTC.getCommandSpecifier());
                str.append("\nID ");
                str.append(mTC.getId());
                if (mTC.getFullName()!=null){
                    str.append("\nFN ");
                    str.append(mTC.getFullName());
                }
                if (mTC.getYearOfBirth()!=0){
                    str.append("\nYB ");
                    str.append(mTC.getYearOfBirth());
                }
                if (mTC.getTelephoneNumber()!=null){
                    str.append("\nTN ");
                    str.append(mTC.getTelephoneNumber());
                }
                if (mTC.getSubject()!=null){
                    str.append("\nST ");
                    str.append(mTC.getSubject());
                }
                if (mTC.getWorkingHours()!=null){
                    str.append("\nWH ");
                    str.append(mTC.getWorkingHours());
                }
                break;
            case "DP":
                DeletePersonCommand dPC = (DeletePersonCommand) cmd;
                str.append(dPC.getCommandSpecifier());
                str.append("\nID ");
                str.append(dPC.getId());
            break;
        }
        if (str.length() == 0){
            str.append("Unknown command");
        }
        return str.toString();
    }

    private String toStringPerson(Person person){
        StringBuilder str = new StringBuilder();
        if (person.getClass() == Student.class){
         Student student = (Student) person;
         str.append("ID ");
         str.append(student.getID());
         str.append("\nFN ");
         str.append(student.getFullName());
         str.append("\nYB ");
         str.append(student.getYearOfBirth());
         str.append("\nTN ");
         str.append(student.getTelephoneNumber());
         str.append("\nGS ");
         for (Person.Subjects sub : student.getGrades().keySet()){
             str.append("\n");
             str.append(sub);
             str.append(" ");
             str.append(student.getGrades().get(sub));
         }
        }else{
            if (person.getClass() == Teacher.class){
                Teacher teacher = (Teacher) person;
                str.append("ID ");
                str.append(teacher.getID());
                str.append("\nFN ");
                str.append(teacher.getFullName());
                str.append("\nYB ");
                str.append(teacher.getYearOfBirth());
                str.append("\nTN ");
                str.append(teacher.getTelephoneNumber());
                str.append("\nST ");
                str.append(teacher.getSubject());
                str.append("\nWH ");
                str.append(teacher.getWorkingHours());
            }
        }
        return str.toString();
    }

}
