package DAO;

import Persons.Person;
import Persons.Student;
import Persons.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.HashMap;

public class CachedPeopleDAO implements Dao{

    HashMap<Integer, Person> peopleById;
    String pathToDirectory;
    String pathToIdsfile;

    public CachedPeopleDAO(String pathToIdsfile, String pathToDirectory){
        this.pathToDirectory = pathToDirectory;
        this.pathToIdsfile = pathToIdsfile;
    }

    public Person readPerson(int Id) throws Exception {

        RandomAccessFile rwID = new RandomAccessFile(pathToIdsfile + "IDs.txt", "rw");
        if (rwID.length()<Id){ throw new Exception("Incorrect ID or IDs.txt!"); }
        rwID.seek(Id);
        int typeOfPerson = rwID.read(); // 1 - Student, 2 - Teacher
        if (typeOfPerson == 0) {throw new Exception("Person doesn't exist in IDs.txt!"); }
        File fPerson = new File(pathToDirectory + Id + ".json");
        if (!fPerson.exists()){ throw new Exception("File with this ID doesn't exists!"); }

        ObjectMapper objectMapper = new ObjectMapper();
        if (typeOfPerson == 1){
            Person person = objectMapper.readValue(fPerson, Student.class);
            peopleById.put(Id, person);
            return person;
        }
        if (typeOfPerson == 2){
            Person person = objectMapper.readValue(fPerson, Teacher.class);
            peopleById.put(Id, person);
            return person;
        }

        throw new Exception("Person type is unknown!");
    }

    public void writePerson(Person person) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (person.getID() == -1) { setNewId(person); }
        objectMapper.writeValue(new File( pathToDirectory + person.getID() + ".json"), person);
        peopleById.put(person.getID(), person);
    }

    public void deletePerson(int Id) throws Exception{
        RandomAccessFile rwID = new RandomAccessFile(pathToIdsfile + "IDs.txt", "rw");
        if (rwID.length()<Id){ throw new Exception("Incorrect ID or IDs.txt!"); }
        rwID.seek(Id);
        rwID.write(0);
        Files.delete(Paths.get(pathToDirectory + Id + ".json"));
        peopleById.remove(Id);
    }

    public void setNewId(Person person) throws Exception {
        RandomAccessFile rwID = new RandomAccessFile(pathToIdsfile + "IDs.txt", "rw");
        if (rwID.length() == 0) {
            rwID.close();
            Formatter fID = new Formatter(pathToIdsfile + "IDs.txt");
            if (person.getClass() == Student.class){ fID.format("1"); }else{
                if (person.getClass() == Teacher.class){ fID.format("2"); }
            }
            for (int i = 0; i < 10; i++) {
                fID.format("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
            }
            fID.close();

            person.setId(0);
            return;
        }

        int Id = -1;
        for (int i = 0; i < rwID.length(); i++) {
            if (rwID.read() == '0') {
                Id = i;
                break;
            }
        }

        if (Id == -1) {
            System.err.println("Ids overflow!");
            rwID.close();
            throw new Exception();
        }

        rwID.seek(Id);
        if (person.getClass() == Student.class){ rwID.write('1'); }else{
            if (person.getClass() == Teacher.class){ rwID.write('2'); }
        }
        rwID.close();

        person.setId(Id);
    }

}
