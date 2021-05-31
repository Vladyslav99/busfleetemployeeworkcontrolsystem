package com.kondratenko.busparkemploeesworkcontrol.service;

import com.kondratenko.busparkemploeesworkcontrol.dto.CustomUserDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;

import java.util.List;
import java.util.Optional;

public interface CustomUserDetailsService extends Service<CustomUserDTO, CustomUser>{
    CustomUser save(CustomUserDTO customUserDTO);

    Optional<CustomUser> findByEmail(String email);

    List<CustomUser> findAllByRole(CustomUser.CustomUserRole role);

    CustomUser findCurrentSessionDriver();
}
