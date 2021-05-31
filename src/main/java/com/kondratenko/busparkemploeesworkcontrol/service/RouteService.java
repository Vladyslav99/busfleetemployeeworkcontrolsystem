package com.kondratenko.busparkemploeesworkcontrol.service;

import com.kondratenko.busparkemploeesworkcontrol.dto.RouteDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.Route;

import java.util.Optional;

public interface RouteService extends Service<RouteDTO, Route> {
    Optional<Route> findByName(String departurePoint, String arrivalPoint);
}
