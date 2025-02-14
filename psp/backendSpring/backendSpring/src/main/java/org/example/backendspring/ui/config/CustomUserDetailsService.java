package org.example.backendspring.ui.config;


import org.example.backendspring.dao.UserEntity;
import org.example.backendspring.dao.RolesEntity;
import org.example.backendspring.dao.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        UserDetails u =  User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(
                        user.getRoles().stream()
                                .map(RolesEntity::getRol)
                                .collect(Collectors.joining(",")))
                .authorities( user.getRoles().stream()
                        .map(RolesEntity::getRol)

                        .collect(Collectors.joining(",")))

                .build();

        return u;



    }
}
