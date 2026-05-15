package com.pm.patientservice.kafkaCommunication;

import com.pm.patientservice.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent event = PatientEvent.newBuilder().setEmail(patient.getEmail())
                .setName(patient.getName()).setId(patient.getId().toString()).build();
        try{
            this.kafkaTemplate.send("patient",event.toByteArray());
            System.out.println("Event Send Successfully");
        }catch (Exception e){
            System.out.println("Error occurred when sending event");
        }
    }
}
