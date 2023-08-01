package com.example.sportstore;

import com.example.sportstore.entity.User;
import com.example.sportstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername == null || !userByUsername.getEnabled()) {
            throw new IllegalArgumentException("User not found or user is disable");
        }

        if (passwordEncoder.matches(password, userByUsername.getPassword())) {
            String authority = userByUsername.getAuthority();

            userByUsername.setLastLoginDate(LocalDateTime.now());
            userRepository.save(userByUsername);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else {
            throw new IllegalArgumentException("Password mismatch");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
