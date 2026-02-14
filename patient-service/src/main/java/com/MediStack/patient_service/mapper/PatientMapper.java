package com.MediStack.patient_service.mapper;

import com.MediStack.patient_service.dto.PatientRequestDto;
import com.MediStack.patient_service.dto.PatientResponseDto;
import com.MediStack.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDto toDto(Patient patient) {
        PatientResponseDto patientDto = new PatientResponseDto();
        patientDto.setId(patient.getId().toString());
        patientDto.setName(patient.getName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setAddress(patient.getAddress());
        patientDto.setDataOfBirth(patient.getDateOfBirth().toString());
        return patientDto;
    }

    public static Patient toModel(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getRegisteredDate()));
        return patient;
    }
}
