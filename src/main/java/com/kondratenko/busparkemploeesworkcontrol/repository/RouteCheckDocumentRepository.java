package com.kondratenko.busparkemploeesworkcontrol.repository;

import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.entity.RouteCheckDocument;
import com.kondratenko.busparkemploeesworkcontrol.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteCheckDocumentRepository extends JpaRepository<RouteCheckDocument, Long> {
    List<RouteCheckDocument> findAllByDriverAndAndTripStatus(CustomUser driver, RouteCheckDocument.TripStatus tripStatus);

    List<RouteCheckDocument> findAllByScheduleIn(List<Schedule> schedules);
}
