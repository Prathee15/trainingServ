package com.acf.trainingserv.service;

import com.acf.trainingserv.model.Participant;
import com.acf.trainingserv.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    public Optional<Participant> getParticipantByEmail(String email) {
        return participantRepository.findByEmail(email);
    }

    public String addParticipant(Participant participant) {
            if (participantRepository.existsByEmail(participant.getEmail())) {
                return "Email already exists!";
            }

            if (participantRepository.existsByPhoneNumber(participant.getPhoneNumber())) {
                return "Mobile number already exists!";
            }

            if (participant.getEnrollmentDate() == null) {
                participant.setEnrollmentDate(LocalDate.now());
            }

            participantRepository.save(participant);
            return "SUCCESS";
        }

    public ResponseEntity<Participant> updateParticipant(Long id, Participant updatedParticipant) {
        return participantRepository.findById(id).map(participant -> {
            participant.setId(id);
            return ResponseEntity.ok(participantRepository.save(updatedParticipant));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    public Page<Participant> getPaginatedParticipants(int page, int size) {
        return participantRepository.findAll(PageRequest.of(page, size));
    }
    public Page<Participant> searchParticipants(String email,String phone, String name, String course, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<Participant> spec = Specification.where(ParticipantSpecification.hasEmail(email))
                .and(ParticipantSpecification.hasName(name))
                .and(ParticipantSpecification.hasCourse(course))
                .and(ParticipantSpecification.hasPhone(phone))
                .and(ParticipantSpecification.hasEnrollmentDateBetween(startDate, endDate));

        return participantRepository.findAll(spec, pageable);
    }
}

