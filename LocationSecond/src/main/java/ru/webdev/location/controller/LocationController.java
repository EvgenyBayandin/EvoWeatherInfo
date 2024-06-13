package ru.webdev.location.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.webdev.location.model.Location;
import ru.webdev.location.model.Weather;
import ru.webdev.location.repository.LocationRepository;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Value("${weather.url}")
    String weatherUrl;

    @Autowired
    private LocationRepository repository;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping
    public Iterable<Location> findAll(){
        return repository.findAll();
    }

    @GetMapping("/")
    public Optional<Location> getLocation(@RequestParam String name){
        return repository.findByName(name);
    }

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String location) {
        Location l = repository.findByName(location).get();
//        String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s", l.getLatitude(), l.getLongitude());
        String url = String.format("http://%s/weather?lat=%s&lon=%s", weatherUrl, l.getLatitude(), l.getLongitude());
        System.out.println(url);
        return restTemplate.getForObject(url, Weather.class);
    }

    @PostMapping
    public Location save(@RequestBody Location location){
        return repository.save(location);
    }

    @PutMapping("/")
    public Location putLocation(@RequestBody Location location,  @RequestParam String name){
        Location existingLocation = repository.findByName(name).get();
        existingLocation.setName(location.getName());
        existingLocation.setLatitude(location.getLatitude());
        existingLocation.setLongitude(location.getLongitude());
        Location savedLocation = repository.save(existingLocation);
        return repository.save(savedLocation);
    }

    @DeleteMapping("/")
    public Location deleteLocation(@RequestParam String name){
        Location existingLocation = repository.findByName(name).get();
        repository.delete(existingLocation);
        return existingLocation;
    }

}
