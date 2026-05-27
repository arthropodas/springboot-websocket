package com.example.socket.springbootwebsocket.service;

import com.example.socket.springbootwebsocket.dto.LoginRequest;
import com.example.socket.springbootwebsocket.dto.RegisterRequest;
import com.example.socket.springbootwebsocket.dto.TokenResponse;
import com.example.socket.springbootwebsocket.entity.Role;
import com.example.socket.springbootwebsocket.entity.User;
import com.example.socket.springbootwebsocket.mapper.ApiMapper;
import com.example.socket.springbootwebsocket.repository.UserRepository;
import com.example.socket.springbootwebsocket.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Map<String, Object> register(RegisterRequest request) {
        if (!request.password().equals(request.passwordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email() != null ? request.email() : "");
        user.setRole(Role.SUBSCRIBER);
        userRepository.save(user);
        return Map.of(
                "message", "Registration successful. Login with username and password to get JWT.",
                "user", ApiMapper.userToMap(user)
        );
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return new TokenResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    public Map<String, Object> profile(User user) {
        return ApiMapper.userToMap(user);
    }
}
