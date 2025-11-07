package com.exemplo.produtorrural.controller;

import com.exemplo.produtorrural.model.Usuario;
import com.exemplo.produtorrural.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("rolesDisponiveis", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
        return "usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario,
                        BindingResult result,
                        @RequestParam(required = false) String[] roles,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        
        // Validação de username único
        if (usuarioService.usernameJaExiste(usuario.getUsername())) {
            result.rejectValue("username", "error.usuario", "Este nome de usuário já existe");
        }

        // Validação de senha obrigatória para novo usuário
        if (usuario.getId() == null && (usuario.getPassword() == null || usuario.getPassword().isEmpty())) {
            result.rejectValue("password", "error.usuario", "A senha é obrigatória");
        }

        if (result.hasErrors()) {
            model.addAttribute("rolesDisponiveis", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
            return "usuarios/form";
        }

        // Define as roles selecionadas
        if (roles != null && roles.length > 0) {
            usuario.setRoles(new HashSet<>(Arrays.asList(roles)));
        } else {
            // Se nenhuma role foi selecionada, adiciona ROLE_USER por padrão
            usuario.addRole("ROLE_USER");
        }

        usuarioService.salvar(usuario);
        redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("rolesDisponiveis", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
                    return "usuarios/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Usuário não encontrado");
                    return "redirect:/usuarios";
                });
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                           @Valid @ModelAttribute Usuario usuario,
                           BindingResult result,
                           @RequestParam(required = false) String[] roles,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        
        // Validação de username único (exceto o próprio usuário)
        if (usuarioService.usernameJaExiste(usuario.getUsername(), id)) {
            result.rejectValue("username", "error.usuario", "Este nome de usuário já existe");
        }

        if (result.hasErrors()) {
            usuario.setId(id);
            model.addAttribute("rolesDisponiveis", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
            return "usuarios/form";
        }

        // Define as roles selecionadas
        if (roles != null && roles.length > 0) {
            usuario.setRoles(new HashSet<>(Arrays.asList(roles)));
        } else {
            usuario.addRole("ROLE_USER");
        }

        usuarioService.atualizar(id, usuario);
        redirectAttributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, 
                         Authentication authentication,
                         RedirectAttributes redirectAttributes) {
        
        // Impede que o usuário exclua a si mesmo
        Usuario usuarioLogado = usuarioService.buscarPorUsername(authentication.getName()).orElse(null);
        if (usuarioLogado != null && usuarioLogado.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("erro", "Você não pode excluir seu próprio usuário!");
            return "redirect:/usuarios";
        }

        usuarioService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/alterar-senha/{id}")
    public String exibirAlterarSenha(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    return "usuarios/alterar-senha";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Usuário não encontrado");
                    return "redirect:/usuarios";
                });
    }

    @PostMapping("/alterar-senha/{id}")
    public String alterarSenha(@PathVariable Long id,
                              @RequestParam String novaSenha,
                              @RequestParam String confirmarSenha,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        
        // Validação de senhas
        if (novaSenha == null || novaSenha.length() < 4) {
            redirectAttributes.addFlashAttribute("erro", "A senha deve ter pelo menos 4 caracteres");
            return "redirect:/usuarios/alterar-senha/" + id;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("erro", "As senhas não coincidem");
            return "redirect:/usuarios/alterar-senha/" + id;
        }

        try {
            // Admin pode alterar senha de qualquer usuário
            usuarioService.alterarSenhaAdmin(id, novaSenha);
            redirectAttributes.addFlashAttribute("mensagem", "Senha alterada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao alterar senha: " + e.getMessage());
            return "redirect:/usuarios/alterar-senha/" + id;
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/minha-senha")
    public String exibirMinhaSenha(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        return usuarioService.buscarPorUsername(authentication.getName())
                .map(usuario -> {
                    model.addAttribute("usuario", usuario);
                    return "usuarios/minha-senha";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Usuário não encontrado");
                    return "redirect:/";
                });
    }

    @PostMapping("/minha-senha")
    public String alterarMinhaSenha(@RequestParam String senhaAtual,
                                   @RequestParam String novaSenha,
                                   @RequestParam String confirmarSenha,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        
        // Validação de senhas
        if (novaSenha == null || novaSenha.length() < 4) {
            redirectAttributes.addFlashAttribute("erro", "A senha deve ter pelo menos 4 caracteres");
            return "redirect:/usuarios/minha-senha";
        }

        if (!novaSenha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("erro", "As senhas não coincidem");
            return "redirect:/usuarios/minha-senha";
        }

        try {
            Usuario usuario = usuarioService.buscarPorUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            usuarioService.alterarSenha(usuario.getId(), senhaAtual, novaSenha);
            redirectAttributes.addFlashAttribute("mensagem", "Senha alterada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/usuarios/minha-senha";
        }

        return "redirect:/";
    }
}
