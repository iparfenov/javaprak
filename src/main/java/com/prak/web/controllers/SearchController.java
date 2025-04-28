package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String search(Model model) {
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("results", daos.getEmployeeDAO().getAll());
        return "search";
    }

    @PostMapping
    public String search(@RequestParam("search") String search, Model model) {
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("results", daos.getEmployeeDAO().search(search));
        return "search";
    }
}
