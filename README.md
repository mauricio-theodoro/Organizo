# Organizô - Sistema de Agendamento

![Organizô Logo](./organizo-frontend/src/img/organizo_logo.jpg)

## 📖 Visão Geral

O **Organizô** é um sistema web completo para agendamento de serviços em salões de beleza, desenvolvido em arquitetura moderna:

- **Back‑end**: Java Spring Boot, MySQL, Redis (cache), JWT, agendamentos por e‑mail.
- **Front‑end**: React (Vite + TypeScript), Tailwind CSS, React Router.
- **Infraestrutura**: Docker, Docker Compose, CI/CD.

O objetivo é digitalizar e automatizar todo o fluxo de marcação de horários, tornando-o ágil, confiável e sustentável.

---

## 🚀 Principais Funcionalidades

- **Autenticação**: Roles (Cliente, Dono_Salão, Profissional).
- **CRUD de Salões, Serviços e Profissionais**: Gestão completa.
- **Agendamentos**: Fluxo de seleção de salão → serviço → profissional → data/hora → confirmação.
- **Lembretes por E‑mail**: Notificações automáticas antes do compromisso.
- **Cache Redis**: Acelera consultas frequentes.
- **Paginação e Filtros**: Listagens otimizadas.
- **Documentação OpenAPI**: Gerada via Springdoc.

---

## 🏗️ Tecnologias e Arquitetura

| Camada      | Tecnologia                                |
|-------------|-------------------------------------------|
| Back‑end    | Spring Boot 3, Spring Data JPA, Spring Security, Spring Mail
| Banco dados | MySQL 8
| Cache       | Redis 6
| Front‑end   | React, TypeScript, Vite, Tailwind CSS    |
| Infra       | Docker, Docker Compose                    |
| Testes      | JUnit 5, Mockito, React Testing Library   |

![Arquitetura](docs/architecture.png)

---

## 🛠️ Como Executar Localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/mauricio-theodoro/organizô.git
   cd organizo
   ```

2. Configure variáveis de ambiente em `app/.env`:
   ```properties
   SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/organizo?useUnicode=true&serverTimezone=UTC
   SPRING_DATASOURCE_USERNAME=organizo
   SPRING_DATASOURCE_PASSWORD=organizo_pass
   SPRING_REDIS_HOST=redis
   SPRING_REDIS_PORT=6379
   SPRING_MAIL_HOST=sandbox.smtp.mailtrap.io
   SPRING_MAIL_PORT=2525
   SPRING_MAIL_USERNAME=<seu_mailtrap_user>
   SPRING_MAIL_PASSWORD=<seu_mailtrap_pass>
   JWT_SECRET=<chave-secreta>
   ```

3. Suba a stack com Docker Compose:
   ```bash
   docker-compose up -d --build
   ```

4. Acesse:
   - **Back‑end**: `http://localhost:8080/swagger-ui.html`
   - **Front‑end**: `http://localhost:3000`

---

## 📝 Endpoints Principais

- **Auth**
  - `POST /api/auth/login`
  - `POST /api/auth/register`
- **Salões**
  - `GET /api/saloes`
  - `POST /api/saloes`
- **Serviços**
  - `GET /api/saloes/{id}/servicos`
  - `POST /api/saloes/{id}/servicos`
- **Profissionais**
  - `GET /api/saloes/{id}/profissionais`
  - `POST /api/saloes/{id}/profissionais`
- **Agendamentos**
  - `GET /api/agendamentos`
  - `POST /api/agendamentos`

> Consulte a [documentação Swagger](http://localhost:8080/swagger-ui.html) para maiores detalhes.

---

## 🎨 Front‑end

1. Entre na pasta do front:
   ```bash
   cd organizo-frontend
   ```
2. Instale dependências e rode em modo dev:
   ```bash
   npm install
   npm run dev
   ```

3. Build para produção:
   ```bash
   npm run build
   ```

---

## ✅ Testes

- **Back‑end**:
  ```bash
  cd organizo-backend
  mvn test
  ```

- **Front‑end**:
  ```bash
  cd organizo-frontend
  npm test
  ```

---

## 🤝 Contribuições

Contribuições são bem‑vindas! Abra uma issue ou pull request explicando a feature ou bugfix.

---

## 📄 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

