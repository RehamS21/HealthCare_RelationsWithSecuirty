package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.Controller.DoctorController;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DoctorController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class DoctorControllerTest {
    @MockBean
    DoctorService doctorService;

    @Autowired
    MockMvc mockMvc;

    User user;
    Doctor doctor;


    @BeforeEach
    void setUp() {
        user = new User(null, "Reham","123","Doctor",null,null);
        doctor  =new Doctor(user.getId() , "RehamS","general","0509795187",23090.5,null,user);
    }

    @Test
    public void GetDoctorTest() throws Exception {
        Mockito.when(doctorService.getAllDoctor(user.getId())).thenReturn(doctor);
        mockMvc.perform(get("/api/v1/doctor/get",user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void addBlogTest() throws Exception{
        mockMvc.perform(post("/api/v1/doctor/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(doctor)))
                .andExpect(status().isOk());
    }

}
