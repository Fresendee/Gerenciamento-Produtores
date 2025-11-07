package com.exemplo.produtorrural.service;

import com.exemplo.produtorrural.model.Usuario;
import com.exemplo.produtorrural.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        // Se a senha foi fornecida (não está vazia), criptografa
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setUsername(usuarioAtualizado.getUsername());
        usuario.setEnabled(usuarioAtualizado.isEnabled());
        usuario.setRoles(usuarioAtualizado.getRoles());

        // Só atualiza a senha se uma nova foi fornecida
        if (usuarioAtualizado.getPassword() != null && !usuarioAtualizado.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioAtualizado.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha atual está correta
        if (!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        // Atualiza para a nova senha
        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenhaAdmin(Long id, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean usernameJaExiste(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public boolean usernameJaExiste(String username, Long idExcluir) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        return usuario.isPresent() && !usuario.get().getId().equals(idExcluir);
    }
}
