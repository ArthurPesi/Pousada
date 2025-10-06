import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class Quarto implements Serializable {
    private int numero;
    private char categoria;
    private float diaria;
    private int[] consumo;
    private Produto[] produtosDisponiveis;
    private int ultimaPosicaoConsumo = 0;

    Quarto(int numero, char categoria, float diaria, Produto[] produtosDisponiveis) {
        this.numero = numero;
        this.categoria = categoria;
        this.diaria = diaria;
        consumo = new int[32];
        Arrays.fill(consumo, -1);
        this.produtosDisponiveis = produtosDisponiveis.clone();
    }

    Quarto(int numero, char categoria, float diaria, Produto[] produtosDisponiveis, int[] consumo) {
        this.numero = numero;
        this.categoria = categoria;
        this.diaria = diaria;
        this.consumo = consumo.clone();
        this.ultimaPosicaoConsumo = this.consumo.length -1;
        this.produtosDisponiveis = produtosDisponiveis.clone();
    }

    //Adicionar consumo na copa
    public void adicionaConsumo(int codigo) {
        if(ultimaPosicaoConsumo + 1 == consumo.length) {//Duplicar tamanho se faltar espaco
            consumo = Arrays.copyOf(consumo, consumo.length * 2);
        }

        consumo[ultimaPosicaoConsumo] = codigo;//Adicionar consumo para a lista
        ultimaPosicaoConsumo++;
        System.out.println("Consumo adicionado para o quarto " + numero);
    }

    public void limpaConsumo() {//Limpar lista de consumo
        ultimaPosicaoConsumo = 0;
    }


    public float valorTotalConsumo() {
        float resultado = 0;
        for (int i = 0; i <= ultimaPosicaoConsumo; i++) {
            int idProdutoAtual = consumo[i];
            if(idProdutoAtual < 0) {
                break;
            }
            resultado += produtosDisponiveis[idProdutoAtual].getPreco();//Somar preco de cada produto
        }
        return resultado;
    }

    public String listaConsumo() {
        String resultado = "";
        for (int i = 0; i <= ultimaPosicaoConsumo; i++) {
            int idProdutoAtual = consumo[i];
            if(idProdutoAtual < 0) {
                break;
            }
            resultado += produtosDisponiveis[idProdutoAtual].toString();//Listar produtos
            resultado += "\n";
        }
        return resultado;
    }

    public String getInformacoes() {
        return "numero: " + numero + " | categoria: " + categoria + " | diaria: " + diaria + "\nprodutos consumidos:\n" + listaConsumo();
    }

    //Getters e setters
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

    public float calcularPrecoDiarias(int quantidadeDias) {
        return diaria * quantidadeDias;
    }

    //Serializacao
    @Override
    public String toString() {
        return numero + '\t' + categoria + '\t' + diaria + '\t' + Arrays.toString(consumo) + '\t' + Arrays.toString(produtosDisponiveis) + '\t' + ultimaPosicaoConsumo;
    }
}
