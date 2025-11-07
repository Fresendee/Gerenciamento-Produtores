# 👥 Gerenciamento de Usuários - Sistema NPA

## 📋 Visão Geral

O sistema agora possui um **módulo completo de gerenciamento de usuários** que permite criar, editar, excluir usuários e alterar senhas diretamente pela interface web. Não é mais necessário editar código ou acessar o banco de dados para gerenciar usuários.

## ✨ Funcionalidades Implementadas

### 1. Listagem de Usuários
- Visualização de todos os usuários cadastrados
- Exibição de username, roles (permissões) e status (ativo/inativo)
- Ações rápidas: editar, alterar senha e excluir

### 2. Criar Novo Usuário
- Formulário completo para cadastro de novos usuários
- Campos:
  - **Nome de usuário** (único no sistema)
  - **Senha** (obrigatória para novos usuários)
  - **Permissões** (ROLE_USER e/ou ROLE_ADMIN)
  - **Status** (ativo/inativo)
- Validação de username duplicado
- Criptografia automática de senha

### 3. Editar Usuário
- Atualização de dados de usuários existentes
- Possibilidade de alterar username, roles e status
- Senha opcional (deixe em branco para manter a atual)
- Validação de username único

### 4. Alterar Senha (Administrador)
- Administradores podem alterar senha de qualquer usuário
- Não requer senha atual
- Validação de confirmação de senha
- Senha mínima de 4 caracteres

### 5. Alterar Minha Senha (Usuário)
- Qualquer usuário pode alterar sua própria senha
- Requer senha atual para validação
- Confirmação de nova senha
- Dicas de segurança exibidas na tela

### 6. Excluir Usuário
- Remoção completa de usuários do sistema
- Confirmação antes da exclusão
- Proteção: usuário não pode excluir a si mesmo

## 🚀 Como Usar

### Acessar o Módulo de Usuários

1. Faça login no sistema
2. No menu lateral, clique em **"Usuários"**
3. Ou acesse diretamente: `http://localhost:8080/usuarios`

### Criar um Novo Usuário

1. Na tela de listagem, clique em **"Novo Usuário"**
2. Preencha os dados:
   - **Nome de usuário**: escolha um nome único
   - **Senha**: defina uma senha (mínimo 4 caracteres)
   - **Permissões**: marque as roles desejadas
     - ✓ **Usuário Comum** (ROLE_USER): acesso padrão
     - ✓ **Administrador** (ROLE_ADMIN): acesso completo + gerenciamento de usuários
   - **Status**: marque "Usuário Ativo" para permitir login
3. Clique em **"Salvar"**

### Editar um Usuário Existente

1. Na lista de usuários, clique no ícone de **editar** (lápis)
2. Modifique os dados desejados:
   - Username
   - Roles (permissões)
   - Status (ativo/inativo)
   - Senha (opcional - deixe em branco para manter)
3. Clique em **"Salvar"**

### Alterar Senha de um Usuário (Como Admin)

1. Na lista de usuários, clique no ícone de **cadeado**
2. Digite a nova senha
3. Confirme a nova senha
4. Clique em **"Alterar Senha"**

**Nota:** Como administrador, você não precisa informar a senha atual do usuário.

### Alterar Sua Própria Senha

**Opção 1: Pela lista de usuários**
1. Clique em **"Alterar Minha Senha"** no topo da lista

**Opção 2: Diretamente**
1. Acesse: `http://localhost:8080/usuarios/minha-senha`

**Passo a passo:**
1. Digite sua **senha atual**
2. Digite a **nova senha**
3. **Confirme** a nova senha
4. Clique em **"Alterar Senha"**

### Excluir um Usuário

1. Na lista de usuários, clique no ícone de **lixeira** (vermelho)
2. Confirme a exclusão na janela de confirmação
3. O usuário será removido permanentemente

**Atenção:**
- Você não pode excluir seu próprio usuário
- A exclusão é irreversível

## 🔐 Permissões e Roles

### ROLE_USER (Usuário Comum)
- Acesso a todas as funcionalidades do sistema
- Pode visualizar e gerenciar:
  - Produtores
  - Atividades
  - Visitas Técnicas
- Pode alterar sua própria senha
- **Não pode** gerenciar outros usuários

### ROLE_ADMIN (Administrador)
- Todas as permissões de ROLE_USER
- **Pode** gerenciar usuários:
  - Criar novos usuários
  - Editar usuários existentes
  - Alterar senhas de outros usuários
  - Excluir usuários
  - Ativar/desativar usuários

**Dica:** Um usuário pode ter ambas as roles simultaneamente.

## 📁 Arquivos Criados

### Backend (Java)

#### Service
- **`service/UsuarioService.java`**
  - Lógica de negócio para gerenciamento de usuários
  - Métodos: listar, buscar, salvar, atualizar, excluir, alterar senha
  - Validações e criptografia de senha

#### Controller
- **`controller/UsuarioController.java`**
  - Endpoints para todas as operações CRUD
  - Rotas:
    - `GET /usuarios` - Listar usuários
    - `GET /usuarios/novo` - Formulário de novo usuário
    - `POST /usuarios/salvar` - Salvar novo usuário
    - `GET /usuarios/editar/{id}` - Formulário de edição
    - `POST /usuarios/atualizar/{id}` - Atualizar usuário
    - `GET /usuarios/excluir/{id}` - Excluir usuário
    - `GET /usuarios/alterar-senha/{id}` - Formulário alterar senha (admin)
    - `POST /usuarios/alterar-senha/{id}` - Processar alteração (admin)
    - `GET /usuarios/minha-senha` - Formulário alterar própria senha
    - `POST /usuarios/minha-senha` - Processar alteração própria senha

### Frontend (Thymeleaf)

#### Templates
- **`templates/usuarios/lista.html`**
  - Listagem de todos os usuários
  - Tabela com ações (editar, alterar senha, excluir)
  - Badges coloridos para roles e status

- **`templates/usuarios/form.html`**
  - Formulário para criar/editar usuário
  - Campos: username, senha, roles, status
  - Validações client-side e server-side

- **`templates/usuarios/alterar-senha.html`**
  - Formulário para admin alterar senha de usuário
  - Não requer senha atual

- **`templates/usuarios/minha-senha.html`**
  - Formulário para usuário alterar própria senha
  - Requer senha atual
  - Dicas de segurança

## 🎨 Interface

### Elementos Visuais

#### Badges de Roles
- **Admin**: Laranja (`#e65100`)
- **Usuário**: Verde (`#2e7d32`)

#### Status
- **Ativo**: Verde com ícone de check
- **Inativo**: Vermelho com ícone de cancel

#### Ações (Ícones)
- **Editar**: Lápis (azul ao hover)
- **Alterar Senha**: Cadeado (verde ao hover)
- **Excluir**: Lixeira (vermelho ao hover)

### Mensagens
- **Sucesso**: Fundo verde claro
- **Erro**: Fundo vermelho claro

## 🔒 Segurança

### Validações Implementadas

1. **Username único**: Sistema verifica duplicidade
2. **Senha obrigatória**: Para novos usuários
3. **Senha mínima**: 4 caracteres (recomendado 8+)
4. **Confirmação de senha**: Deve coincidir
5. **Senha atual**: Necessária para usuário alterar própria senha
6. **Auto-exclusão**: Usuário não pode excluir a si mesmo
7. **Criptografia**: BCrypt para todas as senhas

### Boas Práticas

✓ **Senhas nunca são exibidas** no sistema  
✓ **Senhas são criptografadas** antes de salvar no banco  
✓ **Validação de senha atual** ao alterar própria senha  
✓ **Confirmação antes de excluir** usuários  
✓ **Mensagens claras** de erro e sucesso  

## 📊 Exemplos de Uso

### Exemplo 1: Criar Usuário para Técnico de Campo

```
Username: joao.silva
Senha: tecnico2024
Roles: ☑ Usuário Comum
Status: ☑ Usuário Ativo
```

Este usuário poderá acessar o sistema e gerenciar produtores, atividades e visitas, mas não poderá criar outros usuários.

### Exemplo 2: Criar Usuário Administrador

```
Username: maria.admin
Senha: admin@2024
Roles: ☑ Usuário Comum
       ☑ Administrador
Status: ☑ Usuário Ativo
```

Este usuário terá acesso completo, incluindo gerenciamento de usuários.

### Exemplo 3: Desativar Usuário Temporariamente

1. Edite o usuário
2. Desmarque "Usuário Ativo"
3. Salve

O usuário não poderá mais fazer login, mas seus dados permanecem no sistema.

## 🐛 Solução de Problemas

### "Este nome de usuário já existe"
**Causa:** Tentativa de criar/editar com username duplicado  
**Solução:** Escolha outro nome de usuário único

### "A senha é obrigatória"
**Causa:** Tentativa de criar novo usuário sem senha  
**Solução:** Preencha o campo de senha

### "As senhas não coincidem"
**Causa:** Nova senha e confirmação diferentes  
**Solução:** Digite a mesma senha nos dois campos

### "Senha atual incorreta"
**Causa:** Senha atual informada está errada  
**Solução:** Verifique e digite a senha correta

### "Você não pode excluir seu próprio usuário"
**Causa:** Tentativa de auto-exclusão  
**Solução:** Peça a outro administrador para excluir

## 📈 Melhorias Futuras (Sugestões)

1. **Recuperação de senha por email**
   - Envio de link de reset
   - Token temporário

2. **Auditoria de ações**
   - Log de criação/edição/exclusão
   - Histórico de alterações de senha

3. **Política de senha forte**
   - Exigir letras maiúsculas/minúsculas
   - Exigir números e símbolos
   - Senha mínima de 8 caracteres

4. **Expiração de senha**
   - Forçar troca periódica
   - Notificação de expiração

5. **Autenticação de dois fatores (2FA)**
   - Código via SMS/email
   - Aplicativo autenticador

6. **Perfis de acesso personalizados**
   - Criar roles customizadas
   - Permissões granulares por módulo

## 📞 Resumo de Rotas

| Rota | Método | Descrição |
|------|--------|-----------|
| `/usuarios` | GET | Lista todos os usuários |
| `/usuarios/novo` | GET | Formulário de novo usuário |
| `/usuarios/salvar` | POST | Salva novo usuário |
| `/usuarios/editar/{id}` | GET | Formulário de edição |
| `/usuarios/atualizar/{id}` | POST | Atualiza usuário |
| `/usuarios/excluir/{id}` | GET | Exclui usuário |
| `/usuarios/alterar-senha/{id}` | GET | Form alterar senha (admin) |
| `/usuarios/alterar-senha/{id}` | POST | Processa alteração (admin) |
| `/usuarios/minha-senha` | GET | Form alterar própria senha |
| `/usuarios/minha-senha` | POST | Processa própria alteração |

## ✅ Checklist de Funcionalidades

- [x] Listar usuários
- [x] Criar novo usuário
- [x] Editar usuário existente
- [x] Excluir usuário
- [x] Alterar senha de usuário (admin)
- [x] Alterar própria senha
- [x] Validação de username único
- [x] Validação de senhas
- [x] Criptografia de senhas
- [x] Gerenciamento de roles
- [x] Ativar/desativar usuários
- [x] Interface responsiva
- [x] Mensagens de feedback
- [x] Proteção contra auto-exclusão
- [x] Integração com menu de navegação

---

**Sistema completo e pronto para uso!** 🎉

Agora você pode gerenciar todos os usuários do sistema através de uma interface amigável, sem necessidade de acessar código ou banco de dados.
