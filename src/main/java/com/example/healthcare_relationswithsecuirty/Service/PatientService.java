package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.Model.Bill;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Model.Patient;
import com.example.healthcare_relationswithsecuirty.Model.Room;
import com.example.healthcare_relationswithsecuirty.Repository.BillRepository;
import com.example.healthcare_relationswithsecuirty.Repository.DoctorRepository;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import com.example.healthcare_relationswithsecuirty.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final BillRepository billRepository;
    private final RoomRepository roomRepository;

    public List<Patient> getAllPatient(){
        return patientRepository.findAll();
    }

    public void addPatient(Integer doctor_id, Patient patient){
//        assignDoctorToPatient(doctor_id, patient.getId());
        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
        if (doctor == null)
            throw new ApiException("Sorry the doctor id is wrong");

        patient.setDoctor(doctor);
        patientRepository.save(patient);
    }

    public void updatePatient(Integer id, Patient patient){
        Patient oldPatient = patientRepository.findPatientById(id);

        if (oldPatient == null)
            throw new ApiException("Sorry, patient id is wrong");


        oldPatient.setName(patient.getName());
        oldPatient.setAge(patient.getAge());
        oldPatient.setPhone(patient.getPhone());
        oldPatient.setBalance(patient.getBalance());
        oldPatient.setAppointment(patient.getAppointment());


        patientRepository.save(oldPatient);
    }

    public void deletePatient(Integer id){
        Patient deletePatient = patientRepository.findPatientById(id);

        if (deletePatient == null)
            throw new ApiException("Sorry, patient id is wrong");

        patientRepository.delete(deletePatient);
    }

    public void appointmentBooking(Integer id){
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null)
            throw new ApiException("the patient id is wrong");

        if(patient.getAppointment())
            throw new ApiException("You have already an appointment");
        else
            patient.setAppointment(true);

        patientRepository.save(patient);
    }

    public List<Patient> getAllPatentWithAppointment(){
        return patientRepository.getAllPatientWithAppintment();
    }
    public List<Patient> patientsOrdered(){
        List<Patient> patients = patientRepository.orderPatientByMoney();

        if (patients.isEmpty())
            throw new ApiException("Sorry , No patients exist");

        return patients;
    }

    public void assignDoctorToPatient(Integer doctor_id, Integer patient_id){
        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
        Patient patient = patientRepository.findPatientById(patient_id);

        if (doctor == null || patient == null)
            throw new ApiException("Sorry , the patient or doctor id are wrong");

        patient.setDoctor(doctor);

        patientRepository.save(patient);

    }

    public void assignRoomToPatient(Integer room_id , Integer patient_id){
        Room room = roomRepository.findRoomById(room_id);
        Patient patient = patientRepository.findPatientById(patient_id);

        if (room == null || patient == null)
            throw new ApiException("Sorry , the patient or room id is wrong");

        patient.setRoom(room);

        patientRepository.save(patient);
    }



}
