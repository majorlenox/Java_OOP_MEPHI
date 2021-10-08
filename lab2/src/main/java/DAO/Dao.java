package DAO;

import Persons.Person;

public interface Dao {

    String pathToIdsFile ="target/";
    String pathToDirectory ="./";

    void writePerson(Person person) throws Exception;

    Person readPerson(int id) throws Exception;

    void deletePerson(int id) throws Exception;

}
