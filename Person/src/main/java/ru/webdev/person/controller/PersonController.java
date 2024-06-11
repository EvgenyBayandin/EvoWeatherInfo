package ru.webdev.person.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.person.model.Person;
import ru.webdev.person.model.Weather;
import ru.webdev.person.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Person putPerson(@PathVariable int id, @RequestBody Person person)  {
        Person p = repository.findById(id).get();
        p.setFirstname(person.getFirstname());
        p.setSurname(person.getSurname());
        p.setLastname(person.getLastname());
        p.setLocation(person.getLocation());
        p.setBirthday(person.getBirthday());
        Person savedPerson = repository.save(p);
        return repository.save(savedPerson);
    }

    @DeleteMapping("/{id}")
    public Person deletePerson(@PathVariable int id)  {
        Person p = repository.findById(id).get();
        repository.delete(p);
        return p;
    }

    @GetMapping("{id}/weather")
    public ResponseEntity<Weather> getWeather(@PathVariable int id)  {
       if(repository.existsById(id)) {
           String location = repository.findById(id).get().getLocation();
           Weather weather = restTemplate.getForObject("http://localhost:8083/location/weather?location=" + location, Weather.class);
           return new ResponseEntity<>(weather, HttpStatus.OK);
       }
       return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

}
