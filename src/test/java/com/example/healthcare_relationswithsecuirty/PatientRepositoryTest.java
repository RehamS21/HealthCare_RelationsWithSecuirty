package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.DTO.PatientDTO;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    AuthRepository authRepository;
    User user;
    PatientDTO patientDTO;
    Patient patient;

    @BeforeEach
    void setUp() {
        user = new User(null , "Reham","123","PATIENT", null , null );
        patient = new Patient(user.getId(), "Reham","0552525295",23,20000,false,null,null,null,user);
        patientRepository.save(patient);
    }

    @Test
    public void findPatientById(){
        Patient checkPatient = patientRepository.findPatientById(patient.getId());
        Assertions.assertThat(checkPatient).isEqualTo(patient);
    }
}
