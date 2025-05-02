package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String search(Model model) {
        if (daos.getEmployee() == null) { return "redirect:/login"; }
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("employees", daos.getEmployeeDAO().getAll());
        model.addAttribute("page", "/employees");
        return "search";
    }

    @PostMapping
    public String search(@RequestParam("search") String search, Model model) {
        if (daos.getEmployee() == null) { return "redirect:/login"; }
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("employees", daos.getEmployeeDAO().search(search));
        return "search";
    }

    @GetMapping("/{id}")
    public String employeePage(@PathVariable("id") int id, Model model) {
        if (daos.getEmployee() == null) { return "redirect:/login"; }
        Employees e = daos.getEmployeeDAO().getById(id);
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("data", e);
        model.addAttribute("history", daos.getEmployeeDAO().getHistory(e));
        return "employee_page";
    }

    @GetMapping("/{id}/changeEducation")
    public String changeEducation(@PathVariable("id") int id, Model model) {
        model.addAttribute("changing_education", true);
        return employeePage(id, model);
    }

    @PostMapping("/{id}/changeEducation")
    public String PostChangeEducation(@PathVariable("id") int id,
                                      @RequestParam("education") String education,
                                      Model model) {
        Employees e = daos.getEmployeeDAO().getById(id);
        e.setEducation(education);
        try {
            daos.getEmployeeDAO().update(e);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            employeePage(id, model);
        }
        return employeePage(id, model);
    }

    @GetMapping("{id}/changeEmail")
    public String changeEmail(@PathVariable("id") int id, Model model) {
        model.addAttribute("changing_email", true);
        return employeePage(id, model);
    }

    @PostMapping("/{id}/changeEmail")
    public String PostChangeEmail(@PathVariable("id") int id,
                                  @RequestParam("email") String email,
                                  Model model) {
        Employees e = daos.getEmployeeDAO().getById(id);
        e.setEmail(email);
        try {
            daos.getEmployeeDAO().update(e);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return employeePage(id, model);
        }
        return employeePage(id, model);
    }

    @GetMapping("{id}/changeAddress")
    public String changeAddress(@PathVariable("id") int id, Model model) {
        model.addAttribute("changing_address", true);
        return employeePage(id, model);
    }

    @PostMapping("/{id}/changeAddress")
    public String PostChangeAddress(@PathVariable("id") int id,
                                    @RequestParam("address") String address,
                                    Model model) {
        Employees e = daos.getEmployeeDAO().getById(id);
        e.setAddress(address);
        try {
            daos.getEmployeeDAO().update(e);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return employeePage(id, model);
        }
        return employeePage(id, model);
    }

    @GetMapping("{id}/promote")
    public String changePosition(@PathVariable("id") int id, Model model) {
        model.addAttribute("changing_position", true);
        return employeePage(id, model);
    }

    @PostMapping("/{id}/promote")
    public String PostPromote(@PathVariable("id") int id,
                              @RequestParam("position") String position,
                              Model model) {
        Employees e = daos.getEmployeeDAO().getById(id);
        try {
            daos.getEmployeeDAO().promote(e, position, null);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return employeePage(id, model);
        }
        return employeePage(id, model);
    }

}
