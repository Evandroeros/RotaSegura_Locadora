package rota_segura.model;

public class Popular extends Veiculo {

    public Popular(
            String placa,
            String modelo,
            int ano,
            double valorDiaria) {

        super(placa, modelo, ano, valorDiaria);
    }

    @Override
    public double calcularSeguroDiario() {
        return getValorDiaria() * 0.05;
    }

    @Override
    public double calcularManutencaoDiaria() {
        return getValorDiaria() * 0.03;
    }

    @Override
    public String getCategoria() {
        return "POPULAR";
    }
}
