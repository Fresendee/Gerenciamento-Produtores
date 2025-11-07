# Sistema de Autenticação - Gerenciamento de Produtores Rurais

## 📋 Visão Geral

Este documento descreve o sistema de autenticação com login e senha implementado no projeto de Gerenciamento de Produtores Rurais. O sistema utiliza **Spring Security** para proteger todas as páginas da aplicação, exigindo que os usuários façam login antes de acessar qualquer funcionalidade.

## 🔐 Funcionalidades Implementadas

### 1. Autenticação de Usuários
- Sistema completo de login e senha
- Proteção de todas as rotas da aplicação
- Página de login personalizada com design moderno
- Mensagens de erro e sucesso no login
- Logout seguro com invalidação de sessão

### 2. Gerenciamento de Usuários
- Modelo de dados `Usuario` com suporte a múltiplas roles (papéis)
- Senhas criptografadas com BCrypt
- Usuários podem ser habilitados/desabilitados
- Suporte a diferentes níveis de acesso (ADMIN, USER)

### 3. Segurança
- Todas as senhas são armazenadas criptografadas no banco de dados
- Proteção contra CSRF (Cross-Site Request Forgery)
- Sessões seguras com cookies HTTP-only
- Logout com limpeza completa de sessão

## 👥 Usuários Padrão

O sistema cria automaticamente dois usuários na primeira execução:

### Usuário Administrador
- **Username:** `admin`
- **Senha:** `admin123`
- **Roles:** ROLE_ADMIN, ROLE_USER
- **Descrição:** Acesso completo ao sistema

### Usuário Comum
- **Username:** `usuario`
- **Senha:** `senha123`
- **Roles:** ROLE_USER
- **Descrição:** Acesso padrão ao sistema

## 🏗️ Arquitetura Implementada

### Componentes Criados

#### 1. Modelo de Dados
**`Usuario.java`**
- Entidade JPA que representa um usuário do sistema
- Campos: id, username, password, enabled, roles
- Relacionamento com tabela `usuario_roles` para armazenar papéis

#### 2. Repositório
**`UsuarioRepository.java`**
- Interface Spring Data JPA para acesso aos dados de usuários
- Métodos personalizados: `findByUsername()`, `existsByUsername()`

#### 3. Serviço de Autenticação
**`CustomUserDetailsService.java`**
- Implementa `UserDetailsService` do Spring Security
- Carrega dados do usuário do banco de dados
- Converte roles em authorities do Spring Security

#### 4. Configuração de Segurança
**`SecurityConfig.java`**
- Configuração principal do Spring Security
- Define regras de acesso às URLs
- Configura página de login e logout
- Configura encoder de senha (BCrypt)

#### 5. Inicializador de Dados
**`DataInitializer.java`**
- Cria usuários padrão na primeira execução
- Implementa `CommandLineRunner` para execução automática

#### 6. Controlador de Login
**`LoginController.java`**
- Gerencia a página de login
- Exibe mensagens de erro e sucesso

#### 7. Templates Thymeleaf
**`login.html`**
- Página de login moderna e responsiva
- Design consistente com o resto da aplicação
- Exibe credenciais padrão para facilitar o primeiro acesso

**`index.html` (atualizado)**
- Adicionado botão de logout no cabeçalho
- Exibe nome do usuário logado
- Integração com Spring Security tags

## 🗄️ Estrutura do Banco de Dados

### Tabela: usuarios
```sql
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT true
);
```

### Tabela: usuario_roles
```sql
CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    role VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

## 🚀 Como Usar

### 1. Iniciar a Aplicação

```bash
# Certifique-se de que o PostgreSQL está rodando (porta 5433)
docker-compose up -d

# Compile e execute a aplicação
mvn spring-boot:run
```

### 2. Acessar o Sistema

1. Abra o navegador em: `http://localhost:8080`
2. Você será redirecionado automaticamente para a página de login
3. Use uma das credenciais padrão:
   - Admin: `admin` / `admin123`
   - Usuário: `usuario` / `senha123`
4. Após o login, você terá acesso ao dashboard e todas as funcionalidades

### 3. Fazer Logout

- Clique no botão "Sair" no canto superior direito do dashboard
- Você será redirecionado para a página de login

## 🔧 Configurações

### Dependências Adicionadas ao pom.xml

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Thymeleaf extras para Spring Security -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
```

### Regras de Acesso

Configuradas em `SecurityConfig.java`:

- **Permitido sem autenticação:**
  - `/css/**`, `/js/**`, `/images/**` (recursos estáticos)
  - `/login`, `/logout` (páginas de autenticação)

- **Requer autenticação:**
  - Todas as outras URLs (`/**`)

## 🎨 Interface de Login

A página de login foi desenvolvida com:
- Design moderno e responsivo
- Gradiente verde e marrom (cores do tema NPA)
- Ícones Material Design
- Mensagens de erro/sucesso destacadas
- Exibição das credenciais padrão para facilitar o primeiro acesso
- Campos de formulário com validação

## 🔒 Segurança das Senhas

As senhas são protegidas usando:
- **BCrypt**: Algoritmo de hash adaptativo
- **Salt automático**: Cada senha tem um salt único
- **Força configurável**: Padrão de 10 rounds
- **Armazenamento seguro**: Apenas o hash é armazenado no banco

Exemplo de senha criptografada:
```
Senha original: admin123
Hash BCrypt: $2a$10$X8Yt5vZQJ9K3mN2pL4wR1eH7sG6fD5cB4aT3rE2wQ1zX8yV7uI6oP
```

## 📝 Próximos Passos (Opcional)

Para expandir o sistema de autenticação, você pode:

1. **Criar tela de cadastro de usuários**
   - Formulário para administradores criarem novos usuários
   - Validação de senhas fortes

2. **Implementar recuperação de senha**
   - Envio de email com token de reset
   - Página para redefinir senha

3. **Adicionar controle de acesso por roles**
   - Restringir certas funcionalidades apenas para ADMIN
   - Usar anotações `@PreAuthorize` nos controllers

4. **Implementar auditoria**
   - Registrar tentativas de login
   - Log de ações dos usuários

5. **Adicionar "Lembrar-me"**
   - Checkbox na tela de login
   - Persistência de sessão por mais tempo

## 📚 Referências

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Spring Boot Security Tutorial](https://spring.io/guides/gs/securing-web/)
- [Thymeleaf + Spring Security Integration](https://www.thymeleaf.org/doc/articles/springsecurity.html)
- [BCrypt Password Encoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html)

## ✅ Checklist de Implementação

- [x] Adicionar dependências Spring Security ao pom.xml
- [x] Criar modelo Usuario com roles
- [x] Criar UsuarioRepository
- [x] Implementar CustomUserDetailsService
- [x] Configurar SecurityConfig
- [x] Criar DataInitializer para usuários padrão
- [x] Criar LoginController
- [x] Criar página de login (login.html)
- [x] Atualizar página principal com logout
- [x] Testar autenticação e autorização
- [x] Documentar o sistema

## 🐛 Solução de Problemas

### Erro: "Access Denied"
- Verifique se o usuário tem as roles necessárias
- Confirme que está logado corretamente

### Erro: "Bad Credentials"
- Verifique se username e senha estão corretos
- Lembre-se que as senhas são case-sensitive

### Erro: "Session Expired"
- Faça login novamente
- Verifique as configurações de timeout de sessão

### Não consigo acessar nenhuma página
- Certifique-se de que está logado
- Limpe cookies e cache do navegador
- Verifique se o banco de dados está acessível

---

**Desenvolvido com Spring Boot 3.1.4 e Spring Security 6**
