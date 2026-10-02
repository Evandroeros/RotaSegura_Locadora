package rota_segura.service;

import rota_segura.exception.LocadoraException;
import rota_segura.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Locadora {

    private final List<Veiculo> frota = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Contrato> historico = new ArrayList<>();

    public void cadastrarVeiculo(Veiculo veiculo)
            throws LocadoraException {

        if (veiculo == null) {
            throw new LocadoraException("Veiculo invalido.");
        }

        if (buscarVeiculo(veiculo.getPlaca()) != null) {
            throw new LocadoraException(
                    "Ja existe veiculo com esta placa."
            );
        }

        frota.add(veiculo);
    }

    public void cadastrarCliente(Cliente cliente)
            throws LocadoraException {

        if (cliente == null) {
            throw new LocadoraException("Cliente invalido.");
        }

        if (buscarCliente(cliente.getId()) != null) {
            throw new LocadoraException(
                    "Ja existe cliente com este ID."
            );
        }

        clientes.add(cliente);
    }

    public Contrato alugar(
            int idCliente,
            String placa,
            LocalDate inicio,
            LocalDate fim)
            throws LocadoraException {

        Cliente cliente = buscarCliente(idCliente);

        if (cliente == null) {
            throw new LocadoraException(
                    "Cliente nao encontrado."
            );
        }

        Veiculo veiculo = buscarVeiculo(placa);

        if (veiculo == null) {
            throw new LocadoraException(
                    "Veiculo nao encontrado."
            );
        }

        if (!veiculo.isDisponivel()) {
            throw new LocadoraException(
                    "Veiculo ocupado ou indisponivel."
            );
        }

        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new LocadoraException(
                    "Datas inconsistentes."
            );
        }

        Contrato contrato =
                new Contrato(cliente, veiculo, inicio, fim);

        veiculo.setDisponivel(false);
        historico.add(contrato);

        return contrato;
    }

    public void encerrarContrato(int idContrato)
            throws LocadoraException {

        Contrato contrato = buscarContrato(idContrato);

        if (contrato == null) {
            throw new LocadoraException(
                    "Contrato nao encontrado."
            );
        }

        if (contrato.isEncerrado()) {
            throw new LocadoraException(
                    "Contrato ja encerrado."
            );
        }

        contrato.encerrar();
    }

    public Veiculo buscarVeiculo(String placa) {
        if (placa == null) {
            return null;
        }

        for (Veiculo v : frota) {
            if (v.getPlaca().equalsIgnoreCase(placa.trim())) {
                return v;
            }
        }

        return null;
    }

    public Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public Contrato buscarContrato(int id) {
        for (Contrato c : historico) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public List<Veiculo> getFrota() {
        return new ArrayList<>(frota);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public List<Contrato> getHistorico() {
        return new ArrayList<>(historico);
    }
}
