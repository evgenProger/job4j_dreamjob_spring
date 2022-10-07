package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostStore store;

    public PostService(PostStore store) {
        this.store = store;
    }

    public List<Post> findAll() {
        return (List<Post>) store.findAll();
    }

    public Post add(Post post) {
       return store.add(post);
    }

    public Post findById(int id) {
       return store.findById(id);
    }

    public Post updatePost(Post post) {
        return store.updatePost(post);
    }

}
