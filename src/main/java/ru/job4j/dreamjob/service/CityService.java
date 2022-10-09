package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityDbStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CityService {
    private final CityDbStore store;

    public CityService(CityDbStore store) {
        this.store = store;
    }

    public City add(City city) {
        return store.add(city);
    }

    public Collection<City> findAllCities() {
        return store.findAllCities();
    }
}
