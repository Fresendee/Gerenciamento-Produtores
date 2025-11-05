package com.exemplo.produtorrural.controller;

import com.exemplo.produtorrural.model.Produtor;
import com.exemplo.produtorrural.repository.ProdutorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/produtores")
public class ProdutorController {

    private final ProdutorRepository produtorRepository;

    public ProdutorController(ProdutorRepository produtorRepository) {
        this.produtorRepository = produtorRepository;
    }

    @GetMapping
    public String lista(Model model){
        model.addAttribute("produtores", produtorRepository.findAll());
        return "produtores/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model){
        model.addAttribute("produtor", new Produtor());
        return "produtores/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("produtor") @Valid Produtor produtor, BindingResult result){
        if(result.hasErrors()){
            return "produtores/form";
        }
        produtorRepository.save(produtor);
        return "redirect:/produtores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Optional<Produtor> opt = produtorRepository.findById(id);
        if(opt.isPresent()){
            model.addAttribute("produtor", opt.get());
            return "produtores/form";
        }
        return "redirect:/produtores";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        produtorRepository.deleteById(id);
        return "redirect:/produtores";
    }
}
