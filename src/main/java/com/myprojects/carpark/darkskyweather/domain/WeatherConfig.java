package com.myprojects.carpark.darkskyweather.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;

@Configuration
public class WeatherConfig {

    @Value("${darksky.api.key}")
    String darkSkyApiKey;

    @Bean
    DarkSkyJacksonClient darkSkyClient() {
        return new DarkSkyJacksonClient();
    }

    @Bean
    WeatherApi weatherApi(DarkSkyJacksonClient darkSkyJacksonClient) {
        WeatherService weatherService = new WeatherService(darkSkyJacksonClient, darkSkyApiKey);
        return new WeatherApi(weatherService);
    }
}