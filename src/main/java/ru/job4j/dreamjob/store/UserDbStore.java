package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;

import java.sql.*;

@Repository
@ThreadSafe
public class UserDbStore {

    private final String findUserByEmail = "SELECT * FROM  users where email like ?";
    private final String insertUsers = "INSERT INTO users (email, password)"
            + " VALUES (?, ?)";
    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(Post.class.getName());

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(findUserByEmail)
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);

        }
        return null;
    }

    public User add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(insertUsers,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
           LOG.error("Error", e);
        }
        return user;
    }
    private  User createUser(ResultSet it) throws SQLException {
        return new User(it.getInt("id"),
                it.getString("email"),
                it.getString("password"));
    }
}

