package com.MediStack.patient_service.service;

import com.MediStack.patient_service.dto.PatientRequestDto;
import com.MediStack.patient_service.dto.PatientResponseDto;
import com.MediStack.patient_service.exception.EmailAlreadyExistsException;
import com.MediStack.patient_service.exception.PatientNotFoundException;
import com.MediStack.patient_service.mapper.PatientMapper;
import com.MediStack.patient_service.model.Patient;
import com.MediStack.patient_service.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDto> patientResponseDtos = patients.stream()
                .map(PatientMapper::toDto).toList();

        return patientResponseDtos;

    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("A Patient with this email already exists"+ patientRequestDto.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDto));
        return PatientMapper.toDto(newPatient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto) {

        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with ID : "+id));
        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A Patient with this email already exists"+ patientRequestDto.getEmail());
        }

        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
