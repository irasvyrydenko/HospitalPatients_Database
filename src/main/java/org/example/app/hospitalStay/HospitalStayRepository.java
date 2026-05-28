package org.example.app.hospitalStay;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface HospitalStayRepository extends CrudRepository<HospitalStay, Long> {
    // Parameter name updated to match the Record field exactly
    List<HospitalStay> findByPatientId(Long patientId);
}