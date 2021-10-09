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

public class PeopleDAO implements Dao {

    String pathToDirectory;
    String pathToIdsFile;

    public PeopleDAO(String pathToIdsFile, String pathToDirectory){
        this.pathToDirectory = pathToDirectory;
        this.pathToIdsFile = pathToIdsFile;
    }

    public Person readPerson(int id) throws Exception {
        RandomAccessFile rwID = new RandomAccessFile(pathToIdsFile + "IDs.txt", "rw");
        if (rwID.length()< id){ throw new Exception("Incorrect ID or IDs.txt!"); }
        rwID.seek(id);
        int typeOfPerson = rwID.read() - '0'; // 1 - Student, 2 - Teacher
        if (typeOfPerson == 0) {throw new Exception("Person doesn't exist in IDs.txt!"); }
        File fPerson = new File(pathToDirectory + id + ".json");
        if (!fPerson.exists()){ throw new Exception("File with this ID doesn't exists!"); }

        ObjectMapper objectMapper = new ObjectMapper();
        if (typeOfPerson == 1){
            return objectMapper.readValue(fPerson, Student.class);
        }
        if (typeOfPerson == 2){
            return objectMapper.readValue(fPerson, Teacher.class);
        }

        throw new Exception("Person type is unknown!");
    }

    public void writePerson(Person person) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        if (person.getID() == -1) { setNewId(person); }
        objectMapper.writeValue(new File( pathToDirectory + person.getID() + ".json"), person);
    }

    public void deletePerson(int id) throws Exception{
        RandomAccessFile rwID = new RandomAccessFile(pathToIdsFile + "IDs.txt", "rw");
        if (rwID.length()< id){ throw new Exception("Incorrect ID or IDs.txt!"); }
        rwID.seek(id);
        rwID.write('0');
        Files.delete(Paths.get(pathToDirectory + id + ".json"));
    }

    private void setNewId(Person person) throws Exception {
        RandomAccessFile rwID = new RandomAccessFile(pathToIdsFile + "IDs.txt", "rw");
        if (rwID.length() == 0) {
            rwID.close();
            Formatter fID = new Formatter(pathToIdsFile + "IDs.txt");
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

        int id = -1;
        for (int i = 0; i < rwID.length(); i++) {
            if (rwID.read() == '0') {
                id = i;
                break;
            }
        }

        if (id == -1) {
            System.err.println("Ids overflow!");
            rwID.close();
            throw new Exception();
        }

        rwID.seek(id);
        if (person.getClass() == Student.class){ rwID.write('1'); }else{
            if (person.getClass() == Teacher.class){ rwID.write('2'); }
        }
        rwID.close();

        person.setId(id);
    }

}
