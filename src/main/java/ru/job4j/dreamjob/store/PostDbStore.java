package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class PostDbStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(Post.class.getName());


    public PostDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from cities, post where cities.id = post.city_id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("post.id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("created", Timestamp.class).toLocalDateTime().toLocalDate(),
                            new City(it.getInt("cities.id"), it.getString("cities.name"))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }


    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void update(Post post) {

    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM cities, post where cities.id = post.city_id"
                     + "and post.id = ?"
             )
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("post.id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("created", Timestamp.class).toLocalDateTime().toLocalDate(),
                            new City(it.getInt("cities.id"), it.getString("cities.name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePost(Post post) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post set name = ?,"
                     + "description = ?,"
                     + "created = now(),"
                     + "city_id = ?"
                     + " where id = ? ")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setInt(4, post.getId());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return result;
    }
}
