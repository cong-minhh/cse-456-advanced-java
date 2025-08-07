package vn.edu.eiu.lab6.lab6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.lab6.lab6.entity.Student;
import vn.edu.eiu.lab6.lab6.service.StudentService;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping
    public String showStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/new")
    public String createStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("message", "Student created successfully!");
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "student-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Student not found!");
            return "redirect:/students";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, RedirectAttributes redirectAttributes) {
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
