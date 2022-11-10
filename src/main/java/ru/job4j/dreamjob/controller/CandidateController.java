package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static ru.job4j.dreamjob.util.UtilUser.getUser;

@ThreadSafe
@Controller
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("candidate",
                new Candidate(0, "Заполните", "Заполните", LocalDate.now()));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setCreated(LocalDate.now());
        candidateService.add(candidate);
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        File photo = new File("src/main/resources/images" + File.separator + candidate.getId() + "." + ext);
        photo.createNewFile();
        try (FileOutputStream out = new FileOutputStream(photo)) {
            out.write(file.getBytes());
        }
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{CandidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("CandidateId") int id, HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("candidate", candidateService.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) throws IOException {
        candidateService.updateCandidate(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) throws IOException {
        Candidate candidate = candidateService.findById(candidateId);
        String photoName = Arrays.stream(Objects.requireNonNull(new File("src/main/resources/images")
                        .listFiles())).filter(f ->
                f.getName().split("\\.")[0].equals(String.valueOf(candidateId)))
                .findFirst().get().getName();
        File photo = new File("src/main/resources/images" + File.separator + photoName);
        byte[] arr = Files.readAllBytes(photo.toPath());
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(arr.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(arr));
    }
}
