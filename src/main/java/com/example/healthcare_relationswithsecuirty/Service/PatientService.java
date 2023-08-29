package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.DTO.PatientDTO;
import com.example.healthcare_relationswithsecuirty.Model.*;
import com.example.healthcare_relationswithsecuirty.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AuthRepository authRepository;
    private final RoomRepository roomRepository;

    public void register(User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("PATIENT");
        authRepository.save(user);
    }

    public Patient getAllPatient(Integer user_id){
        return patientRepository.getPatientById(user_id);
    }

    public void addPatient(Integer user_id,Integer doctor_id, PatientDTO patientDto){
        User user = authRepository.findUserById(user_id);
        patientDto.setUser_id(user_id);

        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
        if (doctor == null)
            throw new ApiException("Sorry the doctor id is wrong");

        Patient patient = new Patient();

        patientDto.setUser_id(user_id);

        patient.setId(patientDto.getUser_id());
        patient.setName(patientDto.getName());
        patient.setAge(patientDto.getAge());
        patient.setPhone(patientDto.getPhone());
        patient.setBalance(patientDto.getBalance());
        patient.setAppointment(false);
        patient.setDoctor(doctor);
        patient.setUser(user);

        patientRepository.save(patient);
    }

    public void updatePatient(Integer user_id,Integer id, Patient patient){
        Patient oldPatient = patientRepository.findPatientById(id);

        if (oldPatient == null)
            throw new ApiException("Sorry, patient id is wrong");
        else if (patient.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't update the patient");


        oldPatient.setName(patient.getName());
        oldPatient.setAge(patient.getAge());
        oldPatient.setPhone(patient.getPhone());
        oldPatient.setBalance(patient.getBalance());
        oldPatient.setAppointment(patient.getAppointment());


        patientRepository.save(oldPatient);
    }

    public void deletePatient(Integer user_id,Integer id){
        Patient deletePatient = patientRepository.findPatientById(id);

        if (deletePatient == null)
            throw new ApiException("Sorry, patient id is wrong");
        else if (deletePatient.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't delete this patient");


        patientRepository.delete(deletePatient);
    }

    public void appointmentBooking(Integer user_id,Integer id){
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null)
            throw new ApiException("the patient id is wrong");
        else if (patient.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't book an appointmment to this patient");

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

    public void assignRoomToPatient(Integer user_id,Integer room_id , Integer patient_id){
        Room room = roomRepository.findRoomById(room_id);
        Patient patient = patientRepository.findPatientById(patient_id);

        if (room == null || patient == null)
            throw new ApiException("Sorry , the patient or room id is wrong");
        else if (patient.getUser().getId() != user_id) {
            throw new ApiException("Sorry you can't assign this room to the patient");
        }

        patient.setRoom(room);

        patientRepository.save(patient);
    }



}
