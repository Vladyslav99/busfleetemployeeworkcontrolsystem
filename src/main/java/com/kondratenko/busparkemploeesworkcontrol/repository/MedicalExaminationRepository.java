package com.kondratenko.busparkemploeesworkcontrol.repository;

import com.kondratenko.busparkemploeesworkcontrol.entity.MedicalExaminationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalExaminationRepository extends JpaRepository<MedicalExaminationLog, Long> {
}
