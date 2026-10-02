package rota_segura.model;

public class Sedan extends Veiculo {

    public Sedan(
            String placa,
            String modelo,
            int ano,
            double valorDiaria) {

        super(placa, modelo, ano, valorDiaria);
    }

    @Override
    public double calcularSeguroDiario() {
        return getValorDiaria() * 0.08;
    }

    @Override
    public double calcularManutencaoDiaria() {
        return getValorDiaria() * 0.05;
    }

    @Override
    public String getCategoria() {
        return "SEDAN";
    }
}
