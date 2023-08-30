package com.example.healthcare_relationswithsecuirty.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty(message = "the room type must not null")
//    @Column(columnDefinition = "varchar(30) not null, check( roomtype = 'emergency' or roomtype ='intensive care' or roomtype = 'operating' or roomtype = 'hypnosis')")
//    private String roomtype;
    @NotEmpty(message = "the room type must not null")
    @Column(columnDefinition = "varchar(30) not null")
    private String roomtype;



    @OneToMany(cascade = CascadeType.ALL , mappedBy = "room")
    @JsonIgnore
    private Set<Patient> patients;
}
