package com.myprojects.carpark.mvc;

import com.myprojects.carpark.domain.CarParkApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("cars")
@AllArgsConstructor
public class CarParkController {

    CarParkApi carParkApi;

    @RequestMapping(method = RequestMethod.GET, value = "floor/{floors}/spots/{spots}")
    public ResponseEntity<?> generateCarPark(
            @PathVariable("floors") Integer floors,
            @PathVariable("spots") Integer spots) {
        return ResponseEntity.ok(carParkApi.getGeneratedSlots(floors, spots));
    }
}
