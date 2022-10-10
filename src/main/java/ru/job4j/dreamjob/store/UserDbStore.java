package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class UserDbStore {

    private final String findUserByEmail = "SELECT * FROM  users where email like ?";
    private final String insertUsers = "INSERT INTO users (email, password)"
            + " VALUES (?, ?)";
    private final String selectAll = "SELECT * FROM users";
    private final String findUserByEmailAndPwd = "SELECT * FROM  users where email like ? and password like ?";
    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(Post.class.getName());

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> findUserByEmail(String email) {
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
        return Optional.empty();
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(findUserByEmailAndPwd)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return Optional.empty();
    }

    public Optional<User> add(User user) {
        Optional<User> us = Optional.of(user);
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(insertUsers,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, us.get().getEmail());
            ps.setString(2, us.get().getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    us.get().setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
           LOG.error("Error", e);
        }
        return us;
    }

    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(selectAll)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    if (createUser(it).isPresent()) {
                        users.add(createUser(it).get());
                    }
                }
            }
        } catch (SQLException throwables) {
            LOG.error("Error", throwables);
        }
        return users;
    }

    private  Optional<User> createUser(ResultSet it) throws SQLException {
        return Optional.of(new User(it.getInt("id"),
                it.getString("email"),
                it.getString("password")));
    }
}

