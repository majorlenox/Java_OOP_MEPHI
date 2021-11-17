package org.majorlenox.lab3.dao;

import org.majorlenox.lab3.persons.Person;

import java.util.Optional;
import java.util.Set;

public interface Dao {

  Optional<Person> getPerson(long id);

  Set<Person> setOfPersons();

  void add(Person person) throws NullPointerException;

  void del(long id);

}
