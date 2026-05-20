package com.example.biblioteca.controller;

import com.example.biblioteca.model.EstatLectura;
import com.example.biblioteca.service.LecturaService;
import com.example.biblioteca.service.LlibreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador per a la pàgina d'inici.
 * Passa les estadístiques globals a la vista index.html.
 */
@Controller
public class IndexController {

    private final LlibreService llibreService;
    private final LecturaService lecturaService;

    public IndexController(LlibreService llibreService, LecturaService lecturaService) {
        this.llibreService = llibreService;
        this.lecturaService = lecturaService;
    }

    /**
     * GET / → pàgina d'inici amb estadístiques.
     */
    @GetMapping("/")
    public String inici(Model model) {
        // Estadístiques de la col·lecció
        model.addAttribute("totalLlibres", llibreService.comptar());
        model.addAttribute("totalLlegits", lecturaService.comptarPerEstat(EstatLectura.LLEGIT));
        model.addAttribute("totalLlegint", lecturaService.comptarPerEstat(EstatLectura.LLEGINT));
        model.addAttribute("totalPendent", lecturaService.comptarPerEstat(EstatLectura.PENDENT));
        return "index";
    }
}
