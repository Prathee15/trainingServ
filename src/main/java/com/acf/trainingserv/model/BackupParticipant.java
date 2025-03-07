package com.acf.trainingserv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "backup_participants")
@Getter
@Setter
public class BackupParticipant {
    private final LocalDate deletedAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    @Column(nullable = true)
    private String courseEnrolled;

    private LocalDate enrollmentDate;

    @Embedded
    private Address address;

    public BackupParticipant(Participant participant) {
        this.name = participant.getName();
        this.email = participant.getEmail();
        this.phoneNumber = participant.getPhoneNumber();
        this.courseEnrolled = participant.getCourseEnrolled();
        this.enrollmentDate = participant.getEnrollmentDate();
        this.address = participant.getAddress();
        this.deletedAt = LocalDate.now();
    }
}
