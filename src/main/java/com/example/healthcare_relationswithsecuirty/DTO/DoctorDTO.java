package com.example.healthcare_relationswithsecuirty.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorDTO {

    private Integer user_id;
    @NotEmpty(message = "Doctor name must not null")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @NotEmpty(message = "position must not null")
    @Column(columnDefinition = "varchar(20) not null, check(position = 'dentist' or position = 'general' or position = 'pediatrician')")
    private String position;
    @NotEmpty(message = "doctor phone number must not empty")
    // saudi phone number. reference : https://gist.github.com/homaily/8672499
    @Pattern(regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$")
    @Column(columnDefinition = "varchar(10) unique not null")
    private String phone;
    @NotNull(message = "doctor salary must not null")
    @Positive(message = "doctor salary must be a positive")
    @Column(columnDefinition = "int not null")
    private Double salary;
}
