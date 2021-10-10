package dao;

import persons.Person;

public interface Dao {

    void writePerson(Person person) throws Exception;

    Person readPerson(long id) throws Exception;

    void deletePerson(long id) throws Exception;

}
