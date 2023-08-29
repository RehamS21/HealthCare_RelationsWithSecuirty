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

    @Query("select d from Doctor d where d.position =?1")
    List<Doctor> findDoctorByPosition(String position);

    @Query("select max (d.salary) from Doctor d ")
    Double getDoctorWithHigherSalary();

    @Query("select d from Doctor d where d.position = ?1 order by d.salary desc ")
    List<Doctor> orderSalaryByPosition(String position);

    @Query("select d from Doctor d where d.id = ?1 and d.salary > 30000")
    Doctor dudcationDoctorSalary(Integer id);

    @Query("select AVG(d.salary) from Doctor d")
    Double doctorsAverageSalary();


}
