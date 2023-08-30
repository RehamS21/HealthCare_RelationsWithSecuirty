package com.example.healthcare_relationswithsecuirty.Repository;

import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findDoctorById(Integer id);

    @Query("select d from Doctor d where d.id = ?1 and d.salary > 30000.0")
    Doctor dudcationDoctorSalary(Integer id);

    @Query("select AVG(d.salary) from Doctor d")
    Double doctorsAverageSalary();



}
