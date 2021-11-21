package org.majorlenox.lab3.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.persons.Student;
import org.majorlenox.lab3.persons.Teacher;
import org.majorlenox.lab3.server.StaticCacheReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CachedPeopleDAO implements Dao {

  private HashMap<Long, Person> mapPersons;

  @Override
  public Optional<Person> getPerson(long id) {
    return Optional.ofNullable(mapPersons.get(id));
  }

  @Override
  public Set<Person> setOfPersons() {
    return new HashSet<>(mapPersons.values());
  }

  @Override
  public void add(Person person) throws NullPointerException{
    if (person == null) throw new NullPointerException();
    mapPersons.put(person.getID(), person);
  }

  @Override
  public void del(long id) {
    mapPersons.remove(id);
  }

  public CachedPeopleDAO(){
    this.mapPersons = new HashMap<>();
  }

  @Override
  public void saveCache(String filepath) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File(filepath), mapPersons);
  }

  @Override
  public void loadCache(String filepath) throws IOException {
    StaticCacheReader CR = StaticCacheReader.getInstance();
    ObjectMapper objectMapper = new ObjectMapper();
    mapPersons = CR.readPersons(filepath);
  }

}
