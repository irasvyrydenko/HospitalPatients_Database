package org.example.app.patient;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("patient")
public record Patient(
    @Id Long patientId,
    String firstName,
    String lastName,
    String gender,
    LocalDate dateOfBirth,
    String bloodType

) {
    public static Patient of(String firstName, String lastName, String gender, LocalDate dateOfBirth, String bloodType) {
        return new Patient(null, firstName, lastName, gender, dateOfBirth, bloodType);
    }

    public Patient withDetails(String firstName, String lastName, String gender, LocalDate dateOfBirth, String bloodType) {
        return new Patient(patientId, firstName, lastName, gender, dateOfBirth, bloodType);
    }
}
