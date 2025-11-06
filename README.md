# 🌾 Sistema de Gestão de Produtores Rurais

Sistema web desenvolvido com **Spring Boot** para gerenciamento de produtores rurais, suas atividades agrícolas/pecuárias e registro de visitas técnicas.

## 📋 Funcionalidades

- ✅ Cadastro completo de produtores rurais
- ✅ Registro de atividades (cultivo e criação de animais)
- ✅ Documentação de visitas técnicas com diagnósticos e recomendações
- ✅ Interface web moderna e responsiva
- ✅ Banco de dados PostgreeSQL

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.4**
- **Spring Data JPA**
- **Thymeleaf** (template engine)
- **PostgreeSQL** (banco de dados)
- **Maven** (gerenciamento de dependências)
- **HTML5 + CSS3**
- **Docker**

## 📦 Requisitos

- **Java JDK 17** ou superior
- **IntelliJ IDEA** (Community ou Ultimate)
- **Maven** (geralmente já incluído no IntelliJ)
- **PostgreeSQL**
- **Docker Desktop (Recomendado)**

## 🚀 Como Executar no IntelliJ IDEA

### Passo 1: Abrir o Projeto

1. Abra o **IntelliJ IDEA**
2. Clique em **File → Open**
3. Navegue até a pasta `produtor-rural-completo`
4. Selecione a pasta e clique em **OK**
5. Aguarde o IntelliJ importar as dependências do Maven (pode levar alguns minutos)

### Passo 2: Configurar o JDK

1. Vá em **File → Project Structure** (ou pressione `Ctrl+Alt+Shift+S`)
2. Em **Project**, certifique-se de que o **SDK** está configurado para **Java 17** ou superior
3. Clique em **OK**

### Passo 3: Executar a Aplicação

**Opção 1: Via classe principal**
1. Navegue até `src/main/java/com/exemplo/produtorrural/ProdutorRuralApplication.java`
2. Clique com o botão direito no arquivo
3. Selecione **Run 'ProdutorRuralApplication'**

**Opção 2: Via Maven**
1. Abra o terminal do IntelliJ (View → Tool Windows → Terminal)
2. Execute o comando:
   ```bash
   mvn spring-boot:run
   ```

### Passo 4: Acessar o Sistema

1. Aguarde a aplicação iniciar (você verá logs no console)
2. Abra seu navegador e acesse: **http://localhost:8080**
3. Pronto! O sistema está funcionando

### Executar o Projeto em DOCKER (RECOMENDADO)

- **Baixe o Docker**
- **Baixe o Git**
- **Inicie o docker**
- **Na pasta principal do projeto abra o Terminal**
- **Use o comando docker compose up --build**
- **Projeto rodando em localhost:8080**



## 📂 Estrutura do Projeto

```
produtor-rural-completo/
├── src/
│   ├── main/
│   │   ├── java/com/exemplo/produtorrural/
│   │   │   ├── controller/          # Controladores MVC
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── ProdutorController.java
│   │   │   │   ├── AtividadeController.java
│   │   │   │   └── VisitaController.java
│   │   │   ├── model/               # Entidades do banco
│   │   │   │   ├── Produtor.java
│   │   │   │   ├── Atividade.java
│   │   │   │   └── Visita.java
│   │   │   ├── repository/          # Repositórios JPA
│   │   │   │   ├── ProdutorRepository.java
│   │   │   │   ├── AtividadeRepository.java
│   │   │   │   └── VisitaRepository.java
│   │   │   └── ProdutorRuralApplication.java
│   │   └── resources/
│   │       ├── static/css/          # Arquivos CSS
│   │       │   └── style.css
│   │       ├── templates/           # Templates Thymeleaf
│   │       │   ├── index.html
│   │       │   ├── produtores/
│   │       │   ├── atividades/
│   │       │   └── visitas/
│   │       └── application.properties
├── pom.xml                          # Configuração Maven
└── README.md
```



## 🎨 Interface

O sistema possui uma interface moderna e responsiva com:

- Design gradiente 
- Navegação intuitiva
- Tabelas estilizadas
- Formulários organizados
- Ícones para melhor UX
- Responsividade para dispositivos móveis

## 📝 Uso do Sistema

### Cadastrar Produtor
1. Acesse **Produtores** no menu
2. Clique em **Novo Produtor**
3. Preencha os dados (nome e CPF são obrigatórios)
4. Clique em **Salvar**

### Registrar Atividade
1. Acesse **Atividades** no menu
2. Clique em **Nova Atividade**
3. Selecione o tipo (Cultivo ou Animal)
4. Informe a descrição e o produtor
5. Clique em **Salvar**

### Registrar Visita Técnica
1. Acesse **Visitas** no menu
2. Clique em **Registrar Visita**
3. Selecione a data e o produtor
4. Preencha diagnóstico e recomendações
5. Clique em **Salvar**

## 🔧 Solução de Problemas

### Erro: "Port 8080 is already in use"
- Outra aplicação está usando a porta 8080
- Solução: Altere a porta em `application.properties`:
  ```properties
  server.port=8081
  ```

### Erro: "Cannot resolve symbol 'springframework'"
- As dependências Maven não foram baixadas
- Solução: Clique com botão direito no projeto → Maven → Reload Project

### Erro de compilação Java
- Verifique se o JDK 17 está instalado e configurado
- Vá em File → Project Structure → Project → SDK



**Desenvolvido com ❤️ para facilitar a gestão rural**

- DEVs: Fernando, Breno, Kayque, Yuri Robert, Eduardo, Bruno (5° Periodo IMEPAC)
