package rota_segura.service;

import rota_segura.model.Contrato;

import java.io.*;
import java.util.List;

public class Persistencia {

    private final String arquivo;

    public Persistencia(String arquivo) {
        this.arquivo = arquivo;
    }

    public void salvarContratos(List<Contrato> contratos)
            throws IOException {

        try (BufferedWriter bw =
                     new BufferedWriter(
                             new FileWriter(arquivo))) {

            for (Contrato c : contratos) {

                bw.write(
                        c.getId() + ";" +
                        c.getCliente().getId() + ";" +
                        c.getCliente().getNome() + ";" +
                        c.getVeiculo().getPlaca() + ";" +
                        c.getVeiculo().getCategoria() + ";" +
                        c.getInicio() + ";" +
                        c.getFim() + ";" +
                        c.getValorTotal() + ";" +
                        c.isEncerrado()
                );

                bw.newLine();
            }
        }
    }

    public void exibirArquivo() throws IOException {

        File arquivoHistorico = new File(arquivo);

        if (!arquivoHistorico.exists()) {
            System.out.println(
                    "Nenhum historico persistido ainda."
            );
            return;
        }

        System.out.println(
                "\n===== HISTORICO SALVO EM ARQUIVO ====="
        );

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(arquivoHistorico))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        }
    }
}
