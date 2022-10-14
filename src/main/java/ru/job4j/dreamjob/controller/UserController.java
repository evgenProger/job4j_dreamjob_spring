package ru.job4j.dreamjob.controller;

import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static ru.job4j.dreamjob.util.UtilUser.getUser;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findByEmailAndPwd(user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user,  HttpServletRequest req) {
        Optional<User> regUser = userService.findByEmailAndPwd(user.getEmail(), user.getPassword());
        if (regUser.isPresent()) {
            return "redirect:/registerPage?fail=true";
        }
        userService.add(user);
        return "redirect:/loginPage";
    }

    @GetMapping("/registerPage")
    public String registrPage(Model model,  HttpServletRequest req,
                              @RequestParam(name = "fail", required = false) Boolean fail) {
        HttpSession session = req.getSession();
        model.addAttribute("user", getUser(session));
        model.addAttribute("fail", fail != null);
        model.addAttribute("message", "Пользователь с такой почтой уже существует");
        return "registration";
    }
}

