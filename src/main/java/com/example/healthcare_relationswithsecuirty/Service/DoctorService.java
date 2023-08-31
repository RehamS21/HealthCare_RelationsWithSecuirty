package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.DTO.DoctorDTO;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Repository.AuthRepository;
import com.example.healthcare_relationswithsecuirty.Repository.DoctorRepository;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HandlerPointcut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AuthRepository authRepository;

    public void register(User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("DOCTOR");
        authRepository.save(user);
    }

    public Doctor getAllDoctor(Integer user_id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("Sorry only doctor can see this page");

        Doctor doctor = doctorRepository.findDoctorById(user_id);
        if (doctor == null)
            throw new ApiException("Sorry you cant see info of this doctor");

        return doctor;
    }


    public void addDoctor(Integer user_id , DoctorDTO doctorDTO){
        User user = authRepository.findUserById(user_id);
        Doctor checkDoctor = doctorRepository.findDoctorById(user_id);

        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("The doctor can't add a patient");
        else if (checkDoctor != null)
            throw new ApiException("This doctor already complete his/her information");

        Doctor doctor = new Doctor();
        doctorDTO.setUser_id(user_id);

        doctor.setId(doctorDTO.getUser_id());
        doctor.setName(doctorDTO.getName());
        doctor.setPosition(doctorDTO.getPosition());
        doctor.setPhone(doctorDTO.getPhone());
        doctor.setSalary(doctorDTO.getSalary());

        doctorRepository.save(doctor);
    }
    public void updateDoctor(Integer user_id,Integer id, DoctorDTO doctor){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("Sorry only doctor can see this page");

        Doctor oldDoctor = doctorRepository.findDoctorById(id);

        if (oldDoctor == null)
            throw new ApiException("Doctor id is wrong");
        else if (oldDoctor.getUser().getId() != user_id)
            throw new ApiException("Sorry , you can't update ");

        oldDoctor.setName(doctor.getName());
        oldDoctor.setPhone(doctor.getPhone());
        oldDoctor.setPosition(doctor.getPosition());
        oldDoctor.setSalary(doctor.getSalary());

        doctorRepository.save(oldDoctor);
    }

    public void deleteDoctor(Integer user_id,Integer id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("Sorry only doctor can see this page");

        Doctor deleteDoctor = doctorRepository.findDoctorById(id);

        if (deleteDoctor == null)
            throw new ApiException("Sorry, the doctor id is wrong");
        else if (deleteDoctor.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't delete this doctor");

        doctorRepository.delete(deleteDoctor);
    }

    public Double bounsSalary(Integer user_id,Integer id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("Sorry only doctor can see this page");

        Doctor doctor = doctorRepository.findDoctorById(id);
        if (doctor == null)
            throw new ApiException("Sorry, doctor id is wrong");
        else if (doctor.getUser().getId() != user_id)
            throw new ApiException("Sorry you can't add bouns to this doctor");

        Integer countNumberOfPatient = patientRepository.numberOfPatient(id);
        if (countNumberOfPatient > 4){
            Double bouns = doctor.getSalary() + (doctor.getSalary() * 0.15);
            doctor.setSalary(bouns);
            doctorRepository.save(doctor);
        }else
            throw new ApiException("Sorry can't add bouns to your salary, you must treat more than 4 patient");

        return doctor.getSalary();
    }

    // Insurance deduction from the doctor’s salary higher than 30,000

    public Double deductionSalary(Integer user_id,Integer id){
        User user = authRepository.findUserById(user_id);
        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("Sorry only doctor can see this page");

        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null)
            throw new ApiException("Sorry doctor id is wrong");
        else if (doctor.getUser().getId() != user_id) {
            throw new ApiException("Sorry you can't do this operation");
        }

        doctor = doctorRepository.dudcationDoctorSalary(doctor.getId());

        if (doctor == null)
            throw new ApiException("Sorry, the doctor salary less than 30000");

        Double result = doctor.getSalary() - 3000;

        doctor.setSalary(result);
        doctorRepository.save(doctor);

        return result;
    }
    public Double doctorsAverageSalary(Integer user_id){
        User user = authRepository.findUserById(user_id);

        if (!(user.getRole().equals("DOCTOR")))
            throw new ApiException("Sorry, only doctor can see the average");

        Double avg = doctorRepository.doctorsAverageSalary();

        return avg;
    }
}
