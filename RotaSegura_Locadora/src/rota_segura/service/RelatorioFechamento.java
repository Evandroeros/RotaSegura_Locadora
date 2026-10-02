package rota_segura.service;

import rota_segura.model.Contrato;

import java.util.List;

public class RelatorioFechamento implements Documento {

    private final List<Contrato> contratos;

    public RelatorioFechamento(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    @Override
    public String gerar() {

        double total = 0;

        StringBuilder sb = new StringBuilder();

        sb.append(
                "\n========== RELATORIO DE FECHAMENTO ==========\n"
        );

        for (Contrato c : contratos) {

            if (c.isEncerrado()) {
                sb.append(c).append("\n");
                total += c.getValorTotal();
            }
        }

        sb.append(
                String.format(
                        "Faturamento dos contratos encerrados: R$ %.2f%n",
                        total
                )
        );

        sb.append(
                "=============================================\n"
        );

        return sb.toString();
    }

    @Override
    public void imprimir() {
        System.out.println(gerar());
    }
}
