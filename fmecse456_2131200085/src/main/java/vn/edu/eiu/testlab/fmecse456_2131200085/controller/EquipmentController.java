package vn.edu.eiu.testlab.fmecse456_2131200085.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.Equipment;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.EquipmentType;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.User;
import vn.edu.eiu.testlab.fmecse456_2131200085.repository.EquipmentRepository;
import vn.edu.eiu.testlab.fmecse456_2131200085.repository.EquipmentTypeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentTypeRepository equipmentTypeRepository;

    @GetMapping
    public String listEquipment(@RequestParam(required = false) String keyword, Model model, HttpSession session) {
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Equipment> equipmentList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            equipmentList = equipmentRepository.findByEquipmentNameContainingIgnoreCase(keyword);
        } else {
            equipmentList = equipmentRepository.findAll();
        }

        model.addAttribute("equipmentList", equipmentList);
        return "equipment-list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model, HttpSession session) {
        // Check if user is admin
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            model.addAttribute("error", "You are not authorized to access this page!");
            return "redirect:/equipment";
        }

        List<EquipmentType> equipmentTypes = equipmentTypeRepository.findAll();
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("equipmentTypes", equipmentTypes);
        return "equipment-form";
    }

    @PostMapping("/save")
    public String saveEquipment(@Valid @ModelAttribute("equipment") Equipment equipment,
            BindingResult result, Model model, HttpSession session) {
        // Check if user is admin
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            model.addAttribute("error", "You are not authorized to perform this action!");
            return "redirect:/equipment";
        }

        if (result.hasErrors()) {
            model.addAttribute("equipmentTypes", equipmentTypeRepository.findAll());
            return "equipment-form";
        }

        // Set purchase date to current time if not set
        if (equipment.getPurchaseDate() == null) {
            equipment.setPurchaseDate(LocalDateTime.now());
        }

        // Set equipment type
        if (equipment.getEquipmentType() != null && equipment.getEquipmentType().getEquipmentTypeId() != null) {
            EquipmentType type = equipmentTypeRepository.findById(equipment.getEquipmentType().getEquipmentTypeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Invalid equipment type ID: " + equipment.getEquipmentType().getEquipmentTypeId()));
            equipment.setEquipmentType(type);
        }

        equipmentRepository.save(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model, HttpSession session) {
        // Check if user is admin
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            model.addAttribute("error", "You are not authorized to access this page!");
            return "redirect:/equipment";
        }

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid equipment ID: " + id));

        List<EquipmentType> equipmentTypes = equipmentTypeRepository.findAll();
        model.addAttribute("equipment", equipment);
        model.addAttribute("equipmentTypes", equipmentTypes);
        return "equipment-form";
    }

    @PostMapping("/update")
    public String updateEquipment(@Valid @ModelAttribute("equipment") Equipment equipment,
            BindingResult result, Model model, HttpSession session) {
        // This method is similar to save, but we keep it separate for clarity
        return saveEquipment(equipment, result, model, session);
    }

    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable("id") String id, Model model, HttpSession session) {
        // Check if user is admin
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            model.addAttribute("error", "You are not authorized to perform this action!");
            return "redirect:/equipment";
        }

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid equipment ID: " + id));

        equipmentRepository.delete(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/top-by-type")
    public String getTopEquipmentByType(Model model, HttpSession session) {
        // Check if user is admin
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            model.addAttribute("error", "You are not authorized to access this page!");
            return "redirect:/equipment";
        }

        // Get all equipment types
        List<EquipmentType> types = equipmentTypeRepository.findAll();

        // For each type, get top 3 equipment by quantity
        for (EquipmentType type : types) {
            List<Equipment> topEquipment = equipmentRepository
                    .findTop3ByEquipmentTypeOrderByQuantityAvailableDesc(type.getEquipmentTypeId());
            type.setTopEquipment(topEquipment);
        }

        model.addAttribute("equipmentTypes", types);
        return "top-equipment";
    }
}
