package com.newzarc.NewzarcServer.repository;

import com.newzarc.NewzarcServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
