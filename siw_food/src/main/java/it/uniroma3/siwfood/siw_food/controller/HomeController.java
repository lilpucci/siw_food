package it.uniroma3.siwfood.siw_food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    
    //restituisce il template della welcome page
    @GetMapping("")
    public String getHomePage() {
        return "home.html";
    }
    
}
