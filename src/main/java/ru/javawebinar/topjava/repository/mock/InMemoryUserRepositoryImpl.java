package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        repository.computeIfPresent(id, (userId, user) -> repository.remove(userId));
        log.info("delete {}", id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
// check whether the user with such email was already created
        boolean userExists = repository.values().stream().anyMatch(u -> user.getEmail().equals(u.getEmail()));
        if (!userExists){
            log.info("User was successfully saved");
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        else {
            log.warn("user already exists");
            User oldUser = repository.get(user.getId());
            return oldUser;
        }


    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.getOrDefault(id, null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(user -> user.getEmail().equals(email)).findAny().get();
    }
}
