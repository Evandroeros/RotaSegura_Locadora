package rota_segura.model;

public class Cliente {

    private static int proximoId = 1;
    private final int id;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String email) {
        this.id = proximoId++;

        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank() || nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome invalido.");
        }
        this.nome = nome.trim();
    }

    public void setCpf(String cpf) {
        String cpfLimpo = cpf == null ? "" : cpf.replaceAll("\\D", "");

        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException(
                    "CPF deve possuir 11 digitos."
            );
        }

        this.cpf = cpfLimpo;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException(
                    "Telefone obrigatorio."
            );
        }

        this.telefone = telefone.trim();
    }

    public void setEmail(String email) {
        if (email == null ||
                !email.trim().matches(
                        "^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {

            throw new IllegalArgumentException(
                    "E-mail invalido."
            );
        }

        this.email = email.trim();
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | %s | CPF: %s | Tel: %s | E-mail: %s",
                id, nome, cpf, telefone, email
        );
    }
}
