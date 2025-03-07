package com.acf.trainingserv.controller;

import com.acf.trainingserv.model.User;
import com.acf.trainingserv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String loginPageLoad() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());  // ✅ Ensures the "user" object is available
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Check if username already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        // Call registerUser and capture response
        String result = userService.registerUser(user);

        if (!result.equals("User registered successfully.")) {
            model.addAttribute("error", result); // Show appropriate error message
            return "register";
        }

        return "redirect:/login?success"; // Redirect to login page after successful registration
    }
}

