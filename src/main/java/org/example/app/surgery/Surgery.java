package org.example.app.surgery;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("surgery")
public record Surgery(
        @Id Long surgeryId,
        Long patientId,
        String procedureType,
        String operationRoom,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}