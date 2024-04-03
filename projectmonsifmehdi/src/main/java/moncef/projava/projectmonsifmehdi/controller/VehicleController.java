package moncef.projava.projectmonsifmehdi.controller;

import moncef.projava.projectmonsifmehdi.Repository.VehicleRepository;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller

public class VehicleController {
    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping(path="/index")
    public String findcars(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "3") int size,
                           @RequestParam(name="search", defaultValue = "") String searchName){

        Page<Vehicle> pageVehicules = vehicleRepository.findByMakeContains(searchName, PageRequest.of(page,size));


        int[] pages = new int[pageVehicules.getTotalPages()];
        for(int i=0; i<pages.length; i++)
            pages[i]=i;


        model.addAttribute("pageVehicules",pageVehicules.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        return "allCars";
    }
    @GetMapping(path="/create")
    public String createVehicule(Model model){
        Vehicle vehicle = new Vehicle();
        model.addAttribute("Vehicle", vehicle);
        return "Formaddvehicule";

    }
    @PostMapping(path = "/save")
    public String saveStudent(Model model, Vehicle s,
                              @RequestParam(name="currentPage", defaultValue = "0") int page,
                              @RequestParam(name="size", defaultValue = "3") int size,
                              @RequestParam(name="searchName", defaultValue = "") String search){
        vehicleRepository.save(s);
        return "redirect:/index?page="+page+"&size="+size+"&search="+search;

    }
    @GetMapping(path = "/")
    public String homePage(Model model, @RequestParam(name = "filter", defaultValue = "All") int filter) {
        List<Vehicle> vehicles;
        if (filter == 1) {
            vehicles = vehicleRepository.findByRented(true);
        } else if (filter == 0) {
            vehicles = vehicleRepository.findByRented(false);
        } else {
            vehicles = vehicleRepository.findAll();
        }
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("filter", filter); // Add the selected filter to the model
        return "/Menu";
    }



    @GetMapping(path="/delete")
    public String deletevehicul(
            int page, int size, String search,
            @RequestParam(name="id") Long id){
        vehicleRepository.deleteById(id);

        return "redirect:/index?page="+page+"&size="+size+"&search="+search;
    }

    @GetMapping(path = "/edit")
    public String editStudent(Model model , int page, int size, String search, long id){
        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);
        if(vehicle == null) throw new RuntimeException("Erreur");
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", search);

        return "Formeditvehicule";
    }
}

