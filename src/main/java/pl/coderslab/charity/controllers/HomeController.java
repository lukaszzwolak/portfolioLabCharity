package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;

    @Autowired
    public HomeController(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @GetMapping
    public String index(Model model) {
        Long totalBagsCount = donationRepository.getTotalBagsCount();
        Long totalGiftsCount = donationRepository.getTotalGiftsCount();
        List<Institution> institutions = institutionRepository.findAll();
        model.addAttribute("totalBagsCount", totalBagsCount);
        model.addAttribute("totalGiftsCount", totalGiftsCount);
        model.addAttribute("institutions", institutions);
        return "home/index";
    }
}
