package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    Iterable<User> findAllByEmail(String email);

    Integer countUsersByEmail(String email);

    User findUserByEmail(String email);

    User findUserById(Long id);

    Iterable<User> findAllByEnabledOrderByEmail(boolean enable);

    Iterable<User> findAllByOrderByName();
}
