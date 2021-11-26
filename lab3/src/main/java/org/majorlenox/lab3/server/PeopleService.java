package org.majorlenox.lab3.server;

import org.majorlenox.lab3.dao.CachedPeopleDAO;
import org.majorlenox.lab3.dao.Dao;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.persons.Student;
import org.majorlenox.lab3.persons.Teacher;
import org.majorlenox.lab3.structures.ModifiedData;
import org.majorlenox.lab3.structures.Pair;

import java.io.IOException;
import java.util.*;

public class PeopleService {

    private final Dao peopleDao;
    private long id = 0;

    public PeopleService() {
        this.peopleDao = new CachedPeopleDAO();
    }

    public PeopleService(Dao dao) {
        this.peopleDao = dao;
    }

    public void saveCache(String filepath) throws IOException {
        peopleDao.saveCache(filepath);
    }

    public String loadCache(String filepath) throws IOException {
        String directory = "";
        if (peopleDao.setOfPersons().size() != 0) {
            if ("/PreviousCache.json".equals(filepath.substring(filepath.lastIndexOf('/')))) {
                directory = filepath.substring(0, filepath.lastIndexOf('/'))
                        + "/PreviousCache1.json"; // if you want to load the previous cache
            } else {
                directory = filepath.substring(0, filepath.lastIndexOf('/'))
                        + "/PreviousCache.json"; // for previous cache
            }
            peopleDao.saveCache(directory);
        }
        peopleDao.loadCache(filepath);
        for (Person person : peopleDao.setOfPersons()) {
            if (id < person.getID()) {
                id = person.getID();
            }  // max id from current persons
        }

        return directory;
    }

    public long createStudent(String fullName, int yearOfBirth, String telephoneNumber, HashMap<Person.Subjects, Double> grades) throws NullPointerException {
        Student student = new Student(fullName, yearOfBirth, telephoneNumber, grades, ++id);
        peopleDao.add(student);
        return id;
    }

    public long createTeacher(String fullName, int yearOfBirth, String telephoneNumber, Person.Subjects subject, String workingHours) throws NullPointerException {
        Teacher teacher = new Teacher(fullName, yearOfBirth, telephoneNumber, subject, workingHours, ++id);
        peopleDao.add(teacher);
        return id;
    }

    public String makeListOfPersons() {
        HashSet<Person> setOfPersons = peopleDao.setOfPersons();
        ArrayList<Pair<Long, Person>> listIdPerson = new ArrayList<>();
        for (Person person : setOfPersons) {
            listIdPerson.add(new Pair<>(person.getID(), person));
        }
        listIdPerson.sort((o1, o2) -> {
            if (o1.first < o2.first) {
                return -1;
            } else {
                if (o1.first > o2.first) {
                    return 1;
                }
                return 0;
            }
        });
        return fromListToString(listIdPerson);
    }

    public Optional<Person> getPerson(long id) {
        return peopleDao.getPerson(id);
    }

    public String getPersonInfo(long id) {
        if (peopleDao.getPerson(id).isEmpty()) {
            return "Info about the person was not found";
        }
        Person person = peopleDao.getPerson(id).get();
        StringBuilder outputInfo = new StringBuilder();
        if (person.getClass() == Student.class) {
            outputInfo.append("Student");
        } else {
            outputInfo.append("Teacher");
        }
        outputInfo.append(" with id = ");
        outputInfo.append(id);
        outputInfo.append("\nName: ");
        outputInfo.append(person.getFullName());
        outputInfo.append("\nYear of birth: ");
        outputInfo.append(person.getYearOfBirth());
        outputInfo.append("\nTelephone number: ");
        outputInfo.append(person.getTelephoneNumber());
        if (person.getClass() == Student.class) {
            outputInfo.append("\nGrades: ");
            outputInfo.append(((Student) person).getGrades());
        } else {
            outputInfo.append("\nSubject: ");
            outputInfo.append(((Teacher) person).getSubject());
            outputInfo.append("\nWorking hours: ");
            outputInfo.append(((Teacher) person).getWorkingHours());
        }

        return outputInfo.toString();
    }

    public void deletePerson(long id) {
        peopleDao.del(id);
    }

    public void modifyPerson(ModifiedData md) throws RuntimeException {
        Person person = getPerson(md.id).get();
        if ((person.getClass() != Teacher.class) && ((md.workingHours != null) || (md.subject != null))) {
            throw new RuntimeException("Incorrect command, can't modify student with modify teacher command");
        }
        if ((person.getClass() != Student.class) && ((md.newGrades != null) || (md.removeGrades != null))) {
            throw new RuntimeException("Incorrect command, can't modify teacher with modify student command");
        }
        if (md.fullName != null) {
            person.setFullName(md.fullName);
        }
        if (md.yearOfBirth != 0) {
            person.setYearOfBirth(md.yearOfBirth);
        }
        if (md.telephoneNumber != null) {
            person.setTelephoneNumber(md.telephoneNumber);
        }
        if (md.removeGrades != null) { // 1) remove, 2) add
            for (Person.Subjects subject : md.removeGrades) {
                ((Student) person).getGrades().remove(subject);
            }
        }
        if (md.newGrades != null) {
            for (Person.Subjects subject : md.newGrades.keySet()) {
                ((Student) person).getGrades().put(subject, md.newGrades.get(subject));
            }
        }
        if (md.subject != null) {
            ((Teacher) person).setSubject(md.subject);
        }
        if (md.workingHours != null) {
            ((Teacher) person).setWorkingHours(md.workingHours);
        }
    }

    private String fromListToString(ArrayList<Pair<Long, Person>> list) {
        StringBuilder strOut = new StringBuilder();
        for (Pair<Long, Person> personPair : list) {
            strOut.append("ID ");
            strOut.append(personPair.first);
            strOut.append(": ");
            if (personPair.second.getClass() == Student.class) {
                strOut.append("Student - ");
                strOut.append(personPair.second.getFullName());
            } else {
                if (personPair.second.getClass() == Teacher.class) {
                    strOut.append("Teacher - ");
                    strOut.append(personPair.second.getFullName());
                    strOut.append(", Subject: ");
                    strOut.append(((Teacher) personPair.second).getSubject());
                } else {
                    strOut.append("Unknown - ");
                    strOut.append(personPair.second.getFullName());
                }
            }
            strOut.append(", Telephone number: ");
            strOut.append(personPair.second.getTelephoneNumber());
            strOut.append("\n");
        }
        return strOut.toString();
    }

}
