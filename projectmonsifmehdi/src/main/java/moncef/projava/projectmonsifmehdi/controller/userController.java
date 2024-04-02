package moncef.projava.projectmonsifmehdi.controller;

import moncef.projava.projectmonsifmehdi.Repository.UserRepository;
import moncef.projava.projectmonsifmehdi.Repository.VehicleRepository;
import moncef.projava.projectmonsifmehdi.model.User;
import moncef.projava.projectmonsifmehdi.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/client")
public class userController {
    @Autowired
    UserRepository userRepository;
    @GetMapping(path="/index")
    public String findusers(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "3") int size,
                           @RequestParam(name="search", defaultValue = "") String searchName){

        Page<User> pageclients = userRepository.findBynameContains(searchName, PageRequest.of(page, size));


        int[] pages = new int[pageclients.getTotalPages()];
        for(int i=0; i<pages.length; i++)
            pages[i]=i;


        model.addAttribute("pageclients",pageclients.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        return "allUsers";
    }
    @GetMapping(path="/create")
    public String createUser(Model model){
        User user = new User();
        model.addAttribute("User", user);
        return "Formaddclient";

    }
    @PostMapping(path = "/save")
    public String saveStudent(Model model, User s,
                              @RequestParam(name="currentPage", defaultValue = "0") int page,
                              @RequestParam(name="size", defaultValue = "3") int size,
                              @RequestParam(name="searchName", defaultValue = "") String search){
        s.setRentals(null);
        userRepository.save(s);
        return "redirect:/client/index?page="+page+"&size="+size+"&search="+search;
    }
    @GetMapping(path = "/")
    public String homePage(){
        return "/Menu";
    }
    @GetMapping(path="/delete")
    public String deleteuser(
            int page, int size, String search,
            @RequestParam(name="id") Long id){
        userRepository.deleteById(id);

        return "redirect:/client/index?page="+page+"&size="+size+"&search="+search;
    }
    @GetMapping(path = "/edit")
    public String editStudent(Model model , int page, int size, String search, long id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null) throw new RuntimeException("Erreur");
        model.addAttribute("user", user);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", search);

        return "formeditclient";
    }
}
