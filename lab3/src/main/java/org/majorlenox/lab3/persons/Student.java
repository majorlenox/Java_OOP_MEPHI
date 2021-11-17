package org.majorlenox.lab3.persons;

import java.util.HashMap;

public class Student extends Person {

  private HashMap<Subjects, Double> grades;

  public Student() {}

  public Student(String fullName, int yearOfBirth, String telephoneNumber, HashMap<Subjects, Double> grades) {
    super(fullName, yearOfBirth, telephoneNumber);
    this.grades = grades;
  }

  public HashMap<Subjects, Double> getGrades() {
    return grades;
  }
}
