package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import com.example.healthcare_relationswithsecuirty.Repository.DoctorRepository;
import com.example.healthcare_relationswithsecuirty.Service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository;
    @Mock
    AuthRepository authRepository;

    Doctor doctor;
    User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "Reham", "123", "DOCTOR",null ,null);
        doctor = new Doctor(user.getId() , "RehamS", "general","0552525293",12000.2,null,user);
    }

    @Test
    public void getDoctorTest(){
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        when(doctorRepository.findDoctorById(doctor.getId())).thenReturn(doctor);
        Doctor checkDoctor= doctorService.getAllDoctor(user.getId());
        Assertions.assertEquals(checkDoctor,doctor);
        verify(doctorRepository , times(1)).findDoctorById(doctor.getId());
        verify(authRepository,times(1)).findUserById(user.getId());
    }

    @Test
    public void deleteDoctorTest(){
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        when(doctorRepository.findDoctorById(doctor.getId())).thenReturn(doctor);
        doctorService.deleteDoctor(user.getId(), doctor.getId());

        verify(doctorRepository,times(1)).delete(doctor);
        verify(doctorRepository , times(1)).findDoctorById(doctor.getId());
        verify(authRepository,times(1)).findUserById(user.getId());
    }
    @Test
    public void doctorsAverageSalaryTest(){
        Double avg = 0.0;
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        when(doctorRepository.doctorsAverageSalary()).thenReturn(12000.2);

        avg = doctorService.doctorsAverageSalary(user.getId());
        Assertions.assertEquals(avg, 12000.2);

        verify(doctorRepository,times(1)).doctorsAverageSalary();
        verify(authRepository, times(1)).findUserById(user.getId());
    }
    }
