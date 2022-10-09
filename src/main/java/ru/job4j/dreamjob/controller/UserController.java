package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }


}
