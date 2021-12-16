package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import persons.Person;

public interface SpringDataJPA extends JpaRepository<Person, Long> {

    Person findByName(String name);

}