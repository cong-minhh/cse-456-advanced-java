package vn.edu.eiu.testlab.fmecse456_2131200085.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.edu.eiu.testlab.fmecse456_2131200085.dto.LoginDto;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.User;
import vn.edu.eiu.testlab.fmecse456_2131200085.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/auth")
    public String processLogin(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
            BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }

        // Find user by username and password (no password hashing as per requirements)
        User user = userRepository.findByUsernameAndPassword(
                loginDto.getUsername(),
                loginDto.getPassword());

        if (user == null) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        // Store user in session
        session.setAttribute("user", user);
        return "redirect:/equipment";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session
        session.invalidate();
        return "redirect:/login";
    }
}
