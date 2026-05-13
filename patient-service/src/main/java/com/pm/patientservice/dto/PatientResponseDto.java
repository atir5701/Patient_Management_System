package com.pm.patientservice.dto;

import org.springframework.stereotype.Component;

@Component
public class PatientResponseDto {
    private String id;
    private String name;
    private String email;
    private String dateOBirth;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email;}

    public void setEmail(String email) {this.email = email;}

    public String getDateOBirth() {return dateOBirth;}

    public void setDateOBirth(String dateOBirth) {this.dateOBirth = dateOBirth;}

}
