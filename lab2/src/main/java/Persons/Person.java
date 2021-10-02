package Persons;

import DAO.Dao;
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
    private int Id = -1;                // gets the Id only after writing to the file (or if Person readed from file)

    public Person(String fullName, int dateOfBirth, String telephoneNumber) throws Exception {
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
        return Id;
    }

    public void setId(int Id){
        this.Id = Id;
    }

}
