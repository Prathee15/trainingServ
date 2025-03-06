package com.acf.trainingserv.controller;

import com.acf.trainingserv.model.Participant;
import com.acf.trainingserv.service.ExcelExportService;
import com.acf.trainingserv.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    private ParticipantService participantService;

    @Autowired
    ExcelExportService excelExportService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        Optional<Participant> participant = participantService.getParticipantById(id);
        return participant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Participant> getParticipantByEmail(@PathVariable String email) {
        Optional<Participant> participant = participantService.getParticipantByEmail(email);
        return participant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<Participant> addParticipant(@RequestBody Participant participant) {
//        return ResponseEntity.ok(participantService.addParticipant(participant));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long id, @RequestBody Participant updatedParticipant) {
        return (participantService.updateParticipant(id, updatedParticipant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportFilteredParticipantsToExcel(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            // Convert date strings to LocalDate (if provided)
            LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
            LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;

            // Reuse existing searchParticipants method, but fetch all results (no pagination)
            List<Participant> participants = participantService
                    .searchParticipants(email, phone, name, course, start, end, Pageable.unpaged())
                    .getContent(); // Get full list without pagination

            // Generate Excel file

            byte[] excelBytes = excelExportService.generateExcelFile(participants);

            // Set headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=filtered_participants.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}

