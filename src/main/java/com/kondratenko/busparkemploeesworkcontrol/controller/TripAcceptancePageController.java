package com.kondratenko.busparkemploeesworkcontrol.controller;

import com.kondratenko.busparkemploeesworkcontrol.dto.TripAcceptanceForm;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.entity.RouteCheckDocument;
import com.kondratenko.busparkemploeesworkcontrol.entity.TripAcceptanceLog;
import com.kondratenko.busparkemploeesworkcontrol.service.CustomUserDetailsService;
import com.kondratenko.busparkemploeesworkcontrol.service.RouteCheckDocumentService;
import com.kondratenko.busparkemploeesworkcontrol.service.TripAcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL)
public class TripAcceptancePageController {

    private static final String DRIVER_TRIP_LIST = "driverTripList";

    @Autowired
    private RouteCheckDocumentService routeCheckDocumentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TripAcceptanceService tripAcceptanceService;

    @GetMapping(ControllerConstants.Mapping.DRIVER_ASSIGNED_TRIP_LIST)
    public String showDriverAssignedTripRouteListPage(Model model) {
        CustomUser currentSessionDriver = customUserDetailsService.findCurrentSessionDriver();
        List<RouteCheckDocument> driverTripList = routeCheckDocumentService
                .findAllByDriverAndTripStatus(currentSessionDriver, RouteCheckDocument.TripStatus.DRIVER_ACCEPTANCE_WAITING);
        model.addAttribute(DRIVER_TRIP_LIST, driverTripList);
        return "driver-assigned-trip-list";
    }

    @PostMapping(ControllerConstants.Mapping.TRIP_INFO_PREFORMING + "/cancel")
    public String cancelTrip(@RequestBody TripAcceptanceForm tripAcceptanceForm) {
        tripAcceptanceService.save(tripAcceptanceForm, TripAcceptanceLog.TripAcceptanceStatus.DECLINED);
        return ControllerConstants.REDIRECT + ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL +
                ControllerConstants.Mapping.DRIVER_ASSIGNED_TRIP_LIST;
    }

    @GetMapping(ControllerConstants.Mapping.DRIVER_ACCEPTED_TRIP_LIST)
    public String showDriverAcceptedTripRouteListPage(Model model) {
        CustomUser currentSessionDriver = customUserDetailsService.findCurrentSessionDriver();
        List<RouteCheckDocument> driverTripList = routeCheckDocumentService
                .findAllByDriverAndTripStatus(currentSessionDriver, RouteCheckDocument.TripStatus.ACCEPTED_BY_DRIVER);
        model.addAttribute(DRIVER_TRIP_LIST, driverTripList);
        return "driver-accepted-trip-list";
    }

    @PostMapping(ControllerConstants.Mapping.TRIP_INFO_PREFORMING + "/accept")
    public String acceptTrip(@RequestBody TripAcceptanceForm tripAcceptanceForm) {
        tripAcceptanceService.save(tripAcceptanceForm, TripAcceptanceLog.TripAcceptanceStatus.ACCEPTED);
        return ControllerConstants.REDIRECT + ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL +
                ControllerConstants.Mapping.DRIVER_ASSIGNED_TRIP_LIST;
    }

}
