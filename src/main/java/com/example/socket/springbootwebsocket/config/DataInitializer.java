package com.example.socket.springbootwebsocket.config;

import com.example.socket.springbootwebsocket.entity.Role;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@score360.com");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }
            if (!userRepository.existsByUsername("fan1")) {
                User fan = new User();
                fan.setUsername("fan1");
                fan.setPassword(passwordEncoder.encode("fan123"));
                fan.setEmail("fan1@example.com");
                fan.setRole(Role.SUBSCRIBER);
                userRepository.save(fan);
            }
        };
    }
}
