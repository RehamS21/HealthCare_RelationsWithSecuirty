package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.DTO.DoctorDTO;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import com.example.healthcare_relationswithsecuirty.Repository.DoctorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.print.Doc;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DoctorRepositoryTest {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AuthRepository authRepository;
    DoctorDTO doctorDTO;
    Doctor doctor;
    User user;

    @BeforeEach
    void setUp() {
        user = new User(null , "Reham", "123", "DOCTOR", null , null);
        doctor = new Doctor(user.getId(), "RehamS" , "general" ,"0509705187" , 40000.0 , null, user);
        doctorRepository.save(doctor);
    }

    @Test
    public void findDoctorById(){
        Doctor checkDoctor = doctorRepository.findDoctorById(doctor.getId());
        Assertions.assertThat(checkDoctor.getId()).isEqualTo(doctor.getId());
    }

    @Test
    public void dudcationDoctorSalaryTest(){
        Doctor checkDoctor = doctorRepository.dudcationDoctorSalary(doctor.getId());
        Assertions.assertThat(checkDoctor.getId()).isEqualTo(doctor.getId());
    }
}
