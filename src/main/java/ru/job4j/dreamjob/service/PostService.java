package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.List;
import java.util.Optional;

public class PostService {
    private static final PostStore STORE = PostStore.instOf();
    private static final  PostService SERVICE = new PostService();

    private PostService() {

    }

    public static PostService inst() {
        return SERVICE;
    }

    public List<Post> findAll() {
        return (List<Post>) STORE.findAll();
    }

    public Post add(Post post) {
       return STORE.add(post);
    }

    public Post findById(int id) {
       return STORE.findById(id);
    }

    public Post updatePost(Post post) {
        return STORE.updatePost(post);
    }

}
