package ru.webdev.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.weather.model.Root;

@RestController
public class WeatherController {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Value("${appid}")
    String appid;

    @org.springframework.web.bind.annotation.GetMapping("/weather")
    public Root getWeather(){
        return restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.61&units=metric&appid=" + appid, Root.class);
    }
}
