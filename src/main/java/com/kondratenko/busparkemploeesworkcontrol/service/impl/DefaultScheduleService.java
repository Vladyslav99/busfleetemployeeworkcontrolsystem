package com.kondratenko.busparkemploeesworkcontrol.service.impl;

import com.kondratenko.busparkemploeesworkcontrol.dto.ScheduleDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.Route;
import com.kondratenko.busparkemploeesworkcontrol.entity.Schedule;
import com.kondratenko.busparkemploeesworkcontrol.exception.EntityNotFoundException;
import com.kondratenko.busparkemploeesworkcontrol.repository.ScheduleRepository;
import com.kondratenko.busparkemploeesworkcontrol.service.RouteService;
import com.kondratenko.busparkemploeesworkcontrol.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultScheduleService implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RouteService routeService;

    @Override
    public Schedule save(ScheduleDTO scheduleDTO) {
        Route route = routeService.findById(scheduleDTO.getRouteId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s entity with id %s was not found", Route.class, scheduleDTO.getRouteId())));

        Schedule schedule = Schedule.builder()
                .departureDateTime(scheduleDTO.getDepartureDateTime())
                .arrivalDateTime(scheduleDTO.getArrivalDateTime())
                .route(route)
                .build();
        return scheduleRepository.save(schedule);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findByArrivalDateTimeAfterAndAndArrivalDateTimeBeforeAndRoute(LocalDateTime from, Route route) {
        from = from.with(LocalTime.MIN);
        LocalDateTime to = from.plusDays(1);
        return scheduleRepository.findByArrivalDateTimeAfterAndAndArrivalDateTimeBeforeAndRouteOrderByDepartureDateTime(from, to, route);
    }
}
