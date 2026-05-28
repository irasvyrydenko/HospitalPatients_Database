package org.example.app.patient;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    /**
     * Retrieves a detailed summary of a patient's medical activity,
     * joining patient details with surgery and stay records.
     */
    @Query("""
        SELECT 
            p.patient_id, 
            p.first_name, 
            p.last_name,
            COUNT(s.surgery_id) AS total_surgeries,
            MAX(s.start_time) AS last_surgery_date,
            COUNT(h.hospital_stay_id) AS total_hospital_stays
        FROM patient p
        LEFT JOIN surgery s ON p.patient_id = s.patient_id
        LEFT JOIN hospital_stay h ON p.patient_id = h.patient_id
        WHERE p.patient_id = :patientId
        GROUP BY p.patient_id, p.first_name, p.last_name
        """)
    Optional<PatientMedicalSummary> getPatientMedicalSummary(@Param("patientId") Long patientId);

    /**
     * Finds all patients who have undergone a specific type of surgery.
     */
    @Query("""
        SELECT p.* FROM patient p
        JOIN surgery s ON p.patient_id = s.patient_id
        WHERE s.procedure_type LIKE CONCAT('%', :procedure, '%')
        """)
    List<Patient> findBySurgeryType(@Param("procedure") String procedure);

    // Record to hold the complex joined data
    record PatientMedicalSummary(
            Long patientId,
            String firstName,
            String lastName,
            Long totalSurgeries,
            java.time.LocalDateTime lastSurgeryDate,
            Long totalHospitalStays
    ) {}
}
