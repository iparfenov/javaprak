package com.prak.web.controllers;

import com.prak.web.DAO.ProjectDAO;
import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@SessionAttributes("employee")
public class ProjectsController {

    @Autowired
    private DAOsBean daos;

    @GetMapping
    public String projects(Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        List<Projects> projects = daos.getProjectDAO().getAll();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/{id}")
    public String project_page(@PathVariable("id") int id, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        ProjectDAO pdao = daos.getProjectDAO();
        Projects p = pdao.getById(id);
        if (p == null) return "redirect:/projects";

        model.addAttribute("project", p);
        return "project_page";
    }
}
