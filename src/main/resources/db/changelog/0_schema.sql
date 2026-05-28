CREATE TABLE patient (
                         patient_id INT PRIMARY KEY AUTO_INCREMENT,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         date_of_birth DATE,
                         gender VARCHAR(20),
                         blood_type VARCHAR(5)
);

CREATE TABLE hospital_administration (
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         ha_position VARCHAR(100),
                         PRIMARY KEY (first_name, last_name, ha_position)
);

CREATE TABLE hospital_administration_specialisations (
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         ha_position VARCHAR(100),
                         specialisation VARCHAR(100),
                         PRIMARY KEY (first_name, last_name, ha_position, specialisation),
                         CONSTRAINT fk_ha_specialisation
                             FOREIGN KEY (first_name, last_name, ha_position)
                                 REFERENCES hospital_administration(first_name, last_name, ha_position)
                                 ON DELETE CASCADE
);

CREATE TABLE doctor (
                        first_name VARCHAR(50),
                        last_name VARCHAR(50),
                        doctor_position VARCHAR(100),
                        PRIMARY KEY (first_name, last_name, doctor_position)
);

CREATE TABLE doctor_specialisations (
                        first_name VARCHAR(50),
                        last_name VARCHAR(50),
                        doctor_position VARCHAR(100),
                        specialisation VARCHAR(100),
                        PRIMARY KEY (first_name, last_name, doctor_position, specialisation),
                        CONSTRAINT fk_doctor_specialisation
                            FOREIGN KEY (first_name, last_name, doctor_position)
                                REFERENCES doctor(first_name, last_name, doctor_position)
                                ON DELETE CASCADE
);

CREATE TABLE other_staff (
                     staff_id INT PRIMARY KEY,
                     role_description TEXT,
                     shift_type VARCHAR(50)
);

CREATE TABLE surgery (
                         surgery_id INT PRIMARY KEY AUTO_INCREMENT,
                         patient_id INT, -- Connects surgery to a specific patient
                         procedure_type VARCHAR(100),
                         operation_room VARCHAR(50),
                         start_time DATETIME,
                         end_time DATETIME,
                         CONSTRAINT fk_patient_surgery FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE
);

CREATE TABLE hospital_stay (
                       hospital_stay_id INT PRIMARY KEY AUTO_INCREMENT,
                       patient_id INT,
                       bed_number INT,
                       admission_date DATE,
                       discharge_date DATE,
                       room_number INT,
                       diagnosis TEXT,
                       anaesthesia_type VARCHAR(100),
                       CONSTRAINT fk_patient_stay
                       FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE
);

CREATE TABLE follow_up_appointment (
                       appointment_id INT PRIMARY KEY,
                       appointment_date DATE,
                       progress_notes TEXT
);