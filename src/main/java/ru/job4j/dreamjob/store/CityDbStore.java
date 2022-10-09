package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@ThreadSafe
public class CityDbStore {
    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(Post.class.getName());

    public CityDbStore(BasicDataSource pool) {
        this.pool = pool;
    }


    public City add(City city) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO cities (name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, city.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    city.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
           LOG.error("Error", e);
        }
        return city;
    }

    public Collection<City> findAllCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM cities")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(it.getInt("id"),
                            it.getString("name")));


                }
            }
        } catch (SQLException throwables) {
            LOG.error("Error", throwables);
        }
        return cities;
    }
}

