package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bonuses")
public class BonusesController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String bonuses(Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        model.addAttribute("bonuses", daos.getBonusesDAO().getAll());
        return "bonuses";
    }

    @GetMapping("/{id}")
    public String bonuses(@PathVariable("id") int id, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        model.addAttribute("bonuses", daos.getBonusesDAO().getAll());
        model.addAttribute("id", id);
        return "bonuses";
    }
}
