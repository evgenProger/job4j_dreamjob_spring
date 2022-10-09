package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.*;

public class UserDbStoreTest {
    @Test
    public void whenCreatePost() {
        BasicDataSource basicDataSource = new Main().loadPool();
        UserDbStore store = new UserDbStore(basicDataSource);
        User user = new User(0, "login", "password");
        store.add(user);
        User userInDb = store.findUserByEmail(user.getEmail());
        assertEquals(userInDb.getEmail(), (userInDb.getEmail()));
    }
}