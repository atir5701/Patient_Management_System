package com.pm.analyticsservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {
    @KafkaListener(topics="patient",groupId = "analytics-service")
    public void consumeEvent(byte[] event){
        System.out.println("Event Starting to Consume");
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            System.out.println("Patient Name "+patientEvent.getName());
            System.out.println("Patient Email "+patientEvent.getEmail());
            System.out.println("Patient ID "+patientEvent.getId());
        } catch (Exception e){
            System.out.println("Error occurred while parsing the event");
        }

    }
}
