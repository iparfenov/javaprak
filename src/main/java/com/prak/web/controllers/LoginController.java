package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import com.prak.web.components.SessionFactoryBean;
import com.prak.web.entities.Employees;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping({"/", "login"})
public class LoginController {

    @Autowired
    DAOsBean daos;

    @GetMapping({"/", "/login"})
    public String index(Model model) {
        if (daos.getEmployee() != null) return "redirect:/home";
        else return "login";
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam("login") String login,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView();

        Employees emp = daos.getEmployeeDAO().getByLogin(login);
        boolean success = emp != null && emp.getPassword().equals(password);
        if (success) {
            daos.setEmployee(emp);
            redirectView.setUrl("/home");
            return redirectView;
        } else {
            redirectView.setUrl("/login");
            redirectAttributes.addFlashAttribute("login_success", false);
            return redirectView;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

}
