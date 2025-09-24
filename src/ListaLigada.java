public class ListaLigada {
    private Nodo primeiro;
    private int tamanho = 0;

    public int getTamanho() {
        return this.tamanho;
    }

    public void inserir(String[] valores) {
        tamanho++;
        Nodo novoNodo = new Nodo(valores, this.primeiro);
        this.primeiro = novoNodo;
    }

    public Nodo getPrimeiro() {
        return this.primeiro;
    }
}
