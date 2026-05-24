package com.example.biblioteca.controller;

import com.example.biblioteca.model.Categoria;
import com.example.biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador per gestionar el CRUD complet de Categoria.
 * Segueix el mateix patró que LlibreController i LecturaController.
 */
@Controller
@RequestMapping("/categories")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // ─── LLISTAT ────────────────────────────────────────────────────────────────

    /**
     * Mostra la llista de totes les categories.
     * GET /categories
     */
    @GetMapping
    public String llistar(Model model) {
        model.addAttribute("categories", categoriaService.llistarTots());
        return "categories/list";
    }

    // ─── CREAR ──────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per crear una nova categoria.
     * GET /categories/nova
     */
    @GetMapping("/nova")
    public String mostrarFormulariNova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categories/form";
    }

    /**
     * Processa el formulari de creació d'una nova categoria.
     * POST /categories/nova
     */
    @PostMapping("/nova")
    public String guardarNova(@Valid @ModelAttribute("categoria") Categoria categoria,
                              BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "categories/form";
        }
        categoriaService.guardar(categoria);
        return "redirect:/categories";
    }

    // ─── EDITAR ─────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per editar una categoria existent.
     * GET /categories/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormulariEditar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.buscarPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria no trobada: " + id));
        model.addAttribute("categoria", categoria);
        return "categories/form";
    }

    /**
     * Processa el formulari d'edició d'una categoria existent.
     * POST /categories/editar/{id}
     */
    @PostMapping("/editar/{id}")
    public String guardarEdicio(@PathVariable Long id,
                                @Valid @ModelAttribute("categoria") Categoria categoria,
                                BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "categories/form";
        }
        categoria.setId(id);
        categoriaService.guardar(categoria);
        return "redirect:/categories";
    }

    // ─── ELIMINAR ───────────────────────────────────────────────────────────────

    /**
     * Elimina una categoria i redirigeix al llistat.
     * GET /categories/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return "redirect:/categories";
    }
}
