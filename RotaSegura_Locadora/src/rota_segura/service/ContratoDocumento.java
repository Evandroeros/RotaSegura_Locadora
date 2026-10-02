package rota_segura.service;

import rota_segura.model.Contrato;

public class ContratoDocumento implements Documento {

    private final Contrato contrato;

    public ContratoDocumento(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException(
                    "Contrato obrigatorio."
            );
        }

        this.contrato = contrato;
    }

    @Override
    public String gerar() {
        return "\n========== CONTRATO DE LOCACAO ==========\n" +
                "Contrato: " + contrato.getId() + "\n" +
                "Cliente: " + contrato.getCliente().getNome() + "\n" +
                "CPF: " + contrato.getCliente().getCpf() + "\n" +
                "Veiculo: " + contrato.getVeiculo().getModelo() + "\n" +
                "Categoria: " + contrato.getVeiculo().getCategoria() + "\n" +
                "Placa: " + contrato.getVeiculo().getPlaca() + "\n" +
                "Periodo: " + contrato.getInicio() +
                " ate " + contrato.getFim() + "\n" +
                String.format(
                        "Valor total: R$ %.2f%n",
                        contrato.getValorTotal()
                ) +
                "==========================================\n";
    }

    @Override
    public void imprimir() {
        System.out.println(gerar());
    }
}
