package com.exemplo.produtorrural.controller;

import com.exemplo.produtorrural.model.Visita;
import com.exemplo.produtorrural.model.Produtor;
import com.exemplo.produtorrural.repository.VisitaRepository;
import com.exemplo.produtorrural.repository.ProdutorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/visitas")
public class VisitaController {

    private final VisitaRepository visitaRepository;
    private final ProdutorRepository produtorRepository;

    public VisitaController(VisitaRepository visitaRepository, ProdutorRepository produtorRepository) {
        this.visitaRepository = visitaRepository;
        this.produtorRepository = produtorRepository;
    }

    @GetMapping
    public String lista(Model model){
        model.addAttribute("visitas", visitaRepository.findAll());
        return "visitas/lista";
    }

    @GetMapping("/novo")
    public String novoForm(@RequestParam(required = false) Long produtorId, Model model){
        Visita visita = new Visita();
        visita.setDataVisita(LocalDate.now());
        if(produtorId != null){
            Optional<Produtor> p = produtorRepository.findById(produtorId);
            p.ifPresent(visita::setProdutor);
            model.addAttribute("produtorId", produtorId);
        }
        model.addAttribute("visita", visita);
        model.addAttribute("produtores", produtorRepository.findAll());
        return "visitas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Visita visita){
        if(visita.getProdutor() != null && visita.getProdutor().getId() != null){
            Optional<Produtor> p = produtorRepository.findById(visita.getProdutor().getId());
            p.ifPresent(visita::setProdutor);
        }
        visitaRepository.save(visita);
        return "redirect:/visitas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        visitaRepository.deleteById(id);
        return "redirect:/visitas";
    }
}
