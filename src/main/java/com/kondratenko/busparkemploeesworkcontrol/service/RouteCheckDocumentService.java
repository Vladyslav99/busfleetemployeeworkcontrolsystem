package com.kondratenko.busparkemploeesworkcontrol.service;

import com.kondratenko.busparkemploeesworkcontrol.dto.RouteCheckDocumentDTO;
import com.kondratenko.busparkemploeesworkcontrol.dto.RouteCheckDocumentUpdateDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.entity.Route;
import com.kondratenko.busparkemploeesworkcontrol.entity.RouteCheckDocument;
import com.kondratenko.busparkemploeesworkcontrol.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface RouteCheckDocumentService extends Service<RouteCheckDocumentDTO, RouteCheckDocument>{
    RouteCheckDocument update(RouteCheckDocumentUpdateDTO routeCheckDocumentUpdateDTO);

    List<RouteCheckDocument> findAllByDriverAndTripStatus(CustomUser driver, RouteCheckDocument.TripStatus tripStatus);

    List<RouteCheckDocument> findAllByDateAndRoute(LocalDateTime departureDateTime, Route route);

    List<RouteCheckDocument> findAllByScheduleIn(List<Schedule> schedules);
}
