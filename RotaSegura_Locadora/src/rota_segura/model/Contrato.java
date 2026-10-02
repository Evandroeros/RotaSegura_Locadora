package rota_segura.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Contrato {

    private static int proximoId = 1;

    private final int id;
    private final Cliente cliente;
    private final Veiculo veiculo;
    private final LocalDate inicio;
    private final LocalDate fim;
    private final double valorTotal;

    private boolean encerrado;

    public Contrato(
            Cliente cliente,
            Veiculo veiculo,
            LocalDate inicio,
            LocalDate fim) {

        if (cliente == null || veiculo == null) {
            throw new IllegalArgumentException(
                    "Cliente e veiculo sao obrigatorios."
            );
        }

        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new IllegalArgumentException(
                    "Datas inconsistentes."
            );
        }

        long dias = ChronoUnit.DAYS.between(inicio, fim);

        if (dias == 0) {
            dias = 1;
        }

        this.id = proximoId++;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.inicio = inicio;
        this.fim = fim;
        this.valorTotal =
                dias * veiculo.calcularDiariaCompleta();
        this.encerrado = false;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void encerrar() {
        encerrado = true;
        veiculo.setDisponivel(true);
    }

    @Override
    public String toString() {
        return String.format(
                "Contrato %d | Cliente: %s | Veiculo: %s | %s a %s | " +
                "Total: R$ %.2f | %s",
                id,
                cliente.getNome(),
                veiculo.getPlaca(),
                inicio,
                fim,
                valorTotal,
                encerrado ? "ENCERRADO" : "ATIVO"
        );
    }
}
