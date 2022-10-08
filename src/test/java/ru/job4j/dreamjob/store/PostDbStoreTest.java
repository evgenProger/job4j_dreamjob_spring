package ru.job4j.dreamjob.store;

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
        PostDbStore store = new PostDbStore(new Main().loadPool());
        Post post = new Post(0, "Test", "Test", LocalDate.now(),
                new City(1, "Test"));
        store.add(post);
        List<Post> all = store.findAll();
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

}