package com.example.healthcare_relationswithsecuirty.Controller;

import com.example.healthcare_relationswithsecuirty.Api.ApiResponse;
import com.example.healthcare_relationswithsecuirty.DTO.DoctorDTO;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Service.DoctorService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity registerDoctor(@RequestBody @Valid User user){
        doctorService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("The doctor registered successfully"));
    }
    @GetMapping("/get")
    public ResponseEntity getAllDoctors(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(doctorService.getAllDoctor(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addNewDoctor(@AuthenticationPrincipal User user,@RequestBody @Valid DoctorDTO doctor){
        doctorService.addDoctor(user.getId(),doctor);
        return ResponseEntity.status(200).body(new ApiResponse("the doctor added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateDoctor(@AuthenticationPrincipal User user,@PathVariable Integer id, @RequestBody @Valid DoctorDTO doctor){
        doctorService.updateDoctor(user.getId(),id,doctor);
        return ResponseEntity.status(200).body(new ApiResponse("the doctor updated info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDoctor(@AuthenticationPrincipal User user,@PathVariable Integer id){
        doctorService.deleteDoctor(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("The doctor deleted successfully"));
    }

    @PutMapping("/bouns/{id}")
    public ResponseEntity addBouns(@AuthenticationPrincipal User user,@PathVariable Integer id){
        Double result = doctorService.bounsSalary(user.getId(), id);

        return ResponseEntity.status(200).body(new ApiResponse("The salary after bouns = "+ result));
    }


    @PutMapping("/deduction/{id}")
    public ResponseEntity deductionSalary(@AuthenticationPrincipal User user,@PathVariable Integer id){
        Double result = doctorService.deductionSalary(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("The salary after Insurance deduction = "+result));
    }


    @GetMapping("/avg")
    public ResponseEntity doctorsAvgSalary(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(new ApiResponse("The doctors average salary ="+doctorService.doctorsAverageSalary(user.getId())));
    }

}
