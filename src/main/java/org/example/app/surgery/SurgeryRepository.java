package org.example.app.surgery;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SurgeryRepository extends CrudRepository<Surgery, Long> {
    List<Surgery> findByPatientId(Long patientId);
}