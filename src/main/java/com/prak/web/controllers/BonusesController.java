package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Bonuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bonuses")
public class BonusesController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String bonuses(Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("bonuses", daos.getBonusesDAO().getAll());
        return "bonuses";
    }

    @GetMapping("/{id}")
    public String bonuses(@PathVariable("id") int id, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("bonuses", daos.getBonusesDAO().getAll());
        model.addAttribute("id", id);
        return "bonuses";
    }

    @GetMapping("/add")
    public String addBonus(Model model) {
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("bonuses", daos.getBonusesDAO().getAll());
        model.addAttribute("add", true);
        return "bonuses";
    }

    @PostMapping("/add")
    public String addBonus(@RequestParam("name") String name, @RequestParam("percentage") String percentage, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        Bonuses new_bonus = new Bonuses();
        new_bonus.setName(name);
        new_bonus.setPercentage(Float.parseFloat(percentage));
        try {
            daos.getBonusesDAO().insert(new_bonus);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return bonuses(model);
        }
        return "redirect:/bonuses";
    }
}
