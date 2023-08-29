package com.example.healthcare_relationswithsecuirty.Controller;

import com.example.healthcare_relationswithsecuirty.Api.ApiResponse;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Service.DoctorService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    @GetMapping("/get")
    public ResponseEntity getAllDoctors(){
        return ResponseEntity.status(200).body(doctorService.getAllDoctor());
    }

    @PostMapping("/add")
    public ResponseEntity addNewDoctor(@RequestBody @Valid Doctor doctor){
        doctorService.addDoctor(doctor);
        return ResponseEntity.status(200).body(new ApiResponse("the doctor added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateDoctor(@PathVariable Integer id, @RequestBody @Valid Doctor doctor){
        doctorService.updateDoctor(id,doctor);
        return ResponseEntity.status(200).body(new ApiResponse("the doctor updated info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDoctor(@PathVariable Integer id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.status(200).body(new ApiResponse("The doctor deleted successfully"));
    }

    @PutMapping("/bouns/{id}")
    public ResponseEntity addBouns(@PathVariable Integer id){
        Double result = doctorService.bounsSalary(id);

        return ResponseEntity.status(200).body(new ApiResponse("The salary after bouns = "+ result));
    }

    @GetMapping("/getposition/{position}")
    public ResponseEntity getAllDoctorPosition(@PathVariable String position){
        List<Doctor> doctors = doctorService.getAllDoctorWithPosition(position);

        return ResponseEntity.status(200).body(doctors);
    }

    @GetMapping("/high")
    public ResponseEntity getHighSalary(){
        Double doctorSalary = doctorService.higherSalary();

        return ResponseEntity.status(200).body(new ApiResponse("The high salary of doctors = "+doctorSalary));
    }

    @PutMapping("/deduction/{id}")
    public ResponseEntity deductionSalary(@PathVariable Integer id){
        Double result = doctorService.deductionSalary(id);
        return ResponseEntity.status(200).body(new ApiResponse("The salary after Insurance deduction = "+result));
    }

    @GetMapping("/order/{position}")
    public ResponseEntity orderDoctorsSalaryByPosition(@PathVariable String position){
        List<Doctor> doctors = doctorService.DoctorOrderdByPostion(position);
        return ResponseEntity.status(200).body(doctors);
    }

    @GetMapping("/avg")
    public ResponseEntity doctorsAvgSalary(){
        return ResponseEntity.status(200).body(new ApiResponse("The doctors average salary ="+doctorService.doctorsAverageSalary()));
    }

}
