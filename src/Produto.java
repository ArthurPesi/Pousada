import java.io.Serializable;

public class Produto implements Serializable {
    private int codigo;
    private String nome;
    private float preco;

    Produto(String nome, float preco, int codigo) {
    this.codigo = codigo;
    this.nome = nome;
    this.preco = preco;
    }

    public String getInformacoes() {
        return "Nome: " + nome + " | preco: " + preco + " | codigo: " + codigo;
    }

    //Getters e setters
    public float getPreco() {
        return preco;
    }
    
    //Serializacao
    @Override
    public String toString() {
        return "" + codigo + '\t' + nome + '\t' + preco;
    }
}
