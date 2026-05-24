package com.example.biblioteca.controller;

import com.example.biblioteca.model.EstatLectura;
import com.example.biblioteca.model.Lectura;
import com.example.biblioteca.service.LecturaService;
import com.example.biblioteca.service.LlibreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador per gestionar el CRUD complet de Lectura.
 * Segueix la mateixa estructura que LlibreController.
 */
@Controller
@RequestMapping("/lectures")
public class LecturaController {

    // Injectem els serveis necessaris per al formulari:
    // LecturaService per a les operacions CRUD
    // LlibreService per carregar el selector de llibres al formulari
    private final LecturaService lecturaService;
    private final LlibreService llibreService;

    public LecturaController(LecturaService lecturaService, LlibreService llibreService) {
        this.lecturaService = lecturaService;
        this.llibreService = llibreService;
    }

    // ─── LLISTAT ────────────────────────────────────────────────────────────────

    /**
     * Mostra la llista de totes les lectures registrades.
     * GET /lectures
     */
    @GetMapping
    public String llistar(Model model) {
        model.addAttribute("lectures", lecturaService.llistarTots());
        return "lectures/list";
    }

    // ─── CREAR ──────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per registrar una nova lectura.
     * GET /lectures/nova
     */
    @GetMapping("/nova")
    public String mostrarFormulariNova(Model model) {
        model.addAttribute("lectura", new Lectura());
        // Passem tots els llibres per al selector del formulari
        model.addAttribute("totslLlibres", llibreService.llistarTots());
        // Passem els valors de l'enum perquè Thymeleaf els pugui mostrar
        model.addAttribute("estats", EstatLectura.values());
        return "lectures/form";
    }

    /**
     * Processa el formulari de creació d'una nova lectura.
     * POST /lectures/nova
     */
    @PostMapping("/nova")
    public String guardarNova(@Valid @ModelAttribute("lectura") Lectura lectura,
                              BindingResult resultat,
                              Model model) {
        if (resultat.hasErrors()) {
            model.addAttribute("totslLlibres", llibreService.llistarTots());
            model.addAttribute("estats", EstatLectura.values());
            return "lectures/form";
        }
        lecturaService.guardar(lectura);
        return "redirect:/lectures";
    }

    // ─── EDITAR ─────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per editar una lectura existent.
     * GET /lectures/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormulariEditar(@PathVariable Long id, Model model) {
        Lectura lectura = lecturaService.buscarPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Lectura no trobada: " + id));
        model.addAttribute("lectura", lectura);
        model.addAttribute("totslLlibres", llibreService.llistarTots());
        model.addAttribute("estats", EstatLectura.values());
        return "lectures/form";
    }

    /**
     * Processa el formulari d'edició d'una lectura existent.
     * POST /lectures/editar/{id}
     */
    @PostMapping("/editar/{id}")
    public String guardarEdicio(@PathVariable Long id,
                                @Valid @ModelAttribute("lectura") Lectura lectura,
                                BindingResult resultat,
                                Model model) {
        if (resultat.hasErrors()) {
            model.addAttribute("totslLlibres", llibreService.llistarTots());
            model.addAttribute("estats", EstatLectura.values());
            return "lectures/form";
        }
        // Assignem l'id perquè JPA faci UPDATE i no INSERT
        lectura.setId(id);
        lecturaService.guardar(lectura);
        return "redirect:/lectures";
    }

    // ─── ELIMINAR ───────────────────────────────────────────────────────────────

    /**
     * Elimina una lectura i redirigeix al llistat.
     * GET /lectures/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        lecturaService.eliminar(id);
        return "redirect:/lectures";
    }
}
