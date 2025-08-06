package com.example.SSMS.web;

import com.example.SSMS.parent.ParentService;
import com.example.SSMS.parent.Parent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final ParentService parentService;

    public AuthController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("parent", new Parent());
        return "RegisterLogin";
    }

    @GetMapping("/register")
    public String showParentRegisterForm(Model model) {
        model.addAttribute("parent", new Parent());
        return "RegisterLogin";
    }

    @PostMapping("/register")
    public String registerParent(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println("the password is"+password);
        try {
            parentService.registerParent(name, phone, email, password,address,gender);
            redirectAttributes.addFlashAttribute("message", "Parent registered successfully! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }
}