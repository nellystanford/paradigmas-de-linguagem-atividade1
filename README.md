# Sistema de Estoque de Produtos (Java)

Exercício de POO: classes abstratas, herança, interfaces, polimorfismo (estático e dinâmico), composição e exceções.

## Estrutura

```
src/
  EstoqueException.java             
  QuantidadeInvalidaException.java 
  ProdutoIndisponivelException.java 
  Vendavel.java                     
  Product.java                      
  ProdutoComum.java                
  ProdutoPerecivel.java             
  Estoque.java                      
  EstoqueApp.java                   
saida.txt                         
```

## Como compilar e executar

Requer JDK 17 ou superior.

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out EstoqueApp
```

## O que a saída demonstra

1. Cadastro de 2 produtos comuns e 2 perecíveis (o leite vence em 2 dias e recebe desconto).
2. `QuantidadeInvalidaException` ao cadastrar um produto com quantidade negativa.
3. Uma venda bem-sucedida.
4. `ProdutoIndisponivelException` ao vender mais do que o disponível.
5. As duas versões sobrecarregadas de `aplicarDesconto()`.
6. Um único `try` com três `catch`, do mais específico (`QuantidadeInvalidaException`,
   `ProdutoIndisponivelException`) ao mais genérico (`EstoqueException`).
7. O valor total do estoque, somando produtos comuns e perecíveis por polimorfismo.