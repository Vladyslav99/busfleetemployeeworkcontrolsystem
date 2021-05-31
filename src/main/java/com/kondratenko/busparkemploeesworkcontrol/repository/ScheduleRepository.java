package com.kondratenko.busparkemploeesworkcontrol.repository;

import com.kondratenko.busparkemploeesworkcontrol.entity.Route;
import com.kondratenko.busparkemploeesworkcontrol.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByArrivalDateTimeAfterAndAndArrivalDateTimeBeforeAndRouteOrderByDepartureDateTime(LocalDateTime from, LocalDateTime to, Route route);
}
