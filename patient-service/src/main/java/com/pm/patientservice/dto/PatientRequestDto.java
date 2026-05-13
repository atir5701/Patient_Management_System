package com.pm.patientservice.dto;

import jakarta.validation.constraints.NotBlank;

public class PatientRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    private String RegisterDate;

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public String getRegisterDate() {return RegisterDate;}

    public void setRegisterDate(String registerDate) {RegisterDate = registerDate;}

}
