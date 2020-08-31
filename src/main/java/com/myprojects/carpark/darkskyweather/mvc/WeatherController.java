package com.myprojects.carpark.darkskyweather.mvc;

import com.myprojects.carpark.darkskyweather.domain.WeatherApi;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WeatherController {

    WeatherApi weatherApi;

    @GetMapping("current")
    ResponseEntity<?> getWeather() {
        return ResponseEntity.ok(weatherApi.getCurrentWeaher());
    }

    @GetMapping("dailyForecast")
    ResponseEntity<?> getDailyForeCast() {
        return ResponseEntity.ok(weatherApi.getDailyForecast());
    }

    @GetMapping("hourlyForecast")
    ResponseEntity<?> getHourlyForecast() {
        return ResponseEntity.ok(weatherApi.getHourlyForecast());
    }
}
