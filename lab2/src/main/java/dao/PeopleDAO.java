package dao;

import persons.Person;
import persons.Student;
import persons.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;

public class PeopleDAO implements Dao {

    String pathToDirectory;
    String pathToIdsFile;

    public PeopleDAO(String pathToIdsFile, String pathToDirectory){
        this.pathToDirectory = pathToDirectory;
        this.pathToIdsFile = pathToIdsFile;
    }

    public Person readPerson(long id) throws Exception {
        String[] files = new File(pathToDirectory).list();
        ObjectMapper objectMapper = new ObjectMapper();
        if (files != null) {
            for (String file : files) {
                if (file.equals(id + ".json")){
                    File fPerson = new File(pathToDirectory + id + ".json");
                    try{
                        return objectMapper.readValue(fPerson, Student.class);
                    }catch(Exception e){
                        try{
                        return objectMapper.readValue(fPerson, Teacher.class);
                        }catch (Exception e1){
                            throw new Exception("Unknown type of Person in file \n" + pathToDirectory + file);
                        }
                    }
                }
            }
        }
        throw new Exception("File with ID = " + id + " doesn't exist in " + pathToDirectory);
    }

    public void writePerson(Person person) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (person.getID() == 0) { setNewId(person); }
        objectMapper.writeValue(new File( pathToDirectory + person.getID() + ".json"), person);
    }

    public void deletePerson(long id) throws Exception{
        if (!Files.exists(Paths.get(pathToDirectory + id + ".json"))){
            throw new Exception("Person with id = " + id + " doesn't exist in directory: " + pathToIdsFile);
        }
        Files.delete(Paths.get(pathToDirectory + id + ".json"));
    }

    private void setNewId(Person person) throws Exception {
        if (!Files.exists(Paths.get(pathToIdsFile + "ID.txt"))) {
            Formatter fID = new Formatter(pathToIdsFile + "ID.txt");
            fID.format("1");
            fID.close();
            person.setId(1);
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(pathToIdsFile + "ID.txt"));
        long id = Long.parseLong(reader.readLine()) + 1;
        person.setId(id);
        reader.close();
        FileWriter writer = new FileWriter(pathToIdsFile + "ID.txt");
        writer.write(String.valueOf(id));
        writer.close();
    }

}
