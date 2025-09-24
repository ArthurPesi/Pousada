import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;

public class Pousada {
    private static final String TEXTOMENU = "\n1: Consultar disponibilidade\n" +
            "2: Consultar reserva\n" +
            "3: Realizar reserva\n" +
            "4: Cancelar reserva\n" +
            "5: Realizar check-in\n" +
            "6: Realizar check-out\n" +
            "7: Registrar consumo\n" +
            "8: Salvar\n" +
            "0: Sair\n";

    private static Produto[] produtos;
    private static Quarto[] quartos;
    private static Reserva[] reservas;
    private static String caminhoProjeto;

    public static void main(String[] args) throws Exception {
        caminhoProjeto = new File("").getAbsolutePath();
        
        carregaDados();


        System.out.println(TEXTOMENU);

        try {
            Scanner inputUsuario = new Scanner(System.in);

            while (true) {
                int resposta = inputUsuario.nextInt();
                // Sair se escolher 0
                if (resposta == 0) {
                    break;
                }

            }
            inputUsuario.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TODO: refatorar isso
    public static boolean consultaDisponibilidade(int data, Quarto quarto) {
        for (Reserva reserva : reservas) {
            if(reserva.getDataInicio() < data && reserva.getDataFim() > data && reserva.getNumeroQuarto() == quarto.getNumero())
                return false;
        }
        return true;
    }

    public static void carregaDados() {
        //carregar produtos
        ListaLigada informacoesProdutos = deserializar(caminhoProjeto + "/produto.txt");
        Nodo informacoesAtuais = informacoesProdutos.getPrimeiro();

        int quantidadeProdutos = informacoesProdutos.getTamanho();
        produtos = new Produto[quantidadeProdutos];

        for (int i = 0; i < quantidadeProdutos; i++) {
            String[] dados = informacoesAtuais.getConteudo().clone();
            String nome = dados[0];
            float preco = Float.parseFloat(dados[1]);
            int codigo = Integer.parseInt(dados[2]);
            produtos[codigo] = new Produto(nome, preco, codigo);

            informacoesAtuais = informacoesAtuais.getProximo();
        }

        //carregar quartos
        ListaLigada informacoesQuartos = deserializar(caminhoProjeto + "/quarto.txt");
        informacoesAtuais = informacoesQuartos.getPrimeiro();

        int quantidadeQuartos = informacoesQuartos.getTamanho();
        quartos = new Quarto[quantidadeQuartos];

        for (int i = 0; i < quantidadeQuartos; i++) {
            String[] dados = informacoesAtuais.getConteudo().clone();
            int numero = Integer.parseInt(dados[0]);
            char categoria = dados[1].charAt(0);
            float diaria = Float.parseFloat(dados[2]);

            //pegar lista de itens consumidos
            String[] consumoString = dados[3].split(",");
            int[] consumo = new int[consumoString.length];
            for(int j = 0; j < consumoString.length; j++) {
                consumo[j] = Integer.parseInt(consumoString[j]);
            }

            if(consumo[0] == -1) {
                //Se nao houver consumo
                quartos[numero] = new Quarto(numero, categoria, diaria, produtos);
            } else {
                quartos[numero] = new Quarto(numero, categoria, diaria, produtos, consumo);
            }
            informacoesAtuais = informacoesAtuais.getProximo();
        }

        //carregar reservas
        ListaLigada informacoesReservas = deserializar(caminhoProjeto + "/reserva.txt");
        informacoesAtuais = informacoesReservas.getPrimeiro();

        int quantidadeReservas = informacoesReservas.getTamanho();
        reservas = new Reserva[quantidadeReservas];

        for (int i = 0; i < quantidadeReservas; i++) {
            String[] dados = informacoesAtuais.getConteudo().clone();
            int diaInicio = Integer.parseInt(dados[0]);
            int diaFim = Integer.parseInt(dados[1]);
            String cliente = dados[2];
            int numeroQuarto = Integer.parseInt(dados[3]);
            Quarto quarto = quartos[numeroQuarto];
            char status = dados[4].charAt(0);
            reservas[i] = new Reserva(diaInicio, diaFim, cliente, quarto, status);

            informacoesAtuais = informacoesAtuais.getProximo();
        }
    }

    private static ListaLigada deserializar(String caminhoArquivo) {
        ListaLigada informacoesArquivo = new ListaLigada();
        // Nodo primeiroNodo = new Nodo();
        try {
            File arquivoALer = new File(caminhoArquivo);
            BufferedReader LeitorArquivo = new BufferedReader(new FileReader(arquivoALer));

            String linha;
            while ((linha = LeitorArquivo.readLine()) != null) {
                String[] linhaDiscriminada = linha.split(";");
                informacoesArquivo.inserir(linhaDiscriminada);
            }
            LeitorArquivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informacoesArquivo;
    }

}
