package org.majorlenox.lab3.server;

import org.majorlenox.lab3.persons.Student;
import org.majorlenox.lab3.persons.Teacher;
import org.majorlenox.lab3.structures.ModifiedData;
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

    @GetMapping("/save")
    public String saveCache(@RequestParam String filepath) {
        try {
            peopleService.saveCache(filepath);
            return "Cache of persons has been saved in a file: " + filepath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/load")
    public String loadCache(@RequestParam String filepath) {
        try {
            String message = "Cache of persons has been loaded from a file: " + filepath + "\n";
            String filepathPrev = peopleService.loadCache(filepath);
            if (!filepathPrev.equals("")) {
                message += "The previous cache was saved in a file " + filepathPrev + "\n";
            }
            return message;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/get")
    public String getPerson(@RequestParam long id) {
        if (peopleService.getPerson(id).isEmpty()) {
            return "Person with id = " + id + " doesn't exist";
        }
        return peopleService.getPersonInfo(id);
    }

    @GetMapping("/del")
    public String deletePerson(@RequestParam long id) {
        if (peopleService.getPerson(id).isEmpty()) {
            return "Person with id = " + id + " doesn't exist";
        }
        String name = peopleService.getPerson(id).get().getFullName();
        peopleService.deletePerson(id);
        return "Person " + name + " with id = " + id + " was successfully deleted";
    }

    @PostMapping("/create/student")
    public String createStudent(@RequestBody Student student) {
        try {
            long id = peopleService.createStudent(student.getFullName(), student.getYearOfBirth(), student.getTelephoneNumber(), student.getGrades());
            return "Student - " + student.getFullName() + " was successfully created and now has id = " + id;
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/create/teacher")
    public String createStudent(@RequestBody Teacher teacher) {
        try {
            long id = peopleService.createTeacher(teacher.getFullName(), teacher.getYearOfBirth(), teacher.getTelephoneNumber(), teacher.getSubject(), teacher.getWorkingHours());
            return "Teacher - " + teacher.getFullName() + " was successfully created and now has id = " + id;
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/modify")
    public String modifyPerson(@RequestBody ModifiedData md) {
        try {
            if (peopleService.getPerson(md.id).isEmpty()) {
                return "Person to modify doesn't exist";
            }
            peopleService.modifyPerson(md);
            return "Person with id = " + md.id + " was successfully modified";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/show")
    public String showAllPersons() {
        return peopleService.makeListOfPersons();
    }


}
