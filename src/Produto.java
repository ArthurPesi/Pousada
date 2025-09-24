public class Produto {
    private int codigo;
    private String nome;
    private float preco;

    Produto(String nome, float preco, int codigo) {
    this.codigo = codigo;
    this.nome = nome;
    this.preco = preco;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | preco: " + preco + " | codigo: " + codigo;
    }

    public float getPreco() {
        return preco;
    }
}
