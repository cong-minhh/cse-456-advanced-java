package vn.edu.eiu.lab6.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.lab6.lab6.entity.Student;
import vn.edu.eiu.lab6.lab6.service.StudentService;
import vn.edu.eiu.lab6.lab6.service.MajorService;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MajorService majorService;

    @GetMapping
    public String showStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("majors", majorService.getAllMajors());
        return "student-form";
    }

    @PostMapping("/new")
    public String createStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("majors", majorService.getAllMajors());
            return "student-form";
        }
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("message", "Student created successfully!");
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            model.addAttribute("majors", majorService.getAllMajors());
            return "student-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Student not found!");
            return "redirect:/students";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute Student student, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            student.setStudentId(id);
            model.addAttribute("majors", majorService.getAllMajors());
            return "student-form";
        }
        student.setStudentId(id);
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (studentService.existsById(id)) {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Student not found!");
        }
        return "redirect:/students";
    }
}
