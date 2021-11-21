package org.majorlenox.lab3.server;

import org.majorlenox.lab3.dao.CachedPeopleDAO;
import org.majorlenox.lab3.dao.Dao;
import org.majorlenox.lab3.persons.Person;
import org.majorlenox.lab3.persons.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@SpringBootApplication
@RestController
public class Lab3Application {

    PeopleService peopleService = new PeopleService();

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @GetMapping("/")
    public String serverStatus() {
        return "Server on.";
    }

    @GetMapping ("/save")
    public void saveCache(@RequestParam String filepath){
        try{
            peopleService.saveCache(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create/student")
    public void createStudent (@RequestBody Student student){
        try {
            peopleService.createStudent(student.getFullName(), student.getYearOfBirth(), student.getTelephoneNumber(), student.getID(), student.getGrades());
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
