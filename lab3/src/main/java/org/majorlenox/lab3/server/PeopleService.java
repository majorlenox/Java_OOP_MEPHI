package org.majorlenox.lab3.server;

import org.majorlenox.lab3.dao.CachedPeopleDAO;
import org.majorlenox.lab3.dao.Dao;
import org.majorlenox.lab3.algorithms.Pair;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.persons.Student;
import org.majorlenox.lab3.persons.Teacher;

import java.io.IOException;
import java.util.*;

public class PeopleService {

    private Dao peopleDao;
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
            if (peopleDao.setOfPersons().size()!=0){
            directory = filepath.substring(0, filepath.lastIndexOf('/'))
                    + "/PreviousCache.json"; // for previous cache
            peopleDao.saveCache(directory);
            }
            peopleDao.loadCache(filepath);
            for (Person person : peopleDao.setOfPersons()){
                if (id < person.getID()){id = person.getID();}  // max id from current persons
            }

            return directory;
        }

        public long createStudent(String fullName, int yearOfBirth, String telephoneNumber, HashMap<Person.Subjects, Double> grades) throws NullPointerException{
            Student student = new Student(fullName, yearOfBirth, telephoneNumber, grades);
            student.setId(++id);
            peopleDao.add(student);
            return id;
        }

        public long createTeacher(String fullName, int yearOfBirth, String telephoneNumber, Person.Subjects subject, String workingHours) throws NullPointerException{
            Teacher teacher = new Teacher(fullName, yearOfBirth, telephoneNumber, subject, workingHours);
            teacher.setId(++id);
            peopleDao.add(teacher);
            return id;
        }

        public String makeListOfPersons(){
            HashSet<Person> setOfPersons = peopleDao.setOfPersons();
            ArrayList<Pair<Long, Person>> listIdPerson = new ArrayList<Pair<Long, Person>>();
            for (Person person : setOfPersons){
                listIdPerson.add(new Pair<Long, Person>(person.getID(), person));
            }
            listIdPerson.sort(new Comparator<Pair<Long, Person>>() {
                @Override
                public int compare(Pair<Long, Person> o1, Pair<Long, Person> o2) {
                    if (o1.first < o2.first) {
                        return -1;
                    } else {
                        if (o1.first > o2.first) {
                            return 1;
                        }
                        return 0;
                    }
                }
            });
            return fromListToString(listIdPerson);
        }

        private String fromListToString(ArrayList<Pair<Long, Person>> list){
            StringBuilder strOut = new StringBuilder();
            for (Pair<Long, Person> personPair : list){
                strOut.append("ID ");
                strOut.append(personPair.first);
                strOut.append(": ");
                if (personPair.second.getClass() == Student.class){
                    strOut.append("Student - ");
                    strOut.append(personPair.second.getFullName());
                }else {
                    if (personPair.second.getClass() == Teacher.class) {
                        strOut.append("Teacher - ");
                        strOut.append(personPair.second.getFullName());
                        strOut.append(", Subject: ");
                        strOut.append(((Teacher)personPair.second).getSubject());
                    }else{
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


        /*


        public UUID createStudent(String fullName, Date birthDate, String phoneNumber, List<Subject> subjects, Map<Subject, Double> marks) {
            Student student = new Student(fullName, birthDate, phoneNumber, subjects, marks);
            try {
                peopleDao.save(student);
                return student.getId();
            } catch (IOException e) {
                System.err.println("Could not create student record #" + student.getId());
                return null;
            }
        }

        public Person getPerson(UUID id) {
            Optional<Person> person = peopleDao.get(id);
            return person.orElse(null);
        }

        public void deletePerson(Person person) throws IOException {
            this.deletePerson(person.getId());
        }

        public void deletePerson(UUID id) throws IOException {
            peopleDao.delete(id);
        }

        public UUID updatePerson(UpdateCmd cmd) {
            Person person = this.getPerson(cmd.id);
            if (person == null) {
                System.err.println("Could not find person record #" + cmd.id);
                return null;
            }
            if (person instanceof Teacher) {
                if (cmd.teacherSubject != null) ((Teacher) person).subject = cmd.teacherSubject;
                if (cmd.teacherWorktime != null) ((Teacher) person).workTime = cmd.teacherWorktime;
            } else if (person instanceof Student) {
                if (cmd.studentSubjects != null) ((Student) person).subjects = cmd.studentSubjects;
                if (cmd.studentMarks != null) {
                    Double mark;
                    ((Student) person).marks = new HashMap<>();
                    for (Subject subject: ((Student) person).subjects)
                        if ((mark = cmd.studentMarks.get(subject)) != null)
                            ((Student) person).marks.put(subject, mark);
                }
            } else throw new RuntimeException();
            try {
                peopleDao.save(person);
                return person.getId();
            } catch (IOException e) {
                System.err.printf("Could not update person record %s\n", cmd.id.toString());
                return null;
            }
        }

        public void listAllPeople() {
            List<Person> people = peopleDao.listAll();
            for (Person person: people)
                person.printInfo();
        }

        public Dao<Person> getDao() {
            return this.peopleDao;
        }
    }*/

}
