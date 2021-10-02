package DAO;

import Persons.Person;

public interface Dao {

    String pathToIdsfile ="target/";
    String pathToDirectory ="./";

    void writePerson(Person person) throws Exception;

    Person readPerson(int Id) throws Exception;

    void deletePerson(int Id) throws Exception;

    void setNewId(Person person) throws Exception;

}
