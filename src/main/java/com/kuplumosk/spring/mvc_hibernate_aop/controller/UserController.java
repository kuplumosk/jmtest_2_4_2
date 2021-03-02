package com.kuplumosk.spring.mvc_hibernate_aop.controller;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.User;
import com.kuplumosk.spring.mvc_hibernate_aop.service.UserService;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/admin")
    public String showUserList(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allRoles", userService.findAllRoles());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        return "admin";
    }


    @GetMapping("admin/new")
    public String addUserPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", userService.findAllRoles());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("role_select") Long[] roles ){
        userService.setRolesToUser(user, roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("allRoles", userService.findAllRoles());
        model.addAttribute("userRoles", userService.getRoleById(id));
        model.addAttribute("user", userService.findById(id));
        return "/update-user";
    }

    @PostMapping("admin/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role_select") Long[] roles) {
        userService.setRolesToUser(user, roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
