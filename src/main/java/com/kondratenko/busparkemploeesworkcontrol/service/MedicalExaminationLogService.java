package com.kondratenko.busparkemploeesworkcontrol.service;

import com.kondratenko.busparkemploeesworkcontrol.dto.MedicalExaminationLogDTO;
import com.kondratenko.busparkemploeesworkcontrol.entity.MedicalExaminationLog;

public interface MedicalExaminationLogService extends Service<MedicalExaminationLogDTO, MedicalExaminationLog>{
    MedicalExaminationLog save(MedicalExaminationLogDTO medicalExaminationLogDTO);
}
