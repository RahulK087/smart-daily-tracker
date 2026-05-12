package com.example.smart_tracker.controller;

import com.example.smart_tracker.entity.User;
import com.example.smart_tracker.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {

        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

        userRepository.save(user);

        return "redirect:/login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session) {

        User user = userRepository.findByEmail(email);

        if (user != null &&
                user.getPassword().equals(password)) {

            session.setAttribute("loggedInUser", user);

            return "redirect:/dashboard";
        }

        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}