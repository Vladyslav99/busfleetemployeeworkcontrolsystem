package com.kondratenko.busparkemploeesworkcontrol.repository;

import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    List<CustomUser> findAllByRole(CustomUser.CustomUserRole role);

    Optional<CustomUser> findByEmail(String email);
}
