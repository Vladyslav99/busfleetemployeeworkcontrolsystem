package com.kondratenko.busparkemploeesworkcontrol.service.impl;

import com.kondratenko.busparkemploeesworkcontrol.dto.BusDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.Bus;
import com.kondratenko.busparkemploeesworkcontrol.entity.ComfortClass;
import com.kondratenko.busparkemploeesworkcontrol.entity.Fuel;
import com.kondratenko.busparkemploeesworkcontrol.exception.EntityNotFoundException;
import com.kondratenko.busparkemploeesworkcontrol.repository.BusRepository;
import com.kondratenko.busparkemploeesworkcontrol.repository.ComfortClassRepository;
import com.kondratenko.busparkemploeesworkcontrol.repository.FuelRepository;
import com.kondratenko.busparkemploeesworkcontrol.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultBusService implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private FuelRepository fuelRepository;

    @Autowired
    private ComfortClassRepository comfortClassRepository;

    @Override
    public Optional<Bus> findById(Long id) {
        return busRepository.findById(id);
    }

    @Override
    @Transactional
    public Bus save(BusDTO busDTO) {

        Fuel fuel = fuelRepository.findById(busDTO.getFuelId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s entity with id %s was not found", Fuel.class, busDTO.getFuelId())));

        ComfortClass comfortClass = comfortClassRepository.findById(busDTO.getComfortClassId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s entity with id %s was not found", ComfortClass.class, busDTO.getComfortClassId())));

        return busRepository.save(Bus.builder()
                .id(busDTO.getId())
                .name(busDTO.getName())
                .registrationNumber(busDTO.getRegistrationNumber())
                .seatAmount(busDTO.getSeatAmount())
                .fuelTankVolume(busDTO.getFuelTankVolume())
                .fuelConsumption(busDTO.getFuelConsumption())
                .mileage(busDTO.getMileage())
                .serviceRequiredAfterMileage(busDTO.getServiceRequiredAfterMileage())
                .fuel(fuel)
                .comfortClass(comfortClass)
                .build());
    }

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }
}
