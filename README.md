# 🚗 GestaCar – Sistema de Gerenciamento de Estacionamento

**GestaCar** é uma aplicação voltada para o controle de entrada e saída de veículos em vagas de estacionamento, oferecendo monitoramento em tempo real, automação de processos e geração de relatórios gerenciais. Ideal para estacionamentos comerciais, privados, empresariais e residenciais.

---

## 📋 Funcionalidades

- 📥 Registro automático de **entrada e saída de veículos**
- 📍 Acompanhamento em tempo real do **status das vagas**
- 🔍 Consulta por **placa** e **número da vaga**
- 📊 Geração de **relatórios de ocupação** e **receita**
- 🔐 Controle de acesso com **perfis de usuários**
- 🔄 Integração com **sensores e APIs externas**
- 🧠 Suporte a leitura de placas (LPR) via OCR

---

## 🛠️ Tecnologias Utilizadas

- **Back-end:** Java 17, Spring Boot 3.5
- **Banco de Dados:** PostgreSQL
- **Mensageria / Eventos:** Webhooks simulando sensores
- **API REST:** Documentada com Swagger/OpenAPI
- **Docker:** Para ambiente de simulação e orquestração

---

## 📦 Requisitos

- Java 17+
- Docker e Docker Compose (para simulação)
- PostgreSQL (ou container equivalente)
- Maven ou Gradle
- Git

---

## 🚀 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/gestacar.git
cd gestacar
