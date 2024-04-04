package moncef.projava.projectmonsifmehdi.controller;

import moncef.projava.projectmonsifmehdi.model.Rental;
import moncef.projava.projectmonsifmehdi.Repository.RentalRepository;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping("/index")
    public String findRentals(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "3") int size,
                              @RequestParam(name = "search", defaultValue = "") String search) {
        Page<Rental> pageRentals = rentalRepository.findByUserNameContainingIgnoreCase(search, PageRequest.of(page, size));
        int[] pages = new int[pageRentals.getTotalPages()];
        for (int i = 0; i < pages.length; i++)
            pages[i] = i;

        model.addAttribute("pageRentals", pageRentals.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", search);
        return "allRentals";
    }

    @GetMapping("/create")
    public String createRental(Model model) {
        Rental rental = new Rental();
        model.addAttribute("rental", rental);
        return "Formaddrental";
    }


    @PostMapping("/save")
    public String saveRental(Model model, Rental rental,
                             @RequestParam(name = "currentPage", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "3") int size,
                             @RequestParam(name = "search", defaultValue = "") String search) {
        // Ajoutez ici la logique de sauvegarde de la location
        rentalRepository.save(rental); // Save the rental object
        model.addAttribute("rental", rental); // Add rental object as model attribute
        return "redirect:/rentals/index?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/delete")
    public String deleteRental(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "search") String search,
            @RequestParam(name = "id") Long id) {
        rentalRepository.deleteById(id);
        return "redirect:/rentals/index?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping("/edit")
    public String editRental(Model model,
                             @RequestParam(name = "page") int page,
                             @RequestParam(name = "size") int size,
                             @RequestParam(name = "search") String search,
                             @RequestParam(name = "id") Long id) {
        Rental rental = rentalRepository.findById(id).orElse(null);
        if (rental == null) throw new RuntimeException("Erreur");
        model.addAttribute("rental", rental);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", search);

        return "formEditRental";
    }
}
