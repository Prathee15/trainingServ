package com.acf.trainingserv.service;


import com.acf.trainingserv.model.Participant;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ParticipantSpecification {

    public static Specification<Participant> hasEmail(String email) {
        return (root, query, cb) -> email == null || email.isEmpty()
                ? null
                : cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    public static Specification<Participant> hasName(String name) {
        return (root, query, cb) -> name == null || name.isEmpty()
                ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Participant> hasCourse(String course) {
        return (root, query, cb) -> course == null || course.isEmpty()
                ? null
                : cb.like(cb.lower(root.get("courseEnrolled")), "%" + course.toLowerCase() + "%");
    }

    public static Specification<Participant> hasPhone(String phone) {
        return (root, query, cb) -> phone == null || phone.isEmpty()
                ? null
                : cb.equal(root.get("phoneNumber"), phone);
    }

    public static Specification<Participant> hasEnrollmentDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) return null;
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("enrollmentDate"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("enrollmentDate"), startDate);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("enrollmentDate"), endDate);
            }
        };
    }
}

