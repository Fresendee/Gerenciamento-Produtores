# Migração para PostgreSQL

Este projeto foi migrado de SQLite para PostgreSQL.

## Alterações Realizadas

### 1. Dependências (pom.xml)
- **Removido**: `sqlite-jdbc` e `hibernate-community-dialects`
- **Adicionado**: `postgresql` driver JDBC

### 2. Configuração (application.properties)
- **URL do banco**: `jdbc:postgresql://localhost:5433/produtordb`
- **Driver**: `org.postgresql.Driver`
- **Dialect**: `org.hibernate.dialect.PostgreSQLDialect`
- **Usuário**: `postgres`
- **Senha**: `postgres`

## Pré-requisitos

Para executar este projeto, você precisa ter o PostgreSQL instalado e configurado:

### Instalação do PostgreSQL

#### Ubuntu/Debian
```bash
sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
```

#### Windows
Baixe o instalador em: https://www.postgresql.org/download/windows/

#### macOS
```bash
brew install postgresql
```

### Configuração Inicial

1. **Iniciar o serviço PostgreSQL**:
   ```bash
   # Linux
   sudo service postgresql start
   
   # macOS
   brew services start postgresql
   
   # Windows
   # O serviço inicia automaticamente após a instalação
   ```

2. **Criar o banco de dados**:
   ```bash
   sudo -u postgres psql -c "CREATE DATABASE produtordb;"
   ```

3. **Configurar a senha do usuário postgres** (opcional):
   ```bash
   sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
   ```

   **Nota**: Se você alterar a senha, atualize o arquivo `application.properties` com a nova senha.

### Configuração Personalizada

Se você deseja usar credenciais diferentes, edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/produtordb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Como Executar

1. **Certifique-se de que o PostgreSQL está rodando**:
   ```bash
   sudo service postgresql status
   ```

2. **Compile e execute o projeto**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Acesse a aplicação**:
   ```
   http://localhost:8080
   ```

## Diferenças entre SQLite e PostgreSQL

### Vantagens do PostgreSQL:
- **Concorrência**: Suporta múltiplas conexões simultâneas
- **Escalabilidade**: Melhor desempenho para grandes volumes de dados
- **Recursos avançados**: Suporta transações ACID completas, triggers, stored procedures
- **Tipos de dados**: Maior variedade de tipos de dados nativos
- **Segurança**: Sistema de autenticação e permissões robusto
- **Produção**: Adequado para ambientes de produção

### Considerações:
- Requer instalação e configuração de servidor de banco de dados
- Consome mais recursos do sistema
- Necessita de backup e manutenção adequados

## Troubleshooting

### Erro de conexão com o banco
- Verifique se o PostgreSQL está rodando: `sudo service postgresql status`
- Verifique se o banco de dados foi criado: `sudo -u postgres psql -c "\l"`
- Verifique as credenciais no `application.properties`

### Erro de autenticação
- Confirme que a senha está correta no `application.properties`
- Verifique o arquivo de configuração do PostgreSQL: `/etc/postgresql/*/main/pg_hba.conf`

### Porta já em uso
- Verifique se outra instância do PostgreSQL está rodando
- Altere a porta no `application.properties` se necessário

## Suporte

Para mais informações sobre PostgreSQL, consulte a documentação oficial:
- https://www.postgresql.org/docs/
