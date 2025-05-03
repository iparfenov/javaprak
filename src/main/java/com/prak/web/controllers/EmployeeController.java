package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Employees;
import com.prak.web.exceptions.MalformedRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String search(Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("employees", daos.getEmployeeDAO().getAll());
        model.addAttribute("page", "/employees");
        return "search";
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (daos.getEmployee() == null) { return "redirect:/login"; }
        model.addAttribute("adding_employee", true);
        return search(model);
    }

    @PostMapping("/add")
    public String add(@RequestParam("name") String name,
                      @RequestParam("address") String address,
                      @RequestParam("birthday_year") int birthday_year,
                      @RequestParam("birthday_month") int birthday_month,
                      @RequestParam("birthday_day") int birthday_day,
                      @RequestParam("education") String education,
                      @RequestParam("working_since_year") int working_since_year,
                      @RequestParam("working_since_month") int working_since_month,
                      @RequestParam("working_since_day") int working_since_day,
                      @RequestParam("position") String position,
                      @RequestParam("email") String email,
                      @RequestParam("login") String login,
                      @RequestParam("password") String password,
                      @RequestParam(value = "is_admin", required = false) Optional<Boolean> isAdminIn,
                      Model model) {
        if (name.isEmpty() || address.isEmpty() || position.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty()) {
            throw new MalformedRequestException("Не заполнены обязательные поля!");
        }
        Boolean isAdmin = isAdminIn.orElse(false);
        Calendar birthday = Calendar.getInstance();
        birthday.set(birthday_day, birthday_month, birthday_year);

        Calendar working_since = Calendar.getInstance();
        working_since.set(working_since_year, working_since_month, working_since_day);

        Employees new_e = new Employees();
        new_e.setName(name);
        new_e.setAddress(address);
        new_e.setBirthday(new Timestamp(birthday.getTimeInMillis()));
        new_e.setEducation(education);
        new_e.setWorking_since(new Timestamp(working_since.getTimeInMillis()));
        new_e.setPosition(position);
        new_e.setEmail(email);
        new_e.setIs_admin(isAdmin);
        new_e.setLogin(login);
        new_e.setPassword(password);
        try {
            daos.getEmployeeDAO().insert(new_e);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return search(model);
        }
//        return search(model);
        return "redirect:/employees";
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
