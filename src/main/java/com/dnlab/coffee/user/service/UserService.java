package com.dnlab.coffee.user.service;

import com.dnlab.coffee.user.common.UserLoginRequest;
import com.dnlab.coffee.user.common.UserUpdateRequest;
import com.dnlab.coffee.user.entity.User;
import com.dnlab.coffee.user.repository.UserRepository;
import com.dnlab.coffee.user.common.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User join(UserJoinRequest req) {
        if(checkLoginIdDuplicate(req.getUsername())) return null;
        return userRepository.save(req.toEntity());
    }

    public boolean checkLoginIdDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    public User login(UserLoginRequest req) {
        Optional<User> optionalUser = userRepository.findByUsername(req.getUsername());
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        if (!user.getPassword().equals(req.getPassword())) return null;
        return user;
    }

    public User update(Long userId, UserUpdateRequest req) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        user.setName(req.getName());
        if (req.getPassword() != null) user.setPassword(req.getPassword());
        user.setPhone(req.getPhone());
        userRepository.save(user);
        return user;

    }

    public User withdrawal(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        user.setActive(false);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        if (id == null) return null;
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User getUserByUsername(String username) {
        if (username == null) return null;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByActive(true);
    }

    public List<User> getAllAdmins() {
        return userRepository.findAllByAdmin(true);
    }

    public List<User> getAllWithdrawalUsers() {
        return userRepository.findAllByActive(false);
    }
}
