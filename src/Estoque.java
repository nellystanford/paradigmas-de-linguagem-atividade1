import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Composição: Estoque TEM UMA lista de produtos, não herda de Product.
public class Estoque {

    private final List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Não é possível adicionar um produto nulo.");
        }
        produtos.add(p);
    }

    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        if (indice < 0 || indice >= produtos.size()) {
            throw new ProdutoIndisponivelException("Não existe produto cadastrado no índice " + indice + ".");
        }
        produtos.get(indice).vender(quantidade);
    }

    // Polimorfismo: cada produto calcula do seu jeito, o Estoque não precisa saber o subtipo.
    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Product p : produtos) {
            total += p.calcularValorTotal();
        }
        return total;
    }

    public Product getProduto(int indice) {
        return produtos.get(indice);
    }

    public List<Product> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    public void listarProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println("  [" + i + "] " + produtos.get(i).getDescricao());
        }
    }
}
