-- Inserting initial patients
INSERT INTO patient (patient_id, first_name, last_name, date_of_birth, gender, blood_type)
VALUES (1, 'Sarah', 'Connor', '1985-04-08', 'Female', 'O-'),
       (2, 'Tom', 'Hardy', '1977-09-15', 'Male', 'A+'),
       (3, 'John', 'Watson', '1996-12-01', 'Male', 'B+');

-- Inserting initial doctors
-- INSERT INTO doctor (firstName, lastName, doctorPosition)
-- VALUES ('Gregory', 'House', 'Head of Diagnostic Medicine'),
--        ('John', 'Watson', 'Surgeon');
--
-- -- Inserting initial surgeries
-- INSERT INTO surgery (SurgeryID, ProcedureType, OperationRoom, startTime, endTime)
-- VALUES (101, 'Appendectomy', 'Room A', '2026-04-23 10:00:00', '2026-04-23 12:00:00');