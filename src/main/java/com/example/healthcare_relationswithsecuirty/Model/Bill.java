package com.example.healthcare_relationswithsecuirty.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "The bill price must not null")
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer billprice;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;
}
