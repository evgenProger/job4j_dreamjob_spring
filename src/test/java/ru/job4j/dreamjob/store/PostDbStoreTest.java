package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PostDbStoreTest {
    @Test
    public void whenCreatePost() {
        City city = new City(1, "Riga");
        BasicDataSource basicDataSource = new Main().loadPool();
        PostDbStore store = new PostDbStore(basicDataSource);
        CityDbStore cityDbStore = new CityDbStore(basicDataSource);
        Post post = new Post(0, "Test", "Test", LocalDate.now(),
                city);
        cityDbStore.add(city);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertEquals(postInDb.getName(), (post.getName()));
        assertEquals(postInDb.getCity().getName(), "Riga");
    }
}