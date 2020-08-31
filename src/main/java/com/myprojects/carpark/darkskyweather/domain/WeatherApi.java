package com.myprojects.carpark.darkskyweather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import tk.plogitech.darksky.forecast.model.Currently;
import tk.plogitech.darksky.forecast.model.Daily;
import tk.plogitech.darksky.forecast.model.Hourly;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WeatherApi {

    WeatherService weatherService;

    public Currently getCurrentWeaher() {
        return weatherService.getCurrentWeather();
    }

    public Daily getDailyForecast() {
        return weatherService.fetchDailyForecast();
    }

    public Hourly getHourlyForecast() {
        return weatherService.fetchHourlyForecast();
    }
}
