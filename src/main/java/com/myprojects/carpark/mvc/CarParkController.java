package com.myprojects.carpark.mvc;

import com.myprojects.carpark.domain.CarSpotsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarParkController {

    @Autowired
    CarSpotsGenerator carSpotsGenerator;

}
