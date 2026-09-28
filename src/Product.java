import java.util.Locale;

public abstract class Product implements Vendavel {

    protected static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        if (preco < 0) {
            throw new QuantidadeInvalidaException(
                    String.format(PT_BR, "Preço inválido para \"%s\": %.2f. O preço não pode ser negativo.", nome, preco));
        }
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException(
                    String.format(PT_BR, "Quantidade inválida para \"%s\": %d. A quantidade não pode ser negativa.", nome, quantidade));
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Cada subclasse define a sua regra de cálculo.
    public abstract double calcularValorTotal();

    public String getDescricao() {
        return String.format(PT_BR, "%s | Preço: R$ %.2f | Quantidade: %d", nome, preco, quantidade);
    }

    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada <= 0) {
            throw new IllegalArgumentException("A quantidade vendida deve ser maior que zero.");
        }
        if (quantidadeDesejada > quantidade) {
            throw new ProdutoIndisponivelException(String.format(PT_BR,
                    "Estoque insuficiente de \"%s\": pedido de %d unidade(s), mas só há %d disponível(is).",
                    nome, quantidadeDesejada, quantidade));
        }
        quantidade -= quantidadeDesejada;
    }

    // Sobrecarga (polimorfismo estático): mesmo nome, assinaturas diferentes.
    public void aplicarDesconto(double percentual) {
        validarPercentual(percentual);
        preco -= preco * percentual / 100.0;
    }

    public void aplicarDesconto(double percentual, double descontoMaximo) {
        validarPercentual(percentual);
        if (descontoMaximo < 0) {
            throw new IllegalArgumentException("O desconto máximo não pode ser negativo.");
        }
        double desconto = Math.min(preco * percentual / 100.0, descontoMaximo);
        preco -= desconto;
    }

    private void validarPercentual(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("O percentual deve estar entre 0 e 100.");
        }
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
