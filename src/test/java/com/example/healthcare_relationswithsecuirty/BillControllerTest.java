package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.Controller.BillController;
import com.example.healthcare_relationswithsecuirty.Model.Bill;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Service.BillService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BillController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class BillControllerTest {
    @MockBean
    BillService billService;

    @Autowired
    MockMvc mockMvc;

    User user;
    Patient patient;
    Bill bill;

    List<Bill> bills;

    @BeforeEach
    void setUp() {
        user = new User(null , "RehamS1" , "123","ADMIN", null , null);
        patient = new Patient(user.getId() , "Reham", "0509705187",23,23459,false,null,null,null,user);
        bill = new Bill(1,234,patient);

        bills = Arrays.asList(bill);
    }

    @Test
    public void getBillTest() throws Exception{
        Mockito.when(billService.getAllBill(user.getId())).thenReturn(bills);
        mockMvc.perform(get("/api/v1/bill/get", user.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].billprice").value(234));
    }
    @Test
    public void updateBlogTest() throws Exception{
      bill.setBillprice(352);
        mockMvc.perform(put("/api/v1/bill/update/{id}",bill.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(bill)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBill() throws Exception{
        mockMvc.perform(delete("/api/v1/bill/delete/{id}",bill.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void discountBillTest() throws Exception{
        mockMvc.perform(put("/api/v1/bill/discount/{id}",bill.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(bill)))
                .andExpect(status().isOk());
    }



}
