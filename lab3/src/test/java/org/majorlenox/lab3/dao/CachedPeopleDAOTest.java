package org.majorlenox.lab3.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.persons.Student;
import org.majorlenox.lab3.persons.Teacher;
import org.majorlenox.lab3.structures.PersonArrayDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CachedPeopleDAOTest {

    String personsCacheJson = "[{\"fullName\":\"Teacher1\",\"yearOfBirth\":1988,\"telephoneNumber\":\"7909323214\"," +
            "\"id\":1,\"subject\":\"CHEMISTRY\",\"workingHours\":\"8:10 - 16:05\"},{\"fullName\":\"Teacher2\"," +
            "\"yearOfBirth\":1980,\"telephoneNumber\":\"89402142132\",\"id\":2,\"subject\":\"ALGEBRA\"," +
            "\"workingHours\":\"7:50 - 15:00\"},{\"fullName\":\"Teacher3\",\"yearOfBirth\":1970," +
            "\"telephoneNumber\":\"88005553535\",\"id\":3,\"subject\":\"HISTORY\"," +
            "\"workingHours\":\"9:00 - 15:45\"},{\"fullName\":\"Student1\",\"yearOfBirth\":2002," +
            "\"telephoneNumber\":\"79043423122\",\"id\":4,\"grades\":{\"ALGEBRA\":4.4,\"PHYSICS\":4.7," +
            "\"HISTORY\":4.0}},{\"fullName\":\"Student2\",\"yearOfBirth\":1999,\"telephoneNumber\":\"79043294332\"," +
            "\"id\":5,\"grades\":{\"ALGEBRA\":4.0,\"LITERATURE\":5.0,\"MUSIC\":4.5}},{\"fullName\":\"Student3\"," +
            "\"yearOfBirth\":2000,\"telephoneNumber\":\"83212392932\",\"id\":6,\"grades\":{\"GEOGRAPHY\":4.0," +
            "\"PAINTING\":4.5,\"MATHEMATICS\":5.0}}]";
    SimpleModule module = new SimpleModule().addDeserializer(HashMap.class, new PersonArrayDeserializer());
    ObjectMapper mapper = new ObjectMapper().registerModule(module);
    HashMap<Long, Person> mapPerson = mapper.readValue(personsCacheJson, HashMap.class);
    ObjectMapper objectMapper = new ObjectMapper();

    String[] strPersons = personsCacheJson.split("},");
    Teacher teacher1 = objectMapper.readValue(strPersons[0].replace("[", "") + "}",
            Teacher.class);
    Teacher teacher2 = objectMapper.readValue(strPersons[1] + "}", Teacher.class);
    Teacher teacher3 = objectMapper.readValue(strPersons[2] + "}", Teacher.class);
    Student student1 = objectMapper.readValue(strPersons[3] + "}", Student.class);
    Student student2 = objectMapper.readValue(strPersons[4] + "}", Student.class);
    Student student3 = objectMapper.readValue(strPersons[5].replace("]", ""), Student.class);
    CachedPeopleDAO peopleDAO = new CachedPeopleDAO(mapPerson);

    CachedPeopleDAOTest() throws JsonProcessingException {
    }

    @Test
    void getPerson() throws IOException {
        assertTrue(isPersonsEquals(teacher1, peopleDAO.getPerson(1).get()));
        assertTrue(isPersonsEquals(teacher2, peopleDAO.getPerson(2).get()));
        assertTrue(isPersonsEquals(teacher3, peopleDAO.getPerson(3).get()));
        assertTrue(isPersonsEquals(student1, peopleDAO.getPerson(4).get()));
        assertTrue(isPersonsEquals(student2, peopleDAO.getPerson(5).get()));
        assertTrue(isPersonsEquals(student3, peopleDAO.getPerson(6).get()));
    }

    @Test
    void setOfPersons() {
        HashSet<Person> setPerson = peopleDAO.setOfPersons();
        HashSet<Person> setPersonExpected = new HashSet<Person>();
        setPersonExpected.add(teacher1);
        setPersonExpected.add(teacher2);
        setPersonExpected.add(teacher3);
        setPersonExpected.add(student1);
        setPersonExpected.add(student2);
        setPersonExpected.add(student3);
        //student3.setFullName("ds");
        assertTrue(isHashSetsEquals(setPerson, setPersonExpected));
    }

    @Test
    void add() {
        peopleDAO.add(null);
    }

    @Test
    void del() {
    }

    @Test
    void saveCache() {
    }

    @Test
    void loadCache() {
    }

    private boolean isPersonsEquals(Person person1, Person person2){
        if ((person1 == null)&&(person2 == null)) { return  true;}
        if ((person1 == null)||(person2 == null)){return false;}
        if (!person1.getFullName().equals(person2.getFullName())){
            return false;
        }
        if (person1.getID() != person2.getID()){
            return false;
        }
        if (!person1.getTelephoneNumber().equals(person2.getTelephoneNumber())){
            return false;
        }
        if (person1.getYearOfBirth() != person2.getYearOfBirth()){
            return  false;
        }
    if ((person1.getClass() == Student.class)&&(person2.getClass() == Student.class)){
        for (Person.Subjects subject : ((Student) person1).getGrades().keySet()){
            if (!((((Student) person1).getGrades().get(subject) <= ((Student) person2).getGrades().get(subject) + 0.001)
                    && (((Student) person1).getGrades().get(subject) >=
                    ((Student) person2).getGrades().get(subject) - 0.001))){
                return false;
            }
        }
        return true;
    }
    if ((person1.getClass() == Teacher.class)&&(person2.getClass() == Teacher.class)){
        if (((Teacher) person1).getSubject() != ((Teacher) person2).getSubject()){
            return false;
        }
        if (!((Teacher) person1).getWorkingHours().equals(((Teacher) person2).getWorkingHours())){
            return false;
        }
        return true;
    }
    return false;
    }

    private boolean isHashSetsEquals(HashSet<Person> hashSet1, HashSet<Person> hashSet2){
        if (hashSet1.size() != hashSet2.size()){
            return false;
        }

        HashMap<Person, Boolean> checkedPersons = new HashMap<Person, Boolean>();   // for second array
        for (Person person2: hashSet2){
            checkedPersons.put(person2, false);
        }
        for (Person person1: hashSet1){
            for (Person person2: hashSet2){
                if (!checkedPersons.get(person2)){
                    if (isPersonsEquals(person1, person2)){
                        checkedPersons.replace(person2, true);
                    }
                }
            }
        }

        for (Person person2: hashSet2){
            if (!checkedPersons.get(person2)){
                return false;
            }
        }

        return true;
    }

}