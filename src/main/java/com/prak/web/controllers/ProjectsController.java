package com.prak.web.controllers;

import com.prak.web.DAO.EmployeeDAO;
import com.prak.web.DAO.ProjectDAO;
import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.util.Calendar;
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
        model.addAttribute("employee", daos.getEmployee());
        return "projects";
    }

    @GetMapping("/add")
    public RedirectView addProject(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("employees", daos.getEmployeeDAO().getAll());
        return new RedirectView("/projects");
    }

    @PostMapping("/add")
    public String createProject(@RequestParam("name") String name,
                                @RequestParam("head") int head,
                                @RequestParam("start_year") int startYear,
                                @RequestParam("start_month") int startMonth,
                                @RequestParam("start_day") int startDay,
                                @RequestParam("end_year") int endYear,
                                @RequestParam("end_month") int endMonth,
                                @RequestParam("end_day") int endDay,
                                Model model) {
        Projects newProject = new Projects();
        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, startMonth, startDay);
        newProject.setStart(new Timestamp(startDate.getTimeInMillis()));

        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, endMonth, endDay);
        newProject.setEnd(new Timestamp(endDate.getTimeInMillis()));

        newProject.setName(name);
        newProject.setHead(daos.getEmployeeDAO().getById(head));

        try {
            daos.getProjectDAO().create(newProject);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return projects(model);
        }
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String project_page(@PathVariable("id") int id, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        ProjectDAO pdao = daos.getProjectDAO();
        Projects p = pdao.getById(id);
        if (p == null) return "redirect:/projects";

        model.addAttribute("project", p);
        model.addAttribute("pdao", pdao);
        model.addAttribute("employee", daos.getEmployee());
        return "project_page";
    }

    @PostMapping("/{id}/deleteFromProject")
    public String deleteFromProject(@PathVariable("id") int id, @RequestParam("id") int eid, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        EmployeeDAO edao = daos.getEmployeeDAO();
        ProjectDAO pdao = daos.getProjectDAO();
        try {
            edao.removeFromProject(edao.getById(eid), pdao.getById(id), null);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return project_page(id, model);
        }
        return project_page(id, model);
    }

    @GetMapping("/{id}/addToProject")
    public String addToProject(@PathVariable("id") int id, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
//        model.addAttribute("project", daos.getProjectDAO().getById(id));
//        model.addAttribute("pdao", daos.getProjectDAO());
//        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("employees", daos.getEmployeeDAO().getAll());
        return project_page(id, model);
//        return "project_page";
    }

    @PostMapping("/addToProject")
    public String addEToProject(@RequestParam("id") int id, @RequestParam("eid") int eid, @RequestParam("position") String position, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        EmployeeDAO edao = daos.getEmployeeDAO();
        Projects p = daos.getProjectDAO().getById(id);
        try {
            edao.addToProject(edao.getById(eid), p, position, null);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return project_page(id, model);
        }
        return project_page(id, model);
//        return "redirect:/projects/" + id;
    }

}
