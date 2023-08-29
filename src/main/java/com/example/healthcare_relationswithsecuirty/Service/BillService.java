package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.Model.Bill;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import com.example.healthcare_relationswithsecuirty.Repository.BillRepository;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final PatientRepository patientRepository;
    private final AuthRepository authRepository;

    public List<Bill> getAllBill(Integer user_id){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        return billRepository.findAll();
    }

    public void addBill(Integer user_id,Integer patient_id,Bill bill){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        Patient patient = patientRepository.findPatientById(patient_id);
        if (patient == null)
            throw new ApiException("Sorry , the patient id is wrong");

        bill.setPatient(patient);
        billRepository.save(bill);
    }

    public void updateBill(Integer user_id,Integer id, Bill bill){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");


        Bill oldBill = billRepository.findBillById(id);

        if (oldBill == null)
            throw new ApiException("Sorry, bill id is wrong");


        oldBill.setBillprice(bill.getBillprice());

        billRepository.save(oldBill);
    }



    public void deleteBill(Integer user_id,Integer id){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        Bill deleteBill = billRepository.findBillById(id);

        if (deleteBill == null)
            throw new ApiException("Sorry, bill id is wrong");

        deleteBill.setPatient(null);
        billRepository.delete(deleteBill);
    }


    public Integer calculateBill(Integer user_id,Integer billId){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        Bill bill = billRepository.findBillById(billId);
        if (bill == null)
            throw new ApiException("bill id is wrong");



        // calculate the bill for patient:
        Integer billResult = bill.getPatient().getBalance() - bill.getBillprice() ;

        if (billResult < 0)
            throw new ApiException("The balance of patient not sufficient");

        bill.getPatient().setBalance(billResult);
        patientRepository.save(bill.getPatient());

        return bill.getPatient().getBalance();
    }


    // discount the bill for children where age < 18
    public Integer DiscountBill(Integer user_id,Integer billId){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("ADMIN")))
            throw new ApiException("Sorry, only admin can see this page");

        Bill bill = billRepository.findBillById(billId);
        if (bill == null)
            throw new ApiException("Sorry the bill id is wrong");

        Patient patient = patientRepository.discountBillPatient(bill.getPatient().getId());

        if (patient == null)
            throw new ApiException("Sorry, the discount only for the patient where age < 18");
        Integer billDiscount = (int) (bill.getBillprice() - (bill.getBillprice() * 0.15));

        bill.setBillprice(billDiscount);

        billRepository.save(bill);

        return billDiscount;
    }


    // no longer needed

    public void assignBillToPatient(Integer bill_id , Integer patient_id){
        Bill bill = billRepository.findBillById(bill_id);
        Patient patient = patientRepository.findPatientById(patient_id);

        if (bill == null || patient == null)
            throw new ApiException("Sorry the bill id or patient id is wrong");
    }
}
