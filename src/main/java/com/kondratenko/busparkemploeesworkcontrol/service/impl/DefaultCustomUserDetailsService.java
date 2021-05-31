package com.kondratenko.busparkemploeesworkcontrol.service.impl;

import com.kondratenko.busparkemploeesworkcontrol.dto.CustomUserDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.exception.UserAlreadyExistsException;
import com.kondratenko.busparkemploeesworkcontrol.repository.CustomUserRepository;
import com.kondratenko.busparkemploeesworkcontrol.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DefaultCustomUserDetailsService implements CustomUserDetailsService, UserDetailsService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public CustomUser save(CustomUserDTO customUserDTO) {

        findByEmail(customUserDTO.getEmail()).ifPresent(a -> {
            throw new UserAlreadyExistsException(
                    String.format("User with email %s already exists", customUserDTO.getEmail()));
        });

        if (customUserDTO.getEmail().equals("1999vlad1999kondratenko@gmail.com")) {
            return customUserRepository.save(CustomUser.builder()
                    .fullName(customUserDTO.getFullName())
                    .email(customUserDTO.getEmail())
                    .password(passwordEncoder.encode(customUserDTO.getPassword()))
                    .role(CustomUser.CustomUserRole.DISPATCHER)
                    .build());
        }

        return customUserRepository.save(CustomUser.builder()
                .fullName(customUserDTO.getFullName())
                .email(customUserDTO.getEmail())
                .password(passwordEncoder.encode(customUserDTO.getPassword()))
                .role(CustomUser.CustomUserRole.DRIVER)
                .build());
    }

    @Override
    public Optional<CustomUser> findByEmail(String email) {
        return customUserRepository.findByEmail(email);
    }

    @Override
    public List<CustomUser> findAllByRole(CustomUser.CustomUserRole role) {
        return customUserRepository.findAllByRole(role);
    }

    @Override
    public Optional<CustomUser> findById(Long id) {
        return customUserRepository.findById(id);
    }

    @Override
    public List<CustomUser> findAll() {
        return customUserRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = customUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(customUser.getRole().name()));

        return new org.springframework.security.core.userdetails.User(customUser.getEmail(),
                customUser.getPassword(), grantedAuthorities);
    }

    public CustomUser findCurrentSessionDriver() {
        String email = getCurrentSessionUserEmail();
        return customUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }

    private String getCurrentSessionUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
    }
}
