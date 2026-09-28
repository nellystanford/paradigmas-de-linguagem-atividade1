public class EstoqueApp {

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        System.out.println("1. Cadastro de produtos");
        try {
            estoque.adicionarProduto(new ProdutoComum("Arroz 5kg", 28.90, 10));
            estoque.adicionarProduto(new ProdutoComum("Detergente 500ml", 2.49, 30));
            estoque.adicionarProduto(new ProdutoPerecivel("Leite integral 1L", 5.79, 20, 2));
            estoque.adicionarProduto(new ProdutoPerecivel("Iogurte natural", 3.50, 15, 10));
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro inesperado no cadastro: " + e.getMessage());
        }
        estoque.listarProdutos();

        System.out.println("\n2. Tentativa de cadastro com quantidade negativa");
        try {
            estoque.adicionarProduto(new ProdutoComum("Feijão 1kg", 8.99, -5));
            System.out.println("Produto cadastrado (isto não deveria acontecer).");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        }

        System.out.println("\n3. Venda válida");
        try {
            estoque.venderProduto(0, 4);
            System.out.println("Venda realizada: 4 unidade(s) de " + estoque.getProduto(0).getNome() + ".");
            System.out.println("  Situação atual: " + estoque.getProduto(0).getDescricao());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("ProdutoIndisponivelException capturada: " + e.getMessage());
        }

        System.out.println("\n4. Venda acima do estoque disponível");
        try {
            estoque.venderProduto(2, 50);
            System.out.println("Venda realizada (isto não deveria acontecer).");
        } catch (ProdutoIndisponivelException e) {
            System.out.println("ProdutoIndisponivelException capturada: " + e.getMessage());
        }

        System.out.println("\n5. Sobrecarga de aplicarDesconto()");
        Product detergente = estoque.getProduto(1);
        detergente.aplicarDesconto(10);
        System.out.println("aplicarDesconto(10)       -> " + detergente.getDescricao());

        Product arroz = estoque.getProduto(0);
        arroz.aplicarDesconto(50, 5.00);
        System.out.println("aplicarDesconto(50, 5.00) -> " + arroz.getDescricao()
                + "  (50% daria R$ 14,45, mas o teto é R$ 5,00)");

        System.out.println("\n6. Vários catches no mesmo try (do mais específico ao mais genérico)");
        Object[][] pedidos = {
                { "Café 500g", 17.90, 8, 3 },
                { "Açúcar 1kg", 4.99, -2, 1 },
                { "Farinha 1kg", 6.49, 5, 12 },
        };
        for (Object[] p : pedidos) {
            try {
                cadastrarEVender(estoque, (String) p[0], (double) p[1], (int) p[2], (int) p[3]);
                System.out.println("Pedido de " + p[0] + " concluído.");
            } catch (QuantidadeInvalidaException e) {
                System.out.println("QuantidadeInvalidaException: " + e.getMessage());
            } catch (ProdutoIndisponivelException e) {
                System.out.println("ProdutoIndisponivelException: " + e.getMessage());
            } catch (EstoqueException e) {
                System.out.println("EstoqueException: " + e.getMessage());
            }
        }

        System.out.println("\n7. Valor total do estoque");
        for (Product p : estoque.getProdutos()) {
            System.out.printf(Product.PT_BR, "  %-18s %-24s R$ %8.2f%n",
                    p.getClass().getSimpleName(), p.getNome(), p.calcularValorTotal());
        }
        System.out.printf(Product.PT_BR, "  %-43s R$ %8.2f%n", "TOTAL", estoque.calcularValorTotalEstoque());
    }

    private static void cadastrarEVender(Estoque estoque, String nome, double preco, int quantidade, int venda)
            throws EstoqueException {
        estoque.adicionarProduto(new ProdutoComum(nome, preco, quantidade));
        int indice = estoque.getProdutos().size() - 1;
        estoque.venderProduto(indice, venda);
    }
}
