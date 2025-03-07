package com.acf.trainingserv.service;

import com.acf.trainingserv.model.Passkey;
import com.acf.trainingserv.model.User;
import com.acf.trainingserv.repository.PasskeyRepository;
import com.acf.trainingserv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private PasskeyRepository passkeyRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(User user) {
        // Fetch stored passkey
//        Optional<Passkey> storedPasskey = passkeyRepository.findById(1L);
//        if (storedPasskey.isEmpty()) {
//            return "Passkey not set. Contact admin.";
//        }
//
//        // Verify entered passkey
//        boolean isPasskeyValid = passwordEncoder.matches(user.getPassKey(), storedPasskey.get().getHashedKey());
//        if (!isPasskeyValid) {
//            return "Invalid passkey.";
//        }

        // Hash and save password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully.";
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

