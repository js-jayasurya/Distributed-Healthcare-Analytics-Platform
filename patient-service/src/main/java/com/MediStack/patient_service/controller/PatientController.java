package com.MediStack.patient_service.controller;

import com.MediStack.patient_service.dto.PatientRequestDto;
import com.MediStack.patient_service.dto.PatientResponseDto;
import com.MediStack.patient_service.dto.validators.CreatePatientValidationGroup;
import com.MediStack.patient_service.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        List<PatientResponseDto> patients = patientService.getPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class }) @RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDto patient = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDto updatedPatient = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable  UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
