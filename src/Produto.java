import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 8128310456923919123L;
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

    //Getters e setters
    public float getPreco() {
        return preco;
    }
    
    //Serializacao
    //@Override
    //public String toString() {
    //    return "" + codigo + '\t' + nome + '\t' + preco;
    //}
}
