package org.majorlenox.lab3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.structures.PersonArrayDeserializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public enum EnumCacheReader {
    INSTANCE;

    public HashMap<Long, Person> readPersons(String filename) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(HashMap.class, new PersonArrayDeserializer());
            mapper.registerModule(module);
            return mapper.readValue(new File(filename), HashMap.class);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
