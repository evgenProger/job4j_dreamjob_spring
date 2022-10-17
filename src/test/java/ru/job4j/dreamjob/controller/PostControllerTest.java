package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(3, "New Post"),
                new Post(4, "New Post")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertEquals(page, "posts");
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(5, "Test");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenAddPost() {
        User user = new User();
        List<City> cities = Arrays.asList(
                new City(2, "Aktobe"),
                new City(4, "Ufa")
        );
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        when(cityService.findAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "addPost");
    }

    @Test
    public void whenUpdatePost() {
        User user = new User();
        Post newPost = new Post(8, "NewPost");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        Model model = mock(Model.class);
        when(postService.updatePost(newPost)).thenReturn(true);
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(newPost);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    public void whenFormUpdatePost() {
        User user = new User();
        Post post = new Post(25, "Test");

        PostService postService = mock(PostService.class);
        postService.add(post);
        HttpSession session = mock(HttpSession.class);
        CityService cityService = mock(CityService.class);
        Model model = mock(Model.class);
        when(postService.findById(post.getId())).thenReturn(post);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, post.getId(), session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", post);
        assertEquals(page, "updatePost");
    }
}