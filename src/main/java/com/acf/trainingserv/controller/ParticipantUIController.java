package com.acf.trainingserv.controller;

import com.acf.trainingserv.model.Participant;
import com.acf.trainingserv.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ParticipantUIController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantUIController(ParticipantService participantService) {
        this.participantService = participantService;
    }

//    @GetMapping("/participants")
//    public String viewParticipants(Model model) {
//        List<Participant> participants = participantService.getAllParticipants();
//        model.addAttribute("participants", participants);
//        return "participants";
//    }

    @GetMapping("/participant/new")
    public String showAddParticipantForm(Model model) {
        model.addAttribute("participant", new Participant());
        return "add-participant";
    }

    @PostMapping("/participant/save")
    public String saveParticipant(@ModelAttribute Participant participant, Model model) {
        String result = participantService.addParticipant(participant);

        if (!result.equals("SUCCESS")) {
            model.addAttribute("error", result); // Send error message to UI
            model.addAttribute("participant", participant); // Keep user input
            return "add-participant"; // Return to the form with an error
        }

        return "redirect:/admin/participants"; // Redirect if no errors
    }

    @GetMapping("/participant/delete/{id}")
    public String deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return "redirect:/admin/participants";
    }

    @GetMapping("/participants")
    public String searchParticipants(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model) {

        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;

        Page<Participant> participantPage = participantService.searchParticipants(email, phone,name, course, start, end, PageRequest.of(page, size));

        model.addAttribute("participants", participantPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", participantPage.getTotalPages() == 0 ? 1 : participantPage.getTotalPages());

        return "participants";
    }

    // Load the Edit Form
    @GetMapping("/participant/edit/{id}")
    public String editParticipant(@PathVariable Long id, Model model) {
        Optional<Participant> participant = participantService.getParticipantById(id);
        if (participant.isPresent()) {
            model.addAttribute("participant", participant.get());
            return "edit-participant";  // Load edit form
        }
        return "redirect:/admin/participants"; // Redirect if participant not found
    }
    @PostMapping("/participant/update")
    public String updateParticipant(@ModelAttribute Participant participant) {
        participantService.updateParticipant(participant.getId(),participant);
        return "redirect:/admin/participants"; // Redirect to the list after updating
    }

    // Load Add & Edit Form (Same Form for Both)
    @GetMapping("/participant/form")
    public String loadParticipantForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            Optional<Participant> participant = participantService.getParticipantById(id);
            participant.ifPresentOrElse(
                    p -> model.addAttribute("participant", p),
                    () -> model.addAttribute("participant", new Participant())
            );
        } else {
            model.addAttribute("participant", new Participant());
        }
        return "add-participant";  // Same template used for Add & Edit
    }
}

