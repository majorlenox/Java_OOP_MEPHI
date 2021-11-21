package org.majorlenox.lab3.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.majorlenox.lab3.persons.Person;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class StaticCacheReader {

    private static StaticCacheReader instance;

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

    public HashMap<Long,Person> readPersons(String filename) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<Long, Person> mapPersons = new  HashMap<Long, Person>();
            return (objectMapper.readValue(new File(filename), new TypeReference<HashMap<Long,Person>>(){}));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
