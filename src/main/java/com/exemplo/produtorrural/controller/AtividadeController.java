package com.exemplo.produtorrural.controller;

import com.exemplo.produtorrural.model.Atividade;
import com.exemplo.produtorrural.model.Produtor;
import com.exemplo.produtorrural.repository.AtividadeRepository;
import com.exemplo.produtorrural.repository.ProdutorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/atividades")
public class AtividadeController {

    private final AtividadeRepository atividadeRepository;
    private final ProdutorRepository produtorRepository;

    public AtividadeController(AtividadeRepository atividadeRepository, ProdutorRepository produtorRepository) {
        this.atividadeRepository = atividadeRepository;
        this.produtorRepository = produtorRepository;
    }

    @GetMapping
    public String lista(Model model){
        model.addAttribute("atividades", atividadeRepository.findAll());
        return "atividades/lista";
    }

    @GetMapping("/novo")
    public String novoForm(@RequestParam(required = false) Long produtorId, Model model){
        Atividade atividade = new Atividade();
        if(produtorId != null){
            Optional<Produtor> p = produtorRepository.findById(produtorId);
            p.ifPresent(atividade::setProdutor);
            model.addAttribute("produtorId", produtorId);
        }
        model.addAttribute("atividade", atividade);
        model.addAttribute("produtores", produtorRepository.findAll());
        return "atividades/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Atividade atividade){
        if(atividade.getProdutor() != null && atividade.getProdutor().getId() != null){
            Optional<Produtor> p = produtorRepository.findById(atividade.getProdutor().getId());
            p.ifPresent(atividade::setProdutor);
        }
        atividadeRepository.save(atividade);
        return "redirect:/atividades";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        atividadeRepository.deleteById(id);
        return "redirect:/atividades";
    }
}
