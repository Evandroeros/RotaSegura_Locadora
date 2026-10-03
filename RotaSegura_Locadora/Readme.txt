========================================================================
               SISTEMA DE LOCADORA DE VEÍCULOS - ROTA SEGURA
========================================================================

1. DESCRIÇÃO DO PROJETO
-----------------------
O RotaSegura é um sistema desenvolvido em Java projetado para gerenciar
as operações de uma locadora de automóveis. O software lida com o 
cadastro de clientes, controle de diferentes categorias de veículos, 
emissão e persistência de contratos, além da geração de relatórios.

2. ARQUITETURA E COMPONENTES DO SISTEMA
---------------------------------------
O projeto está estritamente estruturado sob o pacote 'rota_segura':

* model/
  - Veiculo (Classe abstrata base para a frota)
  - Popular, Sedan, SUV (Especializações de Veiculo com regras próprias)
  - Cliente (Dados cadastrais do locatário)
  - Contrato (Entidade que une Cliente, Veiculo e período de locação)

* service/
  - Locadora (Gerencia o fluxo de locações e regras de negócio principais)
  - Documento & ContratoDocumento (Abstração e geração de arquivos de contratos)
  - Persistencia (Mecanismo para salvar/carregar dados do sistema)
  - RelatorioAnalitico & RelatorioFechamento (Geração de relatórios de auditoria)

* exception/
  - LocadoraException (Tratamento personalizado de erros de negócio)

* Main.java
  - Ponto de entrada do aplicativo que executa o fluxo do sistema.

3. REQUISITOS DO AMBIENTE
--------------------------
* Java Development Kit: JDK 8 ou superior (Recomendado Java 17 LTS)
* IDE Recomendada: Eclipse (projeto contém metadados .classpath/.project) 
  ou IntelliJ IDEA / VS Code.

4. COMO EXECUTAR O PROJETO
---------------------------
Via IDE (Método Recomendado):
1. Abra sua IDE (ex: Eclipse ou IntelliJ).
2. Importe o projeto contido na pasta "RotaSegura_Locadora".
3. Execute a classe principal 'Main.java' localizada no pacote 'rota_segura'.

Via Linha de Comando (Terminal):
1. Clique em Lançamentos, na coluna da direita da página principal;
2. Clique em “RotaSegura.zip” e baixe o arquivo;
3. Abra a pasta de Downloads no Explorador de Arquivos;
4. Clique com o botão direito do mouse no arquivo RotaSegura;
5. Escolha Extrair tudo... (ou Extract All) e clique no botão Extrair;
6. Abra a nova pasta gerada: Pasta Extraída.
7. Entre na nova pasta que foi criada com o nome RotaSegura;
8. Abra o CMD diretamente na pasta: Barra Superior;
9. Com a pasta aberta, onde se encontra o arquivo .jar(RotaSegura), clique 
no espaço em branco da barra de endereços no topo;
10. Escreva “cmd” e tecle Enter;
11. Execute o programa: Terminal.
12. Na janela preta do Prompt de Comando que abrir, digite:
          java -jar RotaSegura.jar
13.Em seuida, o programa será executado.

5. PERSISTÊNCIA DE DADOS
------------------------
O sistema grava as movimentações e o histórico de execuções diretamente
no arquivo local:
* RotaSegura_Locadora/historico_locacoes.txt

========================================================================
                     Desenvolvido por: Evandro - DSM
========================================================================
