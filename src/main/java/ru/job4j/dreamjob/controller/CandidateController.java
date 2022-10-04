package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.store.CandidateStore;

@Controller
public class CandidateController {
    private final CandidateStore candidate = CandidateStore.instof();

    @GetMapping("/candidates")
    private String candidates(Model model) {
        model.addAttribute("candidates", candidate.findAll());
        return "candidates";
    }
}
