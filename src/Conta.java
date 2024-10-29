import java.util.ArrayList;
import java.util.List;

public class Conta {
    private double saldo;
    private List<Transacao> transacoes;

    public Conta() {
        this.saldo = 0.0;
        this.transacoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        if (transacao.getTipo().equals("receita")) {
            saldo += transacao.getValor();
        } else if (transacao.getTipo().equals("despesa")) {
            saldo -= transacao.getValor();
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    // Outros métodos como removerTransacao(), calcularSaldo(), etc.
}
