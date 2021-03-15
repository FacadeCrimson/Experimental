package com.simon.dynamic.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${welcome.message}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("greeting", message);
        return "index";
    }

    @RequestMapping("/artistregister")
    public String artistRegister(Model model) {
        return "ArtistRegister";
    }

    @RequestMapping("/venueregister")
    public String venueRegister(Model model) {
        return "VenueRegister";
    }

    @RequestMapping("/showregister")
    public String showRegister(Model model) {
        return "ShowRegister";
    }
}
