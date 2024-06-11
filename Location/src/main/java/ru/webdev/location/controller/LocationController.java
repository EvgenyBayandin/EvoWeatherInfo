package ru.webdev.location.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.webdev.location.model.Geodata;
import ru.webdev.location.model.Weather;
import ru.webdev.location.repository.GeodataRepository;

@RestController("/location")
public class LocationController {

    @Autowired
    private GeodataRepository repository;
    private RestTemplate restTemplate = new RestTemplate();


    @GetMapping
    public Iterable<Geodata> findAll(){
        return repository.findAll();
    }

//    @GetMapping
//    public Optional<Geodata> getLocation(@RequestParam String location){
//        return repository.findByName(location);
//    }

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String location) {
        Geodata geodata = repository.findByName(location).get();
        String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s", geodata.getLat(), geodata.getLon());
        return restTemplate.getForObject(url, Weather.class);
    }

    @PostMapping
    public Geodata save(@RequestBody Geodata geodata){
        return repository.save(geodata);
    }

}
