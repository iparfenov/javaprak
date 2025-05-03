package com.prak.web.controllers;

import com.prak.web.components.DAOsBean;
import com.prak.web.entities.Payouts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payouts")
public class PayoutsController {

    @Autowired
    DAOsBean daos;

    @GetMapping
    public String payouts(Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        if (!daos.getEmployee().getIs_admin()) return "redirect:/home";
        model.addAttribute("employee", daos.getEmployee());
        model.addAttribute("employees", daos.getEmployeeDAO().getAll());
        model.addAttribute("bonuses", daos.getBonusesDAO().getAll());
        model.addAttribute("payouts", daos.getPayoutsDAO().getAll());
        return "payouts";
    }

    @PostMapping("/add")
    public String add(@RequestParam("eid") int eid, @RequestParam("amount") String amount, @RequestParam("bonus") int bid, Model model) {
        if (daos.getEmployee() == null) return "redirect:/login";
        if (!daos.getEmployee().getIs_admin()) return "redirect:/home";
        Payouts new_payout = new Payouts();
        new_payout.setEmployee(daos.getEmployeeDAO().getById(eid));
        if (bid >= 0) {
            new_payout.setBonus(daos.getBonusesDAO().getById(bid));
        } else {
            new_payout.setBonus(null);
        }
        try {
            new_payout.setAmount(Float.parseFloat(amount));
            daos.getPayoutsDAO().insert(new_payout);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return payouts(model);
        }
        return "redirect:/payouts";
    }
}
