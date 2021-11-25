package org.majorlenox.lab3.dao;

import org.majorlenox.lab3.persons.Person;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

public interface Dao {

    Optional<Person> getPerson(long id);

    HashSet<Person> setOfPersons();

    void add(Person person) throws NullPointerException;

    void del(long id);

    void saveCache(String filepath) throws IOException;

    void loadCache(String filepath) throws IOException;

}
