package com.myprojects.carpark.darkskyweather.domain;

import com.myprojects.carpark.utils.WeatherApiConstans;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.model.Currently;
import tk.plogitech.darksky.forecast.model.Daily;
import tk.plogitech.darksky.forecast.model.Hourly;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
class WeatherService {

    DarkSkyJacksonClient darkSkyClient;
    String darkSkyApiKey;

    Currently getCurrentWeather() {
        Currently currentWeather = new Currently();
        try {
            currentWeather = darkSkyClient.forecast(createForeCastRequest()).getCurrently();
        } catch (ForecastException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    Daily fetchDailyForecast() {
        Daily dailyWeather = new Daily();
        try {
            dailyWeather = darkSkyClient.forecast(createForeCastRequest()).getDaily();
        } catch (ForecastException e) {
            e.printStackTrace();
        }
        return dailyWeather;
    }

    Hourly fetchHourlyForecast() {
        Hourly hourlyForecast = new Hourly();
        try {
            hourlyForecast = darkSkyClient.forecast(createForeCastRequest()).getHourly();
        } catch (ForecastException e) {
            e.printStackTrace();
        }
        return hourlyForecast;
    }

    private ForecastRequest createForeCastRequest() {
        return new ForecastRequestBuilder()
                .key(new APIKey(darkSkyApiKey))
                .location(WeatherApiConstans.rzeszowCoordinates)
                .build();
    }
}
