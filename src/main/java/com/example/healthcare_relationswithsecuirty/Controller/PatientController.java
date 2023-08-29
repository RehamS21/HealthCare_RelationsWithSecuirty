package com.example.healthcare_relationswithsecuirty.Controller;

import com.example.healthcare_relationswithsecuirty.Api.ApiResponse;
import com.example.healthcare_relationswithsecuirty.DTO.PatientDTO;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity registerPatient(@RequestBody @Valid User user){
        patientService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("The patient registered successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity getAllPatients(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(patientService.getAllPatient(user.getId()));
    }

    @PostMapping("/add/{doctor_id}")
    public ResponseEntity addNewPatient(@AuthenticationPrincipal User user, @PathVariable Integer doctor_id , @RequestBody @Valid PatientDTO patientDto){
        patientService.addPatient(user.getId(), doctor_id,patientDto);
        return ResponseEntity.status(200).body(new ApiResponse("the patient '"+patientDto.getName() +"' added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePatient(@AuthenticationPrincipal User user,@PathVariable Integer id, @RequestBody @Valid PatientDTO patient){
        patientService.updatePatient(user.getId(), id,patient);
        return ResponseEntity.status(200).body(new ApiResponse("the patient '"+patient.getName() +"' updated info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePatient(@AuthenticationPrincipal User user,@PathVariable Integer id){
        patientService.deletePatient(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("The patient with id '"+id+"' deleted successfully"));
    }

    @PutMapping("/bookingApo/{id}")
    public ResponseEntity bookingAppointment(@AuthenticationPrincipal User user,@PathVariable Integer id){
        patientService.appointmentBooking(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking appointment for patient successfully"));
    }

//    @GetMapping("/getApo")
//    public ResponseEntity getPatientWithAppointment(@AuthenticationPrincipal User user){
//        return ResponseEntity.status(200).body(patientService.getAllPatentWithAppointment());
//    }
//
//    @GetMapping("order")
//    public ResponseEntity orderPatientAsec(@AuthenticationPrincipal User user){
//        return ResponseEntity.status(200).body(patientService.patientsOrdered());
//    }

    @PutMapping("/assign/{room_id}/assign/{patient_id}")
    public ResponseEntity assignRoomToPatientController(@AuthenticationPrincipal User user,@PathVariable Integer room_id, @PathVariable Integer patient_id){
        patientService.assignRoomToPatient(user.getId(), room_id, patient_id);
        return ResponseEntity.status(200).body(new ApiResponse("Assigned room to patient successfully"));
    }


}
