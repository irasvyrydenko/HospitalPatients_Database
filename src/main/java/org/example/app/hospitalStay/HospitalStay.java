package org.example.app.hospitalStay;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("hospital_stay")
public record HospitalStay(
        @Id Long hospitalStayId,
        Long patientId,
        Integer bedNumber,
        LocalDate admissionDate,
        LocalDate dischargeDate,
        Integer roomNumber,
        String diagnosis,
        String anaesthesiaType
) {}
