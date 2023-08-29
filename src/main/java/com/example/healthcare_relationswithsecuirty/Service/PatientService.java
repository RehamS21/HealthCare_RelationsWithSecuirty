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
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("PATIENT")))
            throw new ApiException("The doctor can't add a patient");

        return patientRepository.getPatientById(user_id);
    }

    public void addPatient(Integer user_id,Integer doctor_id, PatientDTO patientDto){
        User user = authRepository.findUserById(user_id);
        Patient checkPatient = patientRepository.findPatientById(user_id);

        if (!(user.getRole().equals("PATIENT")))
            throw new ApiException("The doctor can't add a patient");
        else if (checkPatient != null)
            throw new ApiException("This patient already complete his/her information");


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

    public void updatePatient(Integer user_id, Integer patient_id,PatientDTO patientDTO){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("PATIENT")))
            throw new ApiException("The doctor can't add a patient");

        Patient oldPatient = patientRepository.findPatientById(patient_id);

        if (oldPatient == null)
            throw new ApiException("Sorry, patient id is wrong");
        else if (oldPatient.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't update the patient");

        patientDTO.setUser_id(user_id);

        oldPatient.setId(patientDTO.getUser_id());
        oldPatient.setName(patientDTO.getName());
        oldPatient.setAge(patientDTO.getAge());
        oldPatient.setPhone(patientDTO.getPhone());
        oldPatient.setBalance(patientDTO.getBalance());


        patientRepository.save(oldPatient);
    }

    public void deletePatient(Integer user_id,Integer id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("PATIENT")))
            throw new ApiException("The doctor can't add a patient");

        Patient deletePatient = patientRepository.findPatientById(id);

        if (deletePatient == null)
            throw new ApiException("Sorry, patient id is wrong");
        else if (deletePatient.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't delete this patient");

        deletePatient.setDoctor(null);
        deletePatient.setUser(null);
        deletePatient.setRoom(null);
        patientRepository.delete(deletePatient);
    }

    public void appointmentBooking(Integer user_id,Integer id){
       User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("PATIENT")))
            throw new ApiException("The doctor can't add a patient");


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



    // This method no longer needed becuase I assigned the doctor already in the addPatient method
    public void assignDoctorToPatient(Integer doctor_id, Integer patient_id){
        Doctor doctor = doctorRepository.findDoctorById(doctor_id);
        Patient patient = patientRepository.findPatientById(patient_id);

        if (doctor == null || patient == null)
            throw new ApiException("Sorry , the patient or doctor id are wrong");

        patient.setDoctor(doctor);

        patientRepository.save(patient);

    }

    public void assignRoomToPatient(Integer user_id,Integer room_id , Integer patient_id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("PATIENT")))
            throw new ApiException("The doctor can't add a patient");


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
