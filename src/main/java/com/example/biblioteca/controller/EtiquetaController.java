package com.example.biblioteca.controller;

import com.example.biblioteca.model.Etiqueta;
import com.example.biblioteca.service.EtiquetaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador per gestionar el CRUD d'Etiqueta.
 * Segueix el mateix patró que la resta de controladors de l'aplicació.
 */
@Controller
@RequestMapping("/etiquetes")
public class EtiquetaController {

    private final EtiquetaService etiquetaService;

    public EtiquetaController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    // ─── LLISTAT ────────────────────────────────────────────────────────────────

    /**
     * Mostra la llista de totes les etiquetes.
     * GET /etiquetes
     */
    @GetMapping
    public String llistar(Model model) {
        model.addAttribute("etiquetes", etiquetaService.llistarTots());
        return "etiquetes/list";
    }

    // ─── CREAR ──────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per crear una nova etiqueta.
     * GET /etiquetes/nova
     */
    @GetMapping("/nova")
    public String mostrarFormulariNova(Model model) {
        model.addAttribute("etiqueta", new Etiqueta());
        return "etiquetes/form";
    }

    /**
     * Processa el formulari de creació d'una nova etiqueta.
     * POST /etiquetes/nova
     */
    @PostMapping("/nova")
    public String guardarNova(@Valid @ModelAttribute("etiqueta") Etiqueta etiqueta,
                              BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "etiquetes/form";
        }
        etiquetaService.guardar(etiqueta);
        return "redirect:/etiquetes";
    }

    // ─── EDITAR ─────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per editar una etiqueta existent.
     * GET /etiquetes/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormulariEditar(@PathVariable Long id, Model model) {
        Etiqueta etiqueta = etiquetaService.buscarPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Etiqueta no trobada: " + id));
        model.addAttribute("etiqueta", etiqueta);
        return "etiquetes/form";
    }

    /**
     * Processa el formulari d'edició d'una etiqueta existent.
     * POST /etiquetes/editar/{id}
     */
    @PostMapping("/editar/{id}")
    public String guardarEdicio(@PathVariable Long id,
                                @Valid @ModelAttribute("etiqueta") Etiqueta etiqueta,
                                BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "etiquetes/form";
        }
        etiqueta.setId(id);
        etiquetaService.guardar(etiqueta);
        return "redirect:/etiquetes";
    }

    // ─── ELIMINAR ───────────────────────────────────────────────────────────────

    /**
     * Elimina una etiqueta i redirigeix al llistat.
     * GET /etiquetes/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        etiquetaService.eliminar(id);
        return "redirect:/etiquetes";
    }
}
