package org.majorlenox.lab3.structures;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.persons.Student;
import org.majorlenox.lab3.persons.Teacher;

import java.io.IOException;
import java.util.HashMap;

public class PersonArrayDeserializer extends StdDeserializer<HashMap<Long, Person>> {
    public PersonArrayDeserializer() {
        this(null);
    }

    public PersonArrayDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public HashMap<Long, Person> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        HashMap<Long, Person> mapPerson = new HashMap<Long, Person>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < node.size(); i++) {
            if (node.get(i).has("grades")) { // this means that it is a Student
                Student student = objectMapper.readValue(node.get(i).toString(), Student.class);
                mapPerson.put(student.getID(), student);
            } else {  // in another case, it is the Teacher
                Teacher teacher = objectMapper.readValue(node.get(i).toString(), Teacher.class);
                mapPerson.put(teacher.getID(), teacher);
            }
        }
        return mapPerson;
    }
}


