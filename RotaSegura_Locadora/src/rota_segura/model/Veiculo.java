package rota_segura.model;

import java.time.LocalDate;

public abstract class Veiculo {

    private final String placa;
    private final String modelo;
    private final int ano;

    private double valorDiaria;
    private boolean disponivel;
    private LocalDate ultimaManutencao;

    public Veiculo(
            String placa,
            String modelo,
            int ano,
            double valorDiaria) {

        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException(
                    "Placa obrigatoria."
            );
        }

        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException(
                    "Modelo obrigatorio."
            );
        }

        int anoAtual = LocalDate.now().getYear();

        if (ano < 1900 || ano > anoAtual + 1) {
            throw new IllegalArgumentException(
                    "Ano do veiculo invalido."
            );
        }

        if (valorDiaria <= 0) {
            throw new IllegalArgumentException(
                    "Valor da diaria deve ser maior que zero."
            );
        }

        this.placa = placa.trim().toUpperCase();
        this.modelo = modelo.trim();
        this.ano = ano;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
        this.ultimaManutencao = LocalDate.now();
    }

    public abstract double calcularSeguroDiario();

    public abstract double calcularManutencaoDiaria();

    public abstract String getCategoria();

    public final double calcularDiariaCompleta() {
        return valorDiaria
                + calcularSeguroDiario()
                + calcularManutencaoDiaria();
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public LocalDate getUltimaManutencao() {
        return ultimaManutencao;
    }

    public void setValorDiaria(double valorDiaria) {
        if (valorDiaria <= 0) {
            throw new IllegalArgumentException(
                    "Valor da diaria invalido."
            );
        }

        this.valorDiaria = valorDiaria;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void registrarManutencao(LocalDate data) {
        if (data == null || data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Data de manutencao invalida."
            );
        }

        this.ultimaManutencao = data;
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | %s | %d | Placa: %s | Diaria: R$ %.2f | " +
                "Disponivel: %s",
                getCategoria(),
                modelo,
                ano,
                placa,
                valorDiaria,
                disponivel ? "SIM" : "NAO"
        );
    }
}
