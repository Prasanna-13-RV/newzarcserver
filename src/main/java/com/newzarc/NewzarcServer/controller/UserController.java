package com.newzarc.NewzarcServer.controller;

import com.newzarc.NewzarcServer.model.User;
import com.newzarc.NewzarcServer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/allusers")
    List<User> getAllUsers() {
        return userRepo.findAll();
    }

//    @GetMapping("/user/{id}")
//    Optional<User> getUserById(@PathVariable Long id) {
//        return userRepo.findById(id);
//    }

    @GetMapping("/user/{email}")
    Optional<User> getUserByEmail(@PathVariable String email) {
        return userRepo.findByEmail(email);
    }

    @PostMapping("/user/create")
    User addUsers(@RequestBody User users) {
        return userRepo.save(users);
    }

    @PutMapping("/user/update/{id}")
    User updateUsers(@RequestBody User addUsers, @PathVariable Long id) {
        try {
            return userRepo.findById(id)
                    .map(users -> {
                        users.setName(addUsers.getName());
                        users.setAboutMe(addUsers.getAboutMe());
                        users.setEmail(addUsers.getEmail());
                        users.setUserImage(addUsers.getUserImage());
                        users.setIsSubscribe(addUsers.getIsSubscribe());
                        users.setPhone(addUsers.getPhone());
                        users.setPassword(addUsers.getPassword());
                        return userRepo.save(users);
                    }).orElseThrow(() -> new PrintException("User Update Failed"));
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/user/delete/{id}")
    String deleteUser(@PathVariable Long id) throws PrintException {
        if (!userRepo.existsById(id)) {
            throw new PrintException("User not found");
        };
        userRepo.deleteById(id);
        return "User deleted successfully " + id;
    }

}
