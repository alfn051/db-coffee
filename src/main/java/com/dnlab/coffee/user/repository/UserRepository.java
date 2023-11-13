package com.dnlab.coffee.user.repository;

import com.dnlab.coffee.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);

    public Optional<User> findByUsername(String username);

    public List<User> findAllByActive(boolean active);

    public List<User> findAllByAdmin(boolean admin);
}