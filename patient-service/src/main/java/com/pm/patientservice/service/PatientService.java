package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    private PatientResponseDto responseDto(Patient patient) {
        PatientResponseDto dto = new PatientResponseDto();
        dto.setId(patient.getId().toString());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setDateOBirth(patient.getDateOfBirth().toString());
        return dto;
    }

    private Patient requestDto(PatientRequestDto patientRequestDto){
        Patient p = new Patient();
        p.setEmail(patientRequestDto.getEmail());
        p.setName(patientRequestDto.getName());
        p.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        p.setDateOfBirth(LocalDate.parse(patientRequestDto.getRegisterDate()));
        return p;
    }

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getAllPatients(){
        return patientRepository.findAll().stream()
                .map(this::responseDto)
                .collect(Collectors.toList());
    }

    public PatientResponseDto addPatient(PatientRequestDto patientRequestDto){
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            return responseDto(patientRepository.findByEmail(patientRequestDto.getEmail()));
        }
        Patient obj = patientRepository.save(this.requestDto(patientRequestDto));
        return responseDto(obj);
    }
}
