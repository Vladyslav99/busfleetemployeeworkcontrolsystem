package com.kondratenko.busparkemploeesworkcontrol.service.impl;

import com.kondratenko.busparkemploeesworkcontrol.dto.FuelDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.Fuel;
import com.kondratenko.busparkemploeesworkcontrol.repository.FuelRepository;
import com.kondratenko.busparkemploeesworkcontrol.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultFuelService implements FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    @Override
    public Fuel save(FuelDTO fuelDTO) {
        return fuelRepository.save(Fuel.builder()
                .price(fuelDTO.getPrice())
                .fuelType(fuelDTO.getFuelType())
                .build());
    }

    @Override
    public Optional<Fuel> findById(Long id) {
        return fuelRepository.findById(id);
    }

    @Override
    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }
}
