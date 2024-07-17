package pl.coderslab.charity.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.services.CategoryService;
import pl.coderslab.charity.services.DonationService;
import pl.coderslab.charity.services.InstitutionService;

import java.util.List;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private InstitutionService institutionService;

    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryService.getAllCategorys();
        List<Institution> institutions = institutionService.getAllInstitutions();
        Donation donation = new Donation();
        model.addAttribute("categories", categories);
        model.addAttribute("institutions", institutions);
        model.addAttribute("donation", donation);
        return "donation/index";
    }

    @PostMapping
    public String create(@Valid Donation donation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Category> categories = categoryService.getAllCategorys();
            List<Institution> institutions = institutionService.getAllInstitutions();
            model.addAttribute("categories", categories);
            model.addAttribute("institutions", institutions);
            return "donation/index";
        }
        donationService.saveDonation(donation);
        return "redirect:donation/form-confirmation";
    }
}
