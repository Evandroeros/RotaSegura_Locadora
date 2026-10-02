package rota_segura.service;

import rota_segura.model.Contrato;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioAnalitico {

    private final List<Contrato> contratos;

    public RelatorioAnalitico(List<Contrato> contratos) {
        this.contratos = contratos != null
                ? new ArrayList<>(contratos)
                : new ArrayList<>();
    }

    public List<Contrato> getContratos() {
        return new ArrayList<>(contratos);
    }

    public List<Contrato> filtrarPorCpfCliente(String cpf) {

        if (cpf == null || cpf.isBlank()) {
            return new ArrayList<>();
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");

        return contratos.stream()
                .filter(c ->
                        c.getCliente()
                                .getCpf()
                                .replaceAll("\\D", "")
                                .equals(cpfLimpo))
                .collect(Collectors.toList());
    }

    public List<Contrato> filtrarPorPlaca(String placa) {

        if (placa == null || placa.isBlank()) {
            return new ArrayList<>();
        }

        return contratos.stream()
                .filter(c ->
                        c.getVeiculo()
                                .getPlaca()
                                .equalsIgnoreCase(placa.trim()))
                .collect(Collectors.toList());
    }

    public List<Contrato> filtrarPorCategoria(String categoria) {

        if (categoria == null || categoria.isBlank()) {
            return new ArrayList<>();
        }

        return contratos.stream()
                .filter(c ->
                        c.getVeiculo()
                                .getCategoria()
                                .equalsIgnoreCase(categoria.trim()))
                .collect(Collectors.toList());
    }

    public List<Contrato> filtrarPorPeriodo(
            LocalDate inicio,
            LocalDate fim) {

        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            return new ArrayList<>();
        }

        return contratos.stream()
                .filter(c ->
                        !c.getInicio().isBefore(inicio) &&
                        !c.getFim().isAfter(fim))
                .collect(Collectors.toList());
    }

    public int totalContratos() {
        return contratos.size();
    }

    public double faturamentoTotal() {
        return contratos.stream()
                .mapToDouble(Contrato::getValorTotal)
                .sum();
    }

    public double ticketMedio() {
        if (contratos.isEmpty()) {
            return 0.0;
        }

        return faturamentoTotal() / contratos.size();
    }

    public double duracaoMediaDias() {

        if (contratos.isEmpty()) {
            return 0.0;
        }

        double totalDias = contratos.stream()
                .mapToDouble(c -> {
                    long dias =
                            ChronoUnit.DAYS.between(
                                    c.getInicio(),
                                    c.getFim()
                            );

                    return dias == 0 ? 1 : dias;
                })
                .sum();

        return totalDias / contratos.size();
    }

    public Map<String, Long> quantidadePorCategoria() {
        return contratos.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getVeiculo().getCategoria(),
                        Collectors.counting()
                ));
    }

    public Map<String, Double> faturamentoPorCategoria() {
        return contratos.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getVeiculo().getCategoria(),
                        Collectors.summingDouble(
                                Contrato::getValorTotal
                        )
                ));
    }

    public void exibirDashboard() {

        System.out.println(
                "\n=================================================="
        );
        System.out.println(
                "   DASHBOARD DE APOIO A DECISAO & INDICADORES"
        );
        System.out.println(
                "=================================================="
        );

        System.out.printf(
                "Total de Locacoes Registradas : %d%n",
                totalContratos()
        );

        System.out.printf(
                "Faturamento Total             : R$ %.2f%n",
                faturamentoTotal()
        );

        System.out.printf(
                "Ticket Medio por Locacao      : R$ %.2f%n",
                ticketMedio()
        );

        System.out.printf(
                "Duracao Media das Locacoes    : %.1f dia(s)%n",
                duracaoMediaDias()
        );

        System.out.println(
                "--------------------------------------------------"
        );
        System.out.println("DISTRIBUICAO POR CATEGORIA:");

        Map<String, Long> qtdMap =
                quantidadePorCategoria();

        Map<String, Double> fatMap =
                faturamentoPorCategoria();

        qtdMap.forEach((categoria, quantidade) -> {

            double faturamento =
                    fatMap.getOrDefault(categoria, 0.0);

            double percentualQuantidade =
                    totalContratos() > 0
                            ? quantidade * 100.0 / totalContratos()
                            : 0;

            double percentualFaturamento =
                    faturamentoTotal() > 0
                            ? faturamento * 100.0 / faturamentoTotal()
                            : 0;

            System.out.printf(
                    " - %-8s : %2d locacao(oes) (%5.1f%%) | " +
                    "Fat: R$ %9.2f (%5.1f%%)%n",
                    categoria,
                    quantidade,
                    percentualQuantidade,
                    faturamento,
                    percentualFaturamento
            );
        });

        System.out.println(
                "==================================================\n"
        );
    }
}
