public class ProdutoPerecivel extends Product {

    private static final int LIMITE_DIAS_DESCONTO = 3;
    private static final double TAXA_DESCONTO_VENCIMENTO = 0.20;

    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    public boolean isPertoDoVencimento() {
        return diasParaVencer <= LIMITE_DIAS_DESCONTO;
    }

    @Override
    public double calcularValorTotal() {
        double valorBruto = getPreco() * getQuantidade();
        if (isPertoDoVencimento()) {
            return valorBruto * (1 - TAXA_DESCONTO_VENCIMENTO);
        }
        return valorBruto;
    }

    @Override
    public String getDescricao() {
        String validade = "Vence em " + diasParaVencer + " dia(s)";
        if (isPertoDoVencimento()) {
            validade += " (desconto automático de 20% no valor total)";
        }
        return super.getDescricao() + " | " + validade;
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }
}
