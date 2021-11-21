package org.majorlenox.lab3.dao;

import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.server.StaticCacheReader;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Dao {

  Optional<Person> getPerson(long id);

  Set<Person> setOfPersons();

  void add(Person person) throws NullPointerException;

  void del(long id);

  void saveCache(String filepath) throws IOException;

  void loadCache(String filepath) throws IOException;

}
