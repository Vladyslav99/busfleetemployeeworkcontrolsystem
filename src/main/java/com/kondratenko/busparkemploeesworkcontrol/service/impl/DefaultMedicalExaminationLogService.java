package com.kondratenko.busparkemploeesworkcontrol.service.impl;

import com.kondratenko.busparkemploeesworkcontrol.dto.MedicalExaminationLogDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.Bus;
import com.kondratenko.busparkemploeesworkcontrol.entity.CustomUser;
import com.kondratenko.busparkemploeesworkcontrol.entity.MedicalExaminationLog;
import com.kondratenko.busparkemploeesworkcontrol.exception.EntityNotFoundException;
import com.kondratenko.busparkemploeesworkcontrol.repository.MedicalExaminationRepository;
import com.kondratenko.busparkemploeesworkcontrol.service.CustomUserDetailsService;
import com.kondratenko.busparkemploeesworkcontrol.service.MedicalExaminationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultMedicalExaminationLogService implements MedicalExaminationLogService {

    @Autowired
    private MedicalExaminationRepository medicalExaminationRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public MedicalExaminationLog save(MedicalExaminationLogDTO medicalExaminationLogDTO) {
        CustomUser driver = customUserDetailsService.findById(medicalExaminationLogDTO.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("%s entity with id %s was not found",
                        CustomUser.class, medicalExaminationLogDTO.getDriverId())));

        return medicalExaminationRepository.save(MedicalExaminationLog.builder()
                .driver(driver)
                .examinationDate(medicalExaminationLogDTO.getExaminationDate())
                .medicalExaminationStatus(MedicalExaminationLog.MedicalExaminationStatus.forName(medicalExaminationLogDTO.getStatus()))
                .build());
    }

    @Override
    public Optional<MedicalExaminationLog> findById(Long id) {
        return medicalExaminationRepository.findById(id);
    }

    @Override
    public List<MedicalExaminationLog> findAll() {
        return medicalExaminationRepository.findAll();
    }
}
