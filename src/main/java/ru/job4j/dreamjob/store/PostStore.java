package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Service
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger(4);


    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Junior",
                LocalDate.of(2013, Month.AUGUST, 20), new City(1, "Москва")));
        posts.put(2, new Post(2, "Middle Java Job", "Middle",
                LocalDate.of(2018, Month.DECEMBER, 4), new City(2, "Уфа")));
        posts.put(3, new Post(3, "Senior Java Job", "Senior",
                LocalDate.of(2015, Month.JANUARY, 8), new City(3, "Ижевск")));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post add(Post post) {
        post.setId(ids.incrementAndGet());
        posts.put(post.getId(), post);
        return post;
    }

    public Post findById(int id) {
       Optional<Post> post = posts.values().stream().filter(p -> p.getId() == id).findAny();
       return post.isEmpty() ? null : post.get();
    }

    public Post updatePost(Post post) {
        Post p = findById(post.getId());
        if (p != null) {
            p.setName(post.getName());
            p.setDescription(post.getDescription());
            p.setCreated(LocalDate.now());
        }
        return p;
    }
}
