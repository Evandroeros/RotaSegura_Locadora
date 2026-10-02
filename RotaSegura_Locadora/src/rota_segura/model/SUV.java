package rota_segura.model;

public class SUV extends Veiculo {

    public SUV(
            String placa,
            String modelo,
            int ano,
            double valorDiaria) {

        super(placa, modelo, ano, valorDiaria);
    }

    @Override
    public double calcularSeguroDiario() {
        return getValorDiaria() * 0.12;
    }

    @Override
    public double calcularManutencaoDiaria() {
        return getValorDiaria() * 0.08;
    }

    @Override
    public String getCategoria() {
        return "SUV";
    }
}
