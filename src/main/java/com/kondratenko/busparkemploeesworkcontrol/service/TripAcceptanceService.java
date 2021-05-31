package com.kondratenko.busparkemploeesworkcontrol.service;

import com.kondratenko.busparkemploeesworkcontrol.dto.TripAcceptanceForm;
import com.kondratenko.busparkemploeesworkcontrol.entity.TripAcceptanceLog;

public interface TripAcceptanceService {
    TripAcceptanceLog save(TripAcceptanceForm tripAcceptanceForm, TripAcceptanceLog.TripAcceptanceStatus tripAcceptanceStatus);
}
