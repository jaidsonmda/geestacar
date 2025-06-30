workspace {

    model {
        user = person "Operador do Estacionamento" {
            description "Funcionário que acompanha o status das vagas e receita via painel"
        }

        system = softwareSystem "Sistema de Gerenciamento de Estacionamento" {
            description "Gerencia entrada/saída de veículos e status das vagas"

            webhook = container "Webhook de Sensores" {
                description "Simulador/serviço em Docker que envia eventos dos sensores"
                technology "Docker + HTTP"
                url "http://localhost:3000"
            }

            api = container "API Java" {
                description "API em Spring Boot 3.5 que processa os eventos e expõe endpoints REST"
                technology "Java + Spring Boot 3.5"
                url "http://localhost:3003"
            }

            db = container "PostgreSQL" {
                description "Banco de dados relacional para persistência de eventos e status"
                technology "PostgreSQL"
            }

            user -> api "Consulta status e receita via consumo da api"
            webhook -> api "Envia eventos de entrada, saída e ocupação de vagas via /webhook"
            api -> db "Lê e grava dados dos eventos e status"
        }
    }

    views {

        systemContext system {
            include *
            autolayout lr
            title "Contexto - Sistema de Estacionamento"
        }

        container system {
            include *
            autolayout lr
            title "Contêineres - Sistema de Estacionamento"
        }

        theme default
    }
}