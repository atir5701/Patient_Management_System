package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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
        p.setRegisterDate(LocalDate.parse(patientRequestDto.getRegisterDate()));
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

    @Transactional
    public PatientResponseDto addPatient(PatientRequestDto patientRequestDto){
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            return responseDto(patientRepository.findByEmail(patientRequestDto.getEmail()));
        }
        Patient obj = patientRepository.save(this.requestDto(patientRequestDto));
        return responseDto(obj);
    }

    @Transactional
    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto){
        try {
            Patient patient = patientRepository.findById(id).orElseThrow(
                    ()-> new RuntimeException("Patient Not Found")
            );
            patient.setDateOfBirth((LocalDate.parse(patientRequestDto.getDateOfBirth())));
            patient.setName(patientRequestDto.getName());
            patient.setEmail(patientRequestDto.getEmail());
            return this.responseDto(patient);

        }catch (Exception e){
            System.out.println("No Such User Found");
            return null;
        }
    }

    @Transactional
    public boolean deletePatient(UUID id){
        try{
            patientRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Patient Not Found")
            );
            patientRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
