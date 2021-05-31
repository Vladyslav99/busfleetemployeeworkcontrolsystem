package com.kondratenko.busparkemploeesworkcontrol.repository;

import com.kondratenko.busparkemploeesworkcontrol.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
