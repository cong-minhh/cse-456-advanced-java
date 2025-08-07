package vn.edu.eiu.lab6.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.lab6.lab6.entity.Major;
import vn.edu.eiu.lab6.lab6.service.MajorService;

import java.util.Optional;

@Controller
@RequestMapping("/majors")
public class MajorController {
    
    @Autowired
    private MajorService majorService;

    @GetMapping
    public String showMajors(Model model) {
        model.addAttribute("majors", majorService.getAllMajors());
        return "major-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("major", new Major());
        return "major-form";
    }

    @PostMapping("/new")
    public String createMajor(@ModelAttribute Major major, RedirectAttributes redirectAttributes) {
        majorService.saveMajor(major);
        redirectAttributes.addFlashAttribute("message", "Major created successfully!");
        return "redirect:/majors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Major> major = majorService.getMajorById(id);
        if (major.isPresent()) {
            model.addAttribute("major", major.get());
            return "major-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Major not found!");
            return "redirect:/majors";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateMajor(@PathVariable Long id, @ModelAttribute Major major, RedirectAttributes redirectAttributes) {
        major.setMajorId(id);
        majorService.saveMajor(major);
        redirectAttributes.addFlashAttribute("message", "Major updated successfully!");
        return "redirect:/majors";
    }

    @PostMapping("/delete/{id}")
    public String deleteMajor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (majorService.existsById(id)) {
            majorService.deleteMajor(id);
            redirectAttributes.addFlashAttribute("message", "Major deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Major not found!");
        }
        return "redirect:/majors";
    }
}