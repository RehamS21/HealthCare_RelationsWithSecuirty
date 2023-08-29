package com.example.healthcare_relationswithsecuirty.Service;

import com.example.healthcare_relationswithsecuirty.Api.ApiException;
import com.example.healthcare_relationswithsecuirty.Model.Doctor;
import com.example.healthcare_relationswithsecuirty.Repository.DoctorRepository;
import com.example.healthcare_relationswithsecuirty.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HandlerPointcut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public List<Doctor> getAllDoctor(){
        return doctorRepository.findAll();
    }
    public void addDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }
    public void updateDoctor(Integer id, Doctor doctor){
        Doctor oldDoctor = doctorRepository.findDoctorById(id);

        if (oldDoctor == null)
            throw new ApiException("Doctor id is wrong");

        oldDoctor.setName(doctor.getName());
        oldDoctor.setPhone(doctor.getPhone());
        oldDoctor.setPosition(doctor.getPosition());
        oldDoctor.setSalary(doctor.getSalary());

        doctorRepository.save(oldDoctor);
    }

    public void deleteDoctor(Integer id){
        Doctor deleteDoctor = doctorRepository.findDoctorById(id);

        if (deleteDoctor == null)
            throw new ApiException("Sorry, the doctor id is wrong");

        doctorRepository.delete(deleteDoctor);
    }

    public Double bounsSalary(Integer id){
        Doctor doctor = doctorRepository.findDoctorById(id);
        if (doctor == null)
            throw new ApiException("Sorry, doctor id is wrong");

        Integer countNumberOfPatient = patientRepository.numberOfPatient(id);
        if (countNumberOfPatient > 4){
            Double bouns = doctor.getSalary() + (doctor.getSalary() * 0.15);
            doctor.setSalary(bouns);
            doctorRepository.save(doctor);
        }else
            throw new ApiException("Sorry can't add bouns to your salary, you must treat more than 4 patient");

        return doctor.getSalary();
    }

    public List<Doctor> getAllDoctorWithPosition(String position){
        List<Doctor> doctors = doctorRepository.findDoctorByPosition(position);

        if (doctors.isEmpty())
            throw new ApiException("Sorry, the doctors with position '"+position+"' not found");

        return doctors;
    }

    // return the higher salary of doctors

    public Double higherSalary(){
        Double doctorsalary = doctorRepository.getDoctorWithHigherSalary();

        if (doctorsalary == 0)
            throw new ApiException("Sorry the doctor not found");

        return doctorsalary;
    }



    // Insurance deduction from the doctor’s salary higher than 30,000

    public Double deductionSalary(Integer id){
        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null)
            throw new ApiException("Sorry doctor id is wrong");

        doctor = doctorRepository.dudcationDoctorSalary(doctor.getId());

        if (doctor == null)
            throw new ApiException("Sorry, the doctor salary less than 30000");

        Double result = doctor.getSalary() - 3000;

        doctor.setSalary(result);
        doctorRepository.save(doctor);

        return result;
    }


    public List<Doctor> DoctorOrderdByPostion(String position){
        List<Doctor> doctors = doctorRepository.orderSalaryByPosition(position);

        if (doctors.isEmpty())
            throw new ApiException("Sorry no doctors exist");

        return doctors;
    }

    public Double doctorsAverageSalary(){
        Double avg = doctorRepository.doctorsAverageSalary();

        return avg;
    }




}
