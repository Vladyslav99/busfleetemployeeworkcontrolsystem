package com.kondratenko.busparkemploeesworkcontrol.service;

import com.kondratenko.busparkemploeesworkcontrol.dto.ScheduleDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.Route;
import com.kondratenko.busparkemploeesworkcontrol.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService extends Service<ScheduleDTO, Schedule> {
    List<Schedule> findByArrivalDateTimeAfterAndAndArrivalDateTimeBeforeAndRoute(LocalDateTime from, Route route);
}
