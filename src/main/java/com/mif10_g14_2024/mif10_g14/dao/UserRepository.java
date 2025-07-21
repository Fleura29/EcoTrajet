package com.mif10_g14_2024.mif10_g14.dao;

import org.springframework.data.repository.CrudRepository;


/*@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // Add test users
        users.add(new User(1L, "user1", "password1", UserType.UTILISATEUR));
        users.add(new User(2L, "admin", "adminpass", UserType.ADMIN));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public User save(User user) {
        user.setId((long) (users.size() + 1)); // Simulated auto-increment
        users.add(user);
        return user;
    }
}*/

import com.mif10_g14_2024.mif10_g14.entity.User;
import java.util.Optional;


// Auto-implémenté par Spring pour créer une connexion avec la BDD
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login); 
}