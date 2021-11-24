package org.majorlenox.lab3.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.majorlenox.lab3.dao.CachedPeopleDAO;
import org.majorlenox.lab3.dao.Dao;
import org.majorlenox.lab3.persons.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StaticCacheReader {

    private static final StaticCacheReader instance;

    private StaticCacheReader(){}

    //static block initialization for exception handling
    static{
        try{
            instance = new StaticCacheReader();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static StaticCacheReader getInstance(){
        return instance;
    }

    public ArrayList<Person> readPersons(String filename) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<ArrayList<Person>> typeRef = new TypeReference<ArrayList<Person>>(){};
            return (objectMapper.readValue(new File(filename), typeRef));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
