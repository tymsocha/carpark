package com.myprojects.carpark.utils;

import org.springframework.beans.factory.annotation.Value;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.Latitude;
import tk.plogitech.darksky.forecast.Longitude;

public class WeatherApiConstans {

    @Value("${darksky.api.key}")
    public static String darkSkyApiKey;

    private static final Latitude rzeszowLat = new Latitude(50.041187);
    private static final Longitude rzeszowLon = new Longitude(21.999121);
    public static final GeoCoordinates rzeszowCoordinates = new GeoCoordinates(rzeszowLon, rzeszowLat);
}

