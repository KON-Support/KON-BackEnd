# KON Support - Sistema de Gerenciamento de Chamados

## 📋 Sobre o Projeto

KON Support é uma API REST completa para gerenciamento de chamados de suporte, desenvolvida com Spring Boot. O sistema oferece funcionalidades como abertura de chamados, atribuição de responsáveis, gestão de SLA, sistema de comentários com anexos, e autenticação via OAuth2 (Google) e JWT.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Security** (JWT + OAuth2)
- **PostgreSQL** (Banco de dados)
- **Hibernate/JPA** (ORM)
- **Swagger/OpenAPI** (Documentação)
- **JavaMailSender** (Envio de e-mails)
- **Lombok** (Redução de boilerplate)

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
├── controller/     # Endpoints REST
├── service/        # Lógica de negócio
├── repository/     # Acesso a dados
├── model/          # Entidades JPA
├── dto/            # Objetos de transferência
├── config/         # Configurações (Security, CORS, Swagger)
├── exception/      # Tratamento de exceções
└── utils/          # Utilitários (JWT, etc)
```

## 📦 Principais Funcionalidades

### 🔐 Autenticação
- Login tradicional (email/senha)
- OAuth2 com Google
- Geração e validação de tokens JWT
- Sistema de roles (USER, ADMIN, AGENTE)

### 🎫 Gestão de Chamados
- Abertura de chamados com anexos
- Atribuição automática de SLA baseado em categoria e plano
- Atribuição de responsáveis
- Controle de status (ABERTO, EM_ANDAMENTO, RESOLVIDO, FECHADO)
- Verificação de violação de SLA
- Notificações por email

### 💬 Sistema de Comentários
- Comentários em chamados
- Anexos em comentários
- Histórico completo de interações

### 📊 SLA (Service Level Agreement)
- Criação de SLAs por categoria + plano
- Cálculo automático de prazos (resposta e resolução)
- Alertas de SLA em risco (50% do prazo)
- Marcação de SLAs violados

### 👥 Gestão de Usuários
- Cadastro com atribuição automática de plano
- Ativação/desativação de usuários
- Gestão de roles e permissões

### 📁 Anexos
- Upload/download de arquivos
- Armazenamento em banco de dados
- Suporte a múltiplos tipos de arquivo

## 🔧 Como Rodar o Projeto

### Pré-requisitos

1. **Java 21 ou superior**
2. **PostgreSQL instalado e rodando**
3. **Maven** (ou use o wrapper incluído `./mvnw`)

### Passo a Passo

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd KON-BackEnd
```

2. **Configure o banco de dados**

Crie um banco PostgreSQL:
```sql
CREATE DATABASE konSupport;
```

3. **Configure as credenciais**

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/konSupport
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Email (opcional - para notificações)
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_app

# OAuth2 Google (opcional)
spring.security.oauth2.client.registration.google.client-id=seu_client_id
spring.security.oauth2.client.registration.google.client-secret=seu_client_secret

# URL do frontend
frontend.url=http://localhost:4200
```

4. **Compile e execute**

```bash
# Usando Maven
mvn clean install
mvn spring-boot:run

# OU usando o wrapper
./mvnw clean install
./mvnw spring-boot:run
```

5. **Acesse a aplicação**

- **API:** http://localhost:8089
- **Swagger UI:** http://localhost:8089/swagger-ui/index.html
- **Docs API:** http://localhost:8089/v3/api-docs

## 📚 Endpoints Principais

### Autenticação
- `POST /api/auth/login` - Login tradicional
- `POST /api/oauth2/completar-cadastro` - Completar cadastro OAuth2

### Chamados
- `POST /api/v1/chamado/abrir` - Abrir chamado
- `GET /api/v1/chamado/listar/todos` - Listar todos
- `PUT /api/v1/chamado/atribuir/{id}` - Atribuir responsável
- `PUT /api/v1/chamado/fechar/{id}` - Fechar chamado

### Comentários
- `POST /api/v1/comentario/criar` - Criar comentário
- `POST /api/v1/comentario/criar-com-anexo` - Comentário com anexo
- `GET /api/v1/comentario/chamado/{id}` - Listar comentários

### Anexos
- `POST /api/v1/anexo/upload` - Upload de arquivo
- `GET /api/v1/anexo/baixar/{id}` - Download de arquivo

## 🗄️ Dados Iniciais

O sistema cria automaticamente:

### Roles
- `ROLE_USER` - Usuário comum
- `ROLE_ADMIN` - Administrador
- `ROLE_AGENTE` - Agente de suporte

### Planos
- **Bronze:** 10 usuários, R$ 99,90
- **Prata:** 50 usuários, R$ 199,90
- **Ouro:** Ilimitado, R$ 499,90

### Categorias
- Incidente (4h resposta, 24h resolução)
- Requisição (8h resposta, 48h resolução)
- Problema (2h resposta, 8h resolução)

### Usuários de Teste
- **User:** user@empresa.com / senha: Senha123@
- **Admin:** admin@empresa.com / senha: Senha123@

## 🔒 Segurança

- Autenticação JWT com expiração de 1 hora
- Criptografia de senhas com BCrypt
- CORS configurado para desenvolvimento
- OAuth2 integrado com Google
- Proteção CSRF desabilitada (API REST)

## 📧 Notificações por Email

O sistema envia emails automáticos em:
- Criação de chamado
- Atribuição de responsável
- Fechamento de chamado

---

**Desenvolvido para gerenciamento eficiente de suporte técnico com controle de SLA e múltiplos níveis de acesso.**
