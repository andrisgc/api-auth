package com.agc.auth.service;

import com.agc.auth.dto.RegisterDTO;
import com.agc.auth.model.User;
import com.agc.auth.model.UserRole;
import com.agc.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // O Spring Security usa este método automaticamente.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public void register(RegisterDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(null, data.email(), encryptedPassword, UserRole.USER);
        userRepository.save(newUser);
    }
}
