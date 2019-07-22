package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.Role;
import eus.jarriaga.cycling.business.entities.User;
import eus.jarriaga.cycling.business.exceptions.RepeatedEmailException;
import eus.jarriaga.cycling.business.exceptions.RoleNotFoundException;
import eus.jarriaga.cycling.business.exceptions.UserNotFoundException;
import eus.jarriaga.cycling.business.repositories.RoleRepository;
import eus.jarriaga.cycling.business.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/users")
    public List<User> getUsers(Principal principal) {
        if (principal != null) {
            logger.info("PRINCIPAL: " + principal.getName());
        }
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User newUser) throws RoleNotFoundException, RepeatedEmailException {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Role userRole = roleRepository.findById(2L).orElseThrow(() -> new RoleNotFoundException(2L));
        newUser.setRole(userRole);
        if (userRepository.countUsersByEmail(newUser.getEmail()) > 0) throw new RepeatedEmailException(newUser.getEmail());
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id)
            throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        Optional.ofNullable(newUser.getName()).ifPresent((name) -> user.setName(name));
        Optional.ofNullable(newUser.getEmail()).ifPresent((email) -> user.setEmail(email));
        Optional.ofNullable(newUser.getPassword()).ifPresent((password) -> user.setPassword(passwordEncoder.encode(newUser.getPassword())));
        Optional.ofNullable(newUser.isEnabled()).ifPresent((enabled) -> user.setEnabled(enabled));
        Optional.ofNullable(newUser.getLastLoginDate()).ifPresent((lastLoginDate) -> user.setLastLoginDate(lastLoginDate));
        Optional.ofNullable(newUser.getRegistrationDate()).ifPresent((registrationDate) -> user.setRegistrationDate(registrationDate));
        Optional.ofNullable(newUser.getRate()).ifPresent((rate) -> user.setRate(rate));
        //TODO Actualizar roles de usuario
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}