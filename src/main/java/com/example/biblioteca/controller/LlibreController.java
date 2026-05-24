package com.example.biblioteca.controller;

import com.example.biblioteca.model.Llibre;
import com.example.biblioteca.service.CategoriaService;
import com.example.biblioteca.service.LecturaService;
import com.example.biblioteca.service.LlibreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador per gestionar el CRUD complet de Llibre.
 * Utilitza Thymeleaf per renderitzar les vistes.
 */
@Controller
@RequestMapping("/llibres")
public class LlibreController {

    private final LlibreService llibreService;
    private final CategoriaService categoriaService;
    private final LecturaService lecturaService;

    public LlibreController(LlibreService llibreService, CategoriaService categoriaService,
                            LecturaService lecturaService) {
        this.llibreService = llibreService;
        this.categoriaService = categoriaService;
        this.lecturaService = lecturaService;
    }

    // ─── LLISTAT ────────────────────────────────────────────────────────────────

    /**
     * Mostra la llista de llibres, amb filtre opcional per títol/autor.
     * GET /llibres o GET /llibres?cerca=tolkien
     */
    @GetMapping
    public String llistar(@RequestParam(required = false) String cerca, Model model) {
        // Si hi ha text de cerca, filtrem; si no, retornem tots
        model.addAttribute("llibres", llibreService.cercar(cerca));
        model.addAttribute("cerca", cerca != null ? cerca : "");
        return "llibres/list";
    }

    /**
     * Mostra el detall d'un llibre amb les seves lectures associades.
     * GET /llibres/{id}
     */
    @GetMapping("/{id}")
    public String detall(@PathVariable Long id, Model model) {
        Llibre llibre = llibreService.buscarPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Llibre no trobat: " + id));
        model.addAttribute("llibre", llibre);
        // Lectures associades a aquest llibre concretes
        model.addAttribute("lectures", lecturaService.llistarPerLlibre(id));
        return "llibres/detall";
    }

    // ─── CREAR ──────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per crear un nou llibre.
     * GET /llibres/nou
     */
    @GetMapping("/nou")
    public String mostrarFormulariNou(Model model) {
        // Passem un objecte buit perquè Thymeleaf el pugui fer servir al formulari
        model.addAttribute("llibre", new Llibre());
        // Passem totes les categories per al selector múltiple
        model.addAttribute("toutesCategories", categoriaService.llistarTots());
        return "llibres/form";
    }

    /**
     * Processa el formulari de creació d'un nou llibre.
     * POST /llibres/nou
     *
     * @Valid activa les validacions de l'entitat (@NotBlank, etc.)
     * BindingResult conté els errors de validació si n'hi ha
     */
    @PostMapping("/nou")
    public String guardarNou(@Valid @ModelAttribute("llibre") Llibre llibre,
                             BindingResult resultat,
                             Model model) {
        // Si hi ha errors de validació, tornem al formulari mostrant-los
        if (resultat.hasErrors()) {
            model.addAttribute("toutesCategories", categoriaService.llistarTots());
            return "llibres/form";
        }
        llibreService.guardar(llibre);
        // Redirigim al llistat per evitar doble enviament del formulari
        return "redirect:/llibres";
    }

    // ─── EDITAR ─────────────────────────────────────────────────────────────────

    /**
     * Mostra el formulari per editar un llibre existent.
     * GET /llibres/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormulariEditar(@PathVariable Long id, Model model) {
        // Busquem el llibre per id; si no existeix llancem excepció
        Llibre llibre = llibreService.buscarPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Llibre no trobat: " + id));
        model.addAttribute("llibre", llibre);
        model.addAttribute("toutesCategories", categoriaService.llistarTots());
        return "llibres/form";
    }

    /**
     * Processa el formulari d'edició d'un llibre existent.
     * POST /llibres/editar/{id}
     */
    @PostMapping("/editar/{id}")
    public String guardarEdicio(@PathVariable Long id,
                                @Valid @ModelAttribute("llibre") Llibre llibre,
                                BindingResult resultat,
                                Model model) {
        if (resultat.hasErrors()) {
            model.addAttribute("toutesCategories", categoriaService.llistarTots());
            return "llibres/form";
        }
        // Assignem l'id perquè JPA faci UPDATE i no INSERT
        llibre.setId(id);
        llibreService.guardar(llibre);
        return "redirect:/llibres";
    }

    // ─── ELIMINAR ───────────────────────────────────────────────────────────────

    /**
     * Elimina un llibre i redirigeix al llistat.
     * GET /llibres/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        llibreService.eliminar(id);
        return "redirect:/llibres";
    }
}
