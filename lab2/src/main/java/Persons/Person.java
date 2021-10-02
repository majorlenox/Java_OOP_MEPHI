package Persons;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

public abstract class Person {

    public enum Subjects {
        ALGEBRA,
        BIOLOGY,
        PAINTING,
        CHEMISTRY,
        GEOGRAPHY,
        GEOMETRY,
        HISTORY,
        LITERATURE,
        MATHEMATICS,
        MUSIC,
        PHYSICAL_EDUCATION,
        PHYSICS,
        TECHNOLOGY
    }

    private String fullName;
    private int dateOfBirth;
    private String telephoneNumber;
    private int ID;

    public Person(String fullName, int dateOfBirth, String telephoneNumber) throws Exception {
        setNewId();
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public int getID() {
        return ID;
    }

    public void toJson(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(path + this.ID + ".json"), this);
    }

    public void fromJson(String path, int ID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

    }

    private void setNewId() throws Exception {
        RandomAccessFile rwID = new RandomAccessFile("target/IDs.txt", "rw");
        if (rwID.length() == 0) {
            rwID.close();
            Formatter fID = new Formatter("target/IDs.txt");
            fID.format("1");
            for (int i = 0; i < 10; i++) {
                fID.format("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
            }
            fID.close();
            return;
        }

        ID = -1;
        for (int i = 0; i < rwID.length(); i++) {
            if (rwID.read() == '0') {
                ID = i;
                break;
            }
        }

        if (ID == -1) {
            System.err.println("Ids overflow!");
            rwID.close();
            throw new Exception();
        }

        rwID.seek(ID);
        rwID.write('1');
        rwID.close();
    }

}
