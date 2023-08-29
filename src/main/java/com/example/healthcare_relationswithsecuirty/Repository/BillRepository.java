package com.example.healthcare_relationswithsecuirty.Repository;

import com.example.healthcare_relationswithsecuirty.Model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

    Bill findBillById(Integer id);


}
