package com.kondratenko.busparkemploeesworkcontrol.service.impl;

import com.kondratenko.busparkemploeesworkcontrol.dto.TripAcceptanceForm;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.entity.Route;
import com.kondratenko.busparkemploeesworkcontrol.entity.RouteCheckDocument;
import com.kondratenko.busparkemploeesworkcontrol.entity.TripAcceptanceLog;
import com.kondratenko.busparkemploeesworkcontrol.exception.EntityNotFoundException;
import com.kondratenko.busparkemploeesworkcontrol.repository.TripAcceptanceRepository;
import com.kondratenko.busparkemploeesworkcontrol.service.CustomUserDetailsService;
import com.kondratenko.busparkemploeesworkcontrol.service.RouteCheckDocumentService;
import com.kondratenko.busparkemploeesworkcontrol.service.TripAcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTripAcceptanceService implements TripAcceptanceService {

    @Autowired
    private TripAcceptanceRepository tripAcceptanceRepository;

    @Autowired
    private RouteCheckDocumentService routeCheckDocumentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public TripAcceptanceLog save(TripAcceptanceForm tripAcceptanceForm, TripAcceptanceLog.TripAcceptanceStatus tripAcceptanceStatus) {
        RouteCheckDocument routeCheckDocument = routeCheckDocumentService.findById(tripAcceptanceForm.getDriverTripId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s entity with id %s was not found", RouteCheckDocument.class, tripAcceptanceForm.getDriverTripId())));


        if (tripAcceptanceStatus.equals(TripAcceptanceLog.TripAcceptanceStatus.ACCEPTED)) {
            routeCheckDocument.setTripStatus(RouteCheckDocument.TripStatus.ACCEPTED_BY_DRIVER);
        }else if (tripAcceptanceStatus.equals(TripAcceptanceLog.TripAcceptanceStatus.DECLINED)) {
            routeCheckDocument.setTripStatus(RouteCheckDocument.TripStatus.REJECTED_BY_DRIVER);
        }

        CustomUser driver = customUserDetailsService.findById(tripAcceptanceForm.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("%s entity with id %s was not found", CustomUser.class, tripAcceptanceForm.getDriverId())));

        return tripAcceptanceRepository.save(TripAcceptanceLog.builder()
                .driver(driver)
                .routeCheckDocument(routeCheckDocument)
                .build());
    }
}
