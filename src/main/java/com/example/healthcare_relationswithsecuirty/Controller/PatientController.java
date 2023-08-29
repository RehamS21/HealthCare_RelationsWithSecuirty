package com.example.healthcare_relationswithsecuirty.Controller;

import com.example.healthcare_relationswithsecuirty.Api.ApiResponse;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/get")
    public ResponseEntity getAllPatients(){
        return ResponseEntity.status(200).body(patientService.getAllPatient());
    }

    @PostMapping("/add/{doctor_id}")
    public ResponseEntity addNewPatient(@PathVariable Integer doctor_id ,@RequestBody @Valid Patient patient){
        patientService.addPatient(doctor_id,patient);
        return ResponseEntity.status(200).body(new ApiResponse("the patient '"+patient.getName() +"' added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePatient(@PathVariable Integer id, @RequestBody @Valid Patient patient){
        patientService.updatePatient(id,patient);
        return ResponseEntity.status(200).body(new ApiResponse("the patient '"+patient.getName() +"' updated info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePatient(@PathVariable Integer id){
        patientService.deletePatient(id);
        return ResponseEntity.status(200).body(new ApiResponse("The patient with id '"+id+"' deleted successfully"));
    }

    @PutMapping("/bookingApo/{id}")
    public ResponseEntity bookingAppointment(@PathVariable Integer id){
        patientService.appointmentBooking(id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking appointment for patient successfully"));
    }

    @GetMapping("/getApo")
    public ResponseEntity getPatientWithAppointment(){
        return ResponseEntity.status(200).body(patientService.getAllPatentWithAppointment());
    }

    @GetMapping("order")
    public ResponseEntity orderPatientAsec(){
        return ResponseEntity.status(200).body(patientService.patientsOrdered());
    }

    @PutMapping("/assign/{room_id}/assign/{patient_id}")
    public ResponseEntity assignRoomToPatientController(@PathVariable Integer room_id, @PathVariable Integer patient_id){
        patientService.assignRoomToPatient(room_id, patient_id);
        return ResponseEntity.status(200).body(new ApiResponse("Assigned room to patient successfully"));
    }


}
