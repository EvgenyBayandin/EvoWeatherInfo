package ru.webdev.weather.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.weather.model.Root;

@RestController
public class WeatherController {

    RestTemplate restTemplate = new RestTemplate();

    @Value("appId")
    String appId;

    @org.springframework.web.bind.annotation.GetMapping("/weather")
    public Root getWeather(){
        return restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?lat=55.7522&lon=37.6156&units=metric&appid="+appId, Root.class);
    }
}
