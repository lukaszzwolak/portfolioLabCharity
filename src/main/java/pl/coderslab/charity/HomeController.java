package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;

    @Autowired
    public HomeController(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @GetMapping
    public String home(Model model) {
        Long totalBagsCount = donationRepository.getTotalBagsCount();
        Long totalGiftsCount = donationRepository.getTotalGiftsCount();
        model.addAttribute("totalBagsCount", totalBagsCount);
        model.addAttribute("totalGiftsCount", totalGiftsCount);
        return "index";
    }

    @GetMapping("/fundations")
    public String showFundations(Model model) {
        model.addAttribute("institutions", institutionRepository.findAll());
        return "fundations";
    }
}
