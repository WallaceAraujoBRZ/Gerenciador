import java.time.LocalDate;

public class Transacao {
    private double valor;
    private String descricao;
    private LocalDate data;
    private String tipo; // "receita" ou "despesa"

    public Transacao(double valor, String descricao, LocalDate data, String tipo) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.tipo = tipo;
    }

    // Getters e Setters
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
