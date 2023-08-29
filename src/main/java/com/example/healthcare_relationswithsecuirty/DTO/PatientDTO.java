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
public class PatientDTO {

    private Integer user_id;

    @NotEmpty(message = "patient name must not null")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "patient phone number must not null")
    @Pattern(regexp = "^(009665|9665|\\+9665|05|5)(5|0|3|6|4|9|1|8|7)([0-9]{7})$")
    @Column(columnDefinition = "varchar(10) unique not null")
    private String phone;

    @NotNull(message = "age must not null")
    @Positive(message = "age must be positive")
    @Column(columnDefinition = "int(80) not null")
    private Integer age;

    @NotNull(message = "patient money must not null")
    @Positive(message = "patient salary must be a positive")
    @Column(columnDefinition = "int not null")
    private Integer balance;

    @Column(columnDefinition = "BOOLEAN default false")
    private Boolean appointment = false;
}
