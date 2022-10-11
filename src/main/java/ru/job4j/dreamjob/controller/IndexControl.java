package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

import static ru.job4j.dreamjob.util.UtilUser.getUser;

@ThreadSafe
@Controller
public class IndexControl {
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        return "index";
    }

    @GetMapping("/")
    public String index2(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        return "index";
    }
}
