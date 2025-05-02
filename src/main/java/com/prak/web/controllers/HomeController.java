package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Employees;
import com.prak.web.entities.Projects;
import org.apache.tools.ant.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String home(Model model) {
        Employees e = daos.getEmployee();
        if (e == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("employee", e);
            model.addAttribute("payouts", daos.getEmployeeDAO().getPayouts(e));
            List<Projects> projects = daos.getEmployeeDAO().getProjects(e);
            projects.removeIf(p -> daos.getProjectDAO().getEmployeeQuitAt(p, e) != null);
            model.addAttribute("projects", projects);
            return "home";
        }
    }

}
