Rota Segura Locadora de Veículos:
Sistema de locação de veículos desenvolvido em Java.

Faça o download do repositório do Projeto Rota Segura Locadora de Veículos e execute-o via Prompt 
de Comando (CMD) ou Terminal:
https://github.com/Evandroeros/RotaSegura_Locadora/releases/download/v1.0.0/RotaSegura.zip
Digite: "java -jar RotaSegura.jar" para iniciar a execução.

Está com dificuldades? Instruções passo a passo para a implantação e execução local:
https://github.com/Evandroeros/RotaSegura_Locadora/blob/main/RotaSegura_Locadora/Readme.txt

Diagrama de Classes UML (Simples):
    
    classDiagram
    %% Interface e Exceção
    class Documento {
        <<interface>>
        +gerar() String
        +imprimir() void
    }

    class LocadoraException {
        +LocadoraException(mensagem: String)
    }

    %% Camada de Modelos
    class Cliente {
        -int id
        -String nome
        -String cpf
        -String telefone
        -String email
        +getId() int
        +getNome() String
    }

    class Veiculo {
        <<abstract>>
        -String placa
        -String modelo
        -int ano
        -double valorDiaria
        -boolean disponivel
        +calcularSeguroDiario()* double
        +calcularManutencaoDiaria()* double
        +getCategoria()* String
        +calcularDiariaCompleta() double
    }

    class Popular {
        +calcularSeguroDiario() double
        +calcularManutencaoDiaria() double
        +getCategoria() String
    }

    class Sedan {
        +calcularSeguroDiario() double
        +calcularManutencaoDiaria() double
        +getCategoria() String
    }

    class SUV {
        +calcularSeguroDiario() double
        +calcularManutencaoDiaria() double
        +getCategoria() String
    }

    class Contrato {
        -int id
        -LocalDate inicio
        -LocalDate fim
        -double valorTotal
        -boolean encerrado
        +encerrar() void
    }

    %% Camada de Serviços
    class Locadora {
        +cadastrarVeiculo(veiculo: Veiculo)
        +cadastrarCliente(cliente: Cliente)
        +alugar(idCliente: int, placa: String, inicio: LocalDate, fim: LocalDate) Contrato
        +encerrarContrato(idContrato: int)
    }

    class ContratoDocumento {
        +gerar() String
        +imprimir() void
    }

    class RelatorioFechamento {
        +gerar() String
        +imprimir() void
    }

    class RelatorioAnalitico {
        +exibirDashboard() void
        +filtrarPorCpfCliente(cpf: String) List~Contrato~
        +filtrarPorPlaca(placa: String) List~Contrato~
        +filtrarPorCategoria(categoria: String) List~Contrato~
    }

    class Persistencia {
        -String arquivo
        +salvarContratos(contratos: List~Contrato~) void
        +exibirArquivo() void
    }

    %% Relacionamentos de Herança e Implementação
    Veiculo <|-- Popular
    Veiculo <|-- Sedan
    Veiculo <|-- SUV
    Documento <|.. ContratoDocumento
    Documento <|.. RelatorioFechamento

    %% Relacionamentos de Associação e Composições
    Contrato "1" --> "1" Cliente : associado a
    Contrato "1" --> "1" Veiculo : refere-se a
    Locadora "1" o-- "*" Veiculo : frota
    Locadora "1" o-- "*" Cliente : clientes
    Locadora "1" o-- "*" Contrato : historico
    ContratoDocumento "1" --> "1" Contrato
    RelatorioFechamento "1" --> "*" Contrato
    RelatorioAnalitico "1" --> "*" Contrato

Elaborado por: 
Evandro Rodrigo Olian
RA 1301392611014
Curso DSM - Fatec
