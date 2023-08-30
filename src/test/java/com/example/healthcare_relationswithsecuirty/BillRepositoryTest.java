package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.DTO.PatientDTO;
import com.example.healthcare_relationswithsecuirty.Model.*;
import com.example.healthcare_relationswithsecuirty.Repository.BillRepository;
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
public class BillRepositoryTest {
    @Autowired
    BillRepository billRepository;

    User user1;
    User user2;
    Doctor doctor;
    PatientDTO patientDTO;
    Patient patient;

    Room room;

    Bill bill;

    @BeforeEach
    void setUp() {
        bill = new Bill(null , 200 , null);
        billRepository.save(bill);
    }
    @Test
    public void findBillById(){
        Bill checkBill = billRepository.findBillById(bill.getId());

        Assertions.assertThat(checkBill).isEqualTo(bill);
    }
}
