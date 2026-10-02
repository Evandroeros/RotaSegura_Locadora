package rota_segura;

import rota_segura.exception.LocadoraException;
import rota_segura.model.*;
import rota_segura.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Locadora locadora = new Locadora();
    private static final Persistencia persistencia =
            new Persistencia("historico_locacoes.txt");

    public static void main(String[] args) {

        carregarDadosExemplo();

        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Opcao: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> cadastrarVeiculo();
                    case 3 -> listarClientes();
                    case 4 -> listarFrota();
                    case 5 -> realizarLocacao();
                    case 6 -> encerrarContrato();
                    case 7 -> listarContratos();
                    case 8 -> gerarRelatorio();
                    case 9 -> salvar();
                    case 10 -> consultarHistoricoEstruturado();
                    case 0 -> System.out.println("Sistema encerrado.");
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (LocadoraException | IllegalArgumentException | IOException e) {
                System.out.println("ERRO: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n========== ROTA SEGURA ==========");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar veiculo");
        System.out.println("3 - Listar clientes");
        System.out.println("4 - Listar frota");
        System.out.println("5 - Realizar locacao");
        System.out.println("6 - Encerrar contrato");
        System.out.println("7 - Listar contratos em memoria");
        System.out.println("8 - Relatorio de fechamento");
        System.out.println("9 - Salvar historico em arquivo");
        System.out.println("10 - Consulta do Historico & Apoio a Decisao (BI)");
        System.out.println("0 - Sair");
        System.out.println("=================================");
    }

    private static void cadastrarCliente() throws LocadoraException {
        String nome = ler("Nome: ");
        String cpf = ler("CPF: ");
        String telefone = ler("Telefone: ");
        String email = ler("E-mail: ");

        locadora.cadastrarCliente(
                new Cliente(nome, cpf, telefone, email)
        );

        System.out.println("Cliente cadastrado com sucesso.");
    }

    private static void cadastrarVeiculo() throws LocadoraException {
        System.out.println("1 - Popular | 2 - Sedan | 3 - SUV");

        int tipo = lerInt("Categoria: ");
        String placa = ler("Placa: ");
        String modelo = ler("Modelo: ");
        int ano = lerInt("Ano: ");
        double diaria = lerDouble("Valor da diaria: ");

        Veiculo veiculo;

        switch (tipo) {
            case 1 -> veiculo = new Popular(placa, modelo, ano, diaria);
            case 2 -> veiculo = new Sedan(placa, modelo, ano, diaria);
            case 3 -> veiculo = new SUV(placa, modelo, ano, diaria);
            default -> throw new IllegalArgumentException("Categoria invalida.");
        }

        locadora.cadastrarVeiculo(veiculo);
        System.out.println("Veiculo cadastrado com sucesso.");
    }

    private static void listarClientes() {
        System.out.println("\n===== CLIENTES =====");

        if (locadora.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        locadora.getClientes().forEach(System.out::println);
    }

    private static void listarFrota() {
        System.out.println("\n===== FROTA =====");

        if (locadora.getFrota().isEmpty()) {
            System.out.println("Nenhum veiculo cadastrado.");
            return;
        }

        locadora.getFrota().forEach(v ->
                System.out.printf(
                        "%s | Diaria completa: R$ %.2f%n",
                        v,
                        v.calcularDiariaCompleta()
                )
        );
    }

    private static void realizarLocacao() throws LocadoraException {
        int idCliente = lerInt("ID do cliente: ");
        String placa = ler("Placa do veiculo: ");

        LocalDate inicio = lerData("Data inicio (AAAA-MM-DD): ");
        LocalDate fim = lerData("Data fim (AAAA-MM-DD): ");

        Contrato contrato = locadora.alugar(
                idCliente, placa, inicio, fim
        );

        new ContratoDocumento(contrato).imprimir();
    }

    private static void encerrarContrato() throws LocadoraException {
        int id = lerInt("ID do contrato: ");

        locadora.encerrarContrato(id);

        System.out.println("Contrato encerrado. Veiculo liberado.");
    }

    private static void listarContratos() {
        System.out.println("\n===== CONTRATOS =====");

        if (locadora.getHistorico().isEmpty()) {
            System.out.println("Nenhum contrato registrado.");
            return;
        }

        locadora.getHistorico().forEach(System.out::println);
    }

    private static void gerarRelatorio() {
        new RelatorioFechamento(
                locadora.getHistorico()
        ).imprimir();
    }

    private static void salvar() throws IOException {
        persistencia.salvarContratos(
                locadora.getHistorico()
        );

        System.out.println(
                "Historico salvo em historico_locacoes.txt com sucesso."
        );
    }

    private static void consultarHistoricoEstruturado()
            throws IOException {

        RelatorioAnalitico analitico =
                new RelatorioAnalitico(locadora.getHistorico());

        int opSub;

        do {
            System.out.println(
                    "\n=== MENU 10: CONSULTA DO HISTORICO & APOIO A DECISAO ==="
            );
            System.out.println("1 - Dashboard & Indicadores Gerenciais (KPIs)");
            System.out.println("2 - Filtrar por CPF do Cliente");
            System.out.println("3 - Filtrar por Placa do Veiculo");
            System.out.println("4 - Filtrar por Categoria (POPULAR, SEDAN, SUV)");
            System.out.println("5 - Filtrar por Periodo (Data Inicio / Data Fim)");
            System.out.println("6 - Exibir arquivo TXT salvo bruto");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.println("=========================================================");

            opSub = lerInt("Opcao: ");

            switch (opSub) {
                case 1 -> analitico.exibirDashboard();

                case 2 -> {
                    String cpf = ler("CPF para busca: ");
                    List<Contrato> res =
                            analitico.filtrarPorCpfCliente(cpf);
                    exibirResultadoBusca(res);
                }

                case 3 -> {
                    String placa = ler("Placa para busca: ");
                    List<Contrato> res =
                            analitico.filtrarPorPlaca(placa);
                    exibirResultadoBusca(res);
                }

                case 4 -> {
                    String categoria =
                            ler("Categoria (POPULAR, SEDAN, SUV): ");
                    List<Contrato> res =
                            analitico.filtrarPorCategoria(categoria);
                    exibirResultadoBusca(res);
                }

                case 5 -> {
                    LocalDate inicio =
                            lerData("Data Inicial (AAAA-MM-DD): ");
                    LocalDate fim =
                            lerData("Data Final (AAAA-MM-DD): ");

                    List<Contrato> res =
                            analitico.filtrarPorPeriodo(inicio, fim);

                    exibirResultadoBusca(res);
                }

                case 6 -> persistencia.exibirArquivo();

                case 0 ->
                        System.out.println(
                                "Retornando ao menu principal..."
                        );

                default -> System.out.println("Opcao invalida.");
            }

        } while (opSub != 0);
    }

    private static void exibirResultadoBusca(
            List<Contrato> contratos) {

        System.out.println(
                "\n--- RESULTADO DA PESQUISA (" +
                contratos.size() +
                " contrato(s) encontrado(s)) ---"
        );

        if (contratos.isEmpty()) {
            System.out.println(
                    "Nenhum contrato encontrado para os criterios informados."
            );
        } else {
            contratos.forEach(System.out::println);
        }
    }

    private static void carregarDadosExemplo() {
        try {
            locadora.cadastrarCliente(
                    new Cliente(
                            "Joao da Silva",
                            "12345678901",
                            "(14) 99999-1111",
                            "joao@email.com"
                    )
            );

            locadora.cadastrarCliente(
                    new Cliente(
                            "Maria Souza",
                            "98765432100",
                            "(14) 98888-2222",
                            "maria@email.com"
                    )
            );

            locadora.cadastrarVeiculo(
                    new Popular(
                            "ABC1D23",
                            "Fiat Argo",
                            2024,
                            120
                    )
            );

            locadora.cadastrarVeiculo(
                    new Sedan(
                            "DEF4G56",
                            "Toyota Corolla",
                            2023,
                            210
                    )
            );

            locadora.cadastrarVeiculo(
                    new SUV(
                            "GHI7J89",
                            "Jeep Compass",
                            2024,
                            280
                    )
            );

        } catch (LocadoraException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    private static String ler(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(ler(mensagem));
            } catch (NumberFormatException e) {
                System.out.println(
                        "Digite um numero inteiro valido."
                );
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(
                        ler(mensagem).replace(",", ".")
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Digite um numero valido."
                );
            }
        }
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            try {
                return LocalDate.parse(ler(mensagem));
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println(
                        "Data invalida. Use o formato AAAA-MM-DD."
                );
            }
        }
    }
}
