package com.acf.trainingserv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "participants")
@Getter
@Setter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String courseEnrolled;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Embedded
    private Address address;
}
