package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDbStore;

@Service
@ThreadSafe
public class UserService {
    private final UserDbStore userDbStore;

    public UserService(UserDbStore userDbStore) {
        this.userDbStore = userDbStore;
    }

    public User findUserByEmail(String email) {
        return userDbStore.findUserByEmail(email);
    }
}
