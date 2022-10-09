package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PostDbStoreTest {
    BasicDataSource basicDataSource;

    @Before
    public void inition() throws SQLException {
         basicDataSource = new Main().loadPool();
         basicDataSource.getConnection().prepareStatement("delete from post").execute();
    }

    @Test
    public void whenCreatePost() {
        City city = new City(1, "Riga");
        PostDbStore store = new PostDbStore(basicDataSource);
        CityDbStore cityDbStore = new CityDbStore(basicDataSource);
        Post post = new Post(0, "Test", "Test", LocalDate.now(),
                city);
        cityDbStore.add(city);
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertEquals(postInDb.getName(), (post.getName()));
    }

    @Test
    public void whenFindByIdThenGetPost() {
        City city = new City(1, "Riga");
        City cityTwo = new City(2, "Piter");
        PostDbStore store = new PostDbStore(basicDataSource);
        CityDbStore cityDbStore = new CityDbStore(basicDataSource);
        Post post = new Post(0, "Test", "Test", LocalDate.now(),
                city);
        Post postTwo = new Post(1, "TestTwo", "TestTwo", LocalDate.now(),
                city);
        cityDbStore.add(city);
        cityDbStore.add(cityTwo);
        store.add(post);
        store.add(postTwo);
       assertEquals(store.findById(post.getId()), post);
       assertEquals(store.findById(postTwo.getId()), postTwo);
       assertNull(store.findById(-1));
    }

    @Test
    public void whenFindAllPostsThenList() {
        City city = new City(1, "Riga");
        City cityTwo = new City(2, "Piter");
        PostDbStore store = new PostDbStore(basicDataSource);
        CityDbStore cityDbStore = new CityDbStore(basicDataSource);
        Post post = new Post(0, "Test", "Test", LocalDate.now(),
                city);
        Post postTwo = new Post(1, "TestTwo", "TestTwo", LocalDate.now(),
                city);
        cityDbStore.add(city);
        cityDbStore.add(cityTwo);
        store.add(post);
        store.add(postTwo);
        List<Post> posts = store.findAll();
        assertTrue(posts.containsAll(List.of(post, postTwo)));
        assertEquals(2, posts.size());
    }

    @Test
    public void whenUpdatePostThenNewPost() {
        City city = new City(1, "Riga");
        City cityTwo = new City(2, "Piter");
        PostDbStore store = new PostDbStore(basicDataSource);
        CityDbStore cityDbStore = new CityDbStore(basicDataSource);
        Post postTwo = new Post(1, "TestTwo", "TestTwo", LocalDate.now(),
                city);
        cityDbStore.add(city);
        cityDbStore.add(cityTwo);
        store.add(postTwo);
        Post post = new Post(postTwo.getId(), "Test", "Test", LocalDate.now(),
                city);
        store.updatePost(post);
        assertTrue(store.updatePost(post));
        assertEquals(store.findById(postTwo.getId()), post);
        assertNotEquals("TestTwo", store.findById(postTwo.getId()).getName());
    }
}