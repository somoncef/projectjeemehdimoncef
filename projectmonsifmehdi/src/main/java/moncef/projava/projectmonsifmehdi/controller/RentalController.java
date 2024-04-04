package moncef.projava.projectmonsifmehdi.controller;

import moncef.projava.projectmonsifmehdi.Repository.UserRepository;
import moncef.projava.projectmonsifmehdi.Repository.VehicleRepository;
import moncef.projava.projectmonsifmehdi.exeption.InvalidRentalDatesException;
import moncef.projava.projectmonsifmehdi.exeption.UserNotFoundException;
import moncef.projava.projectmonsifmehdi.exeption.VehicleNotFoundException;
import moncef.projava.projectmonsifmehdi.model.Rental;
import moncef.projava.projectmonsifmehdi.Repository.RentalRepository;
import moncef.projava.projectmonsifmehdi.model.User;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/index")
    public String findRentals(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "7") int size,
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
        List<Vehicle> vehicles = new ArrayList<>();
        if (vehicleRepository != null) {
            vehicles = vehicleRepository.findByRented(false);
        }
        List<User> users =new ArrayList<>();
        if (userRepository != null) {
            users = userRepository.findAll();
        }

        model.addAttribute("rental", rental);
        model.addAttribute("users", users);
        model.addAttribute("vehicles", vehicles);

        return "Formaddrental";
    }


    @PostMapping("/save")
    public String saveRental(Model model, Rental rental,
                             @RequestParam(name = "currentPage", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "7") int size,
                             @RequestParam(name = "search", defaultValue = "") String search) {
        // Vérifier si les dates de location et de retour sont valides
        Date currentDate = new Date();
        if (rental.getRentalDate().before(currentDate) || rental.getReturnDate().before(rental.getRentalDate())) {
            // Gérer l'erreur : dates de location ou de retour invalides
            throw new InvalidRentalDatesException("Rental dates should be in the future");
        }

        // Récupérer l'ID du véhicule et de l'utilisateur associés à la location
        Long vehicleId = rental.getVehicle().getId();
        Long userId = rental.getUser().getId();

        // Récupérer les entités du véhicule et de l'utilisateur à partir de la base de données
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Assurer que le véhicule et l'utilisateur sont correctement associés à la location
        vehicle.setRented(true);
        vehicle.getRentals().add(rental);
        rental.setVehicle(vehicle);

        // Calculer le coût total de la location
        rental.calculateTotalCost();

        // Associer la location à l'utilisateur
        user.getRentals().add(rental);
       

        // Sauvegarder d'abord l'utilisateur et le véhicule, puis la location dans la base de données
        userRepository.save(user);
        vehicleRepository.save(vehicle);
        rentalRepository.save(rental);

        // Rediriger vers la page d'index des locations avec les paramètres de pagination
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
    @GetMapping(path="/delete")
    public String deleterental(
            int page, int size, String search,
            @RequestParam(name="id") Long id){
        Rental rental = rentalRepository.findById(id).orElse(null);
        if (rental == null) {

            return "redirect:/error";
        }

        Long vehicleId = rental.getVehicle().getId();
        Long userId = rental.getUser().getId();
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        vehicle.setRented(true);
        vehicleRepository.save(vehicle);

        user.getRentals().remove(rental);
        userRepository.save(user);

        rentalRepository.deleteById(id);

        return "redirect:/rentals/index?page="+page+"&size="+size+"&search="+search;
    }

}
