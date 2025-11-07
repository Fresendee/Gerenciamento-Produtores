package com.exemplo.produtorrural.config;

import com.exemplo.produtorrural.model.Usuario;
import com.exemplo.produtorrural.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário admin padrão se não existir
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.addRole("ROLE_ADMIN");
            admin.addRole("ROLE_USER");
            usuarioRepository.save(admin);
            System.out.println("✓ Usuário admin criado com sucesso!");
            System.out.println("  Username: admin");
            System.out.println("  Password: admin123");
        }

        // Criar usuário comum padrão se não existir
        if (!usuarioRepository.existsByUsername("usuario")) {
            Usuario user = new Usuario();
            user.setUsername("usuario");
            user.setPassword(passwordEncoder.encode("senha123"));
            user.setEnabled(true);
            user.addRole("ROLE_USER");
            usuarioRepository.save(user);
            System.out.println("✓ Usuário comum criado com sucesso!");
            System.out.println("  Username: usuario");
            System.out.println("  Password: senha123");
        }
    }
}
