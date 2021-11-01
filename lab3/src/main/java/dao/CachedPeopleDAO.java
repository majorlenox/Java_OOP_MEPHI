package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import persons.Person;
import persons.Student;
import persons.Teacher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.HashMap;

public class CachedPeopleDAO implements Dao {

  private HashMap<Long, Person> peopleById;
  private String pathToDirectory;
  private String pathToIdFile;

  public CachedPeopleDAO(String pathToIdsfile, String pathToDirectory) {
    this.pathToDirectory = pathToDirectory;
    this.pathToIdFile = pathToIdsfile;
  }

  public Person readPerson(long id) throws Exception {

    String[] files = new File(pathToDirectory).list();
    ObjectMapper fileReader = new ObjectMapper();
    if (files != null) {
      for (String file : files) {
        if (file.equals(id + ".json")) {
          try {
            Student student = fileReader.readValue(pathToDirectory + file, Student.class);
            peopleById.put(id, student);
            return student;
          } catch (Exception e) {
            try {
              Teacher teacher = fileReader.readValue(pathToDirectory + file, Teacher.class);
              peopleById.put(id, teacher);
              return teacher;
            } catch (Exception e1) {
              throw new Exception("Unknown type of Person in file \n" + pathToDirectory + file);
            }
          }
        }
      }
    }
    throw new Exception("File with ID = " + id + " doesn't exist in " + pathToDirectory + " ");
  }

  public void writePerson(Person person) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    if (person.getID() == 0) {
      setNewId(person);
    }
    objectMapper.writeValue(new File(pathToDirectory + person.getID() + ".json"), person);
    peopleById.put(person.getID(), person);
  }

  public void deletePerson(long id) throws Exception {
    if (!Files.exists(Paths.get(pathToDirectory + id + ".json"))) {
      throw new Exception(
          "Person with id = " + id + " doesn't exist in directory: " + pathToIdFile);
    }
    Files.delete(Paths.get(pathToDirectory + id + ".json"));
    peopleById.remove(id);
  }

  private void setNewId(Person person) throws Exception {
    if (!Files.exists(Paths.get(pathToIdFile + "ID.txt"))) {
      Formatter fID = new Formatter(pathToIdFile + "ID.txt");
      fID.format("1");
      fID.close();
      person.setId(1);
      return;
    }
    BufferedReader reader = new BufferedReader(new FileReader(pathToIdFile + "ID.txt"));
    long id = Long.parseLong(reader.readLine()) + 1;
    person.setId(id);
    reader.close();
    FileWriter writer = new FileWriter(pathToIdFile + "ID.txt");
    writer.write(String.valueOf(id));
    writer.close();
  }

  public HashMap<Long, Person> getPeopleById() {
    return peopleById;
  }
}
