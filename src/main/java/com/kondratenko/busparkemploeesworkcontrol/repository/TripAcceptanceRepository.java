package com.kondratenko.busparkemploeesworkcontrol.repository;

import com.kondratenko.busparkemploeesworkcontrol.entity.TripAcceptanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripAcceptanceRepository extends JpaRepository<TripAcceptanceLog, Long> {
}
