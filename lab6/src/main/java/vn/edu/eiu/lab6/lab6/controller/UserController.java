package vn.edu.eiu.lab6.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.lab6.lab6.entity.User;
import vn.edu.eiu.lab6.lab6.service.UserService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Check if username already exists
        if (userService.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username already exists");
        }
        
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "User created successfully!");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found!");
            return "redirect:/users";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Integer id, @Valid @ModelAttribute User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Check if username already exists for other users
        Optional<User> existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getUserId().equals(id)) {
            bindingResult.rejectValue("username", "error.user", "Username already exists");
        }
        
        if (bindingResult.hasErrors()) {
            user.setUserId(id);
            return "user-form";
        }
        
        user.setUserId(id);
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "User updated successfully!");
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (userService.existsById(id)) {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "User not found!");
        }
        return "redirect:/users";
    }
}