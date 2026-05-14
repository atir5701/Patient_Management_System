package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients(){
        List<PatientResponseDto> patientResponseDTOS = patientService.getAllPatients();
        return ResponseEntity.ok().body(patientResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto responseDto = patientService.addPatient(patientRequestDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@RequestBody PatientRequestDto patientRequestDto,
                                                            @PathVariable UUID id){
        PatientResponseDto responseDto = patientService.updatePatient(id,patientRequestDto);
        if(responseDto==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        boolean check = patientService.deletePatient(id);
        if(check){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
