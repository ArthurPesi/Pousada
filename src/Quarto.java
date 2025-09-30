import java.util.Arrays;

public class Quarto {
    private int numero;
    private char categoria;
    private float diaria;
        //TODO: aumentar esse cara
    private int[] consumo;
    private Produto[] produtosDisponiveis;
    private int ultimaPosicaoConsumo = 0;

    //TODO: fazer metodos
    Quarto(int numero, char categoria, float diaria, Produto[] produtosDisponiveis) {
        this.numero = numero;
        this.categoria = categoria;
        this.diaria = diaria;
        //TODO: aumentar esse cara
        consumo = new int[32];
        Arrays.fill(consumo, -1);
        this.produtosDisponiveis = produtosDisponiveis.clone();
    }

    Quarto(int numero, char categoria, float diaria, Produto[] produtosDisponiveis, int[] consumo) {
        this.numero = numero;
        this.categoria = categoria;
        this.diaria = diaria;
        this.consumo = consumo.clone();
        this.produtosDisponiveis = produtosDisponiveis.clone();
    }

    public void adicionaConsumo(int codigo) {
        consumo[ultimaPosicaoConsumo] = codigo;
        ultimaPosicaoConsumo++;
    }

    public void limpaConsumo() {
        ultimaPosicaoConsumo = 0;
    }

    public float calcularPrecoDiarias(int quantidadeDias) {
        return diaria * quantidadeDias;
    }

    public float valorTotalConsumo() {
        float resultado = 0;
        for (int i = 0; i < consumo.length; i++) {
            int idProdutoAtual = consumo[i];
            if(idProdutoAtual < 0) {
                break;
            }
            resultado += produtosDisponiveis[idProdutoAtual].getPreco();
        }
        return resultado;
    }

    public String listaConsumo() {
        String resultado = "";
        for (int i = 0; i < consumo.length; i++) {
            int idProdutoAtual = consumo[i];
            if(idProdutoAtual < 0) {
                break;
            }
            resultado += produtosDisponiveis[idProdutoAtual].toString();
            resultado += "\n";
        }
        return resultado;
    }

    @Override
    public String toString() {
        return "numero: " + numero + " | categoria: " + categoria + " | diaria: " + diaria + "\nprodutos consumidos:\n" + listaConsumo();
    }

    public int getNumero() {
        return numero;
    }

	public String getFormaArquivo() {
        String textoProdutos = "";
        boolean primeiro = true;
        for(int produto: consumo) {
            if(!primeiro) {
                if(produto == -1) {
                    break;
                }
                textoProdutos += ",";
            }
            textoProdutos += produto;
            primeiro = false;
            if(produto == -1)
                break;
        }
        return numero + ";" + categoria + ";" + (int)diaria + ";" + textoProdutos;
	}
}
