package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/add")
    public String showForm(Model model) {
        System.out.println("Get cos tam");
        List<Category> categories = categoryService.getAllCategorys();
        List<Institution> institutions = institutionService.getAllInstitutions();
        Donation donation = new Donation();
        model.addAttribute("categories", categories);
        model.addAttribute("institutions", institutions);
        model.addAttribute("donation", donation);
        return "donation-form";
    }

    @PostMapping("/add")
    public String processForm(@ModelAttribute("donation") Donation donation) {
        System.out.println("Cos tam" + donation.getCity());
        donationService.saveDonation(donation);
        return "redirect:/donation/summary-before-submit";
    }

    @GetMapping("/summary-before-submit")
    public String showSummaryBeforeSubmit(Model model, @ModelAttribute("donation") Donation donation) {
        return "donation-summary-before-submit";
    }

    @GetMapping("/summary")
    public String showSummary(Model model, @ModelAttribute("donation") Donation donation) {
        return "donation-summary";
    }
}
