package com.example.healthcare_relationswithsecuirty;

import com.example.healthcare_relationswithsecuirty.Model.Bill;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import com.example.healthcare_relationswithsecuirty.Repository.BillRepository;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import com.example.healthcare_relationswithsecuirty.Service.BillService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {


    @InjectMocks
    BillService billService;

    @Mock
    BillRepository billRepository;
    @Mock
    AuthRepository authRepository;
    @Mock
    PatientRepository patientRepository;


    User user;
    User user2;
    Bill bill;
    Patient patient;
    List<Bill> billList;

    @BeforeEach
    void setUp() {
        user = new User(null, "Reham","123","ADMIN",null,null);
        bill = new Bill(null ,230, null);
        user2 = new User(2 , "Sara_1" , "123", "PATIENT",null,null);
        patient = new Patient(user2.getId(), "Sara","0509785107",20,12000,false,null,null,null,user2);
    }

    @Test
    public void getAllBillsTest(){
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        when(billRepository.findAll()).thenReturn(billList);

        List<Bill> checkBills = billService.getAllBill(user.getId());
        Assertions.assertEquals(checkBills,billList);

        verify(billRepository, times(1)).findAll();
        verify(authRepository,times(1)).findUserById(user.getId());
    }
    @Test
    public void updateBillTest(){
       when(authRepository.findUserById(user.getId())).thenReturn(user);
       when(billRepository.findBillById(bill.getId())).thenReturn(bill);
       bill.setBillprice(1299);
       billService.updateBill(user.getId(), bill.getId(), bill);
       verify(billRepository, times(1)).save(bill);
       verify(billRepository, times(1)).findBillById(bill.getId());
       verify(authRepository,times(1)).findUserById(user.getId());
    }

    @Test
    public void deleteBill(){
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        when(billRepository.findBillById(bill.getId())).thenReturn(bill);
        billService.deleteBill(user.getId(), bill.getId());

        verify(billRepository, times(1)).findBillById(bill.getId());
        verify(authRepository,times(1)).findUserById(user.getId());
    }

}
