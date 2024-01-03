package com.pitang.test.util;

import com.pitang.test.models.Car;

import java.text.SimpleDateFormat;
import java.util.Set;

public class Util {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Car filterById(Set<Car> colection, Long id) {
        Car carReturned = null;
        for (Car c : colection) {
            if (c.getId().equals(id)) {
                carReturned = c;
                break;
            }
        }
        return carReturned;
    }

    public static Set<Car> removeById(Set<Car> colection, Long id) {
        for (Car c : colection) {
            if (c.getId().equals(id)) {
                colection.remove(c);
                break;
            }
        }
        return colection;
    }
}
