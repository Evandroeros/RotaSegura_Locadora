PROJETO ROTA SEGURA - LOCADORA DE VEICULOS

REQUISITOS
- Java 17 ou superior.
- Eclipse IDE.

ESTRUTURA

src/
  rota_segura/
    Main.java

    exception/
      LocadoraException.java

    model/
      Cliente.java
      Contrato.java
      Veiculo.java
      Popular.java
      Sedan.java
      SUV.java

    service/
      Locadora.java
      Documento.java
      ContratoDocumento.java
      RelatorioFechamento.java
      RelatorioAnalitico.java
      Persistencia.java

COMO EXECUTAR

1. Abra o Eclipse.
2. File > Import > Existing Projects into Workspace.
3. Selecione a pasta RotaSegura_Locadora.
4. Clique em Finish.
5. No Package Explorer, abra src > rota_segura.
6. Clique com o botao direito em Main.java.
7. Run As > Java Application.

O sistema possui:
- Cadastro de clientes com ID automatico.
- Cadastro de veiculos.
- Categorias Popular, Sedan e SUV.
- Calculo de diaria, seguro e manutencao.
- Controle de disponibilidade.
- Criacao e encerramento de contratos.
- Relatorio de fechamento.
- Persistencia do historico em TXT.
- Dashboard com KPIs.
- Filtros por CPF, placa, categoria e periodo.

OBSERVACAO
O projeto foi organizado em pacotes separados para facilitar
manutencao, entendimento e aplicacao dos conceitos de POO.
