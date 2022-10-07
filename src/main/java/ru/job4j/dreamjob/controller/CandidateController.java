package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.time.LocalDate;

@Controller
public class CandidateController {
    private final CandidateStore candidates = CandidateStore.instof();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidates.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate",
                new Candidate(0, "Заполните", "Заполните", LocalDate.now()));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        candidate.setCreated(LocalDate.now());
        candidates.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{CandidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("CandidateId") int id) {
        model.addAttribute("candidate", candidates.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        candidates.updateCandidate(candidate);
        return "redirect:/candidates";
    }

}
