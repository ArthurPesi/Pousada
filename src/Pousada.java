import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class Pousada {
    private static final String TEXTOMENU = "Escolha uma opcao:\n" +
            "1: Consultar disponibilidade\n" +
            "2: Consultar reserva\n" +
            "3: Realizar reserva\n" +
            "4: Cancelar reserva\n" +
            "5: Realizar check-in\n" +
            "6: Realizar check-out\n" +
            "7: Registrar consumo\n" +
            "8: Salvar\n" +
            "0: Sair\n";
//TODO: botar variaveis em ordem
    private static Produto[] produtos;
    private static Quarto[] quartos;
    private static String nome = "Pousadona dos Guri";
    private static String contato = "(51) 96420-6911";
    private static Reserva[] reservas;
    private static int ultimaReserva = -1;
    private static String caminhoProjeto;

    public static void main(String[] args) throws Exception {
        caminhoProjeto = System.getProperty("java.class.path");
        
        carregaDados();
        System.out.println("Seja bem vindo a " + nome + ". Caso precise entrar em contato, ligue para " + contato);
        try {
            Scanner inputUsuario = new Scanner(System.in);

            while (true) {
                System.out.println(TEXTOMENU);
                int resposta = inputUsuario.nextInt();
                // Sair se escolher 0
                if (resposta == 0) {
                    System.out.println("Opcao escolhida: sair. Tchau!");
                    break;
                } else if (resposta == 1) {
                    System.out.println("Opcao escolhida: consultar disponibilidade");
                    System.out.println("Insira a data desejada:");
                    int data = inputUsuario.nextInt();
                    System.out.println("Insira o número do quarto desejado:");
                    int quartoNumero = inputUsuario.nextInt();

                    boolean disponivel = consultaDisponibilidade(data, quartoNumero);
                    if(disponivel) {//TODO: printar os dados do quarto
                        System.out.println("O quarto esta disponivel para essa data");
                    } else {
                        System.out.println("O quarto nao esta disponivel para essa data");
                    }
                } else if(resposta == 3) {
                    System.out.println("Opcao escolhida: realizar reserva");
                    System.out.println("Insira a data inicial da reserva");
                    int[] datas = new int[2];
                    datas[0] = inputUsuario.nextInt();
                    System.out.println("Insira a data final da reserva");
                    datas[1] = inputUsuario.nextInt();
                    System.out.println("Insira o nome para a reserva");
                    String nomeCliente = inputUsuario.next();
                    System.out.println("Insira o numero do quarto desejado");
                    int numeroQuarto = inputUsuario.nextInt();
                    Quarto quartoReserva = quartos[numeroQuarto];
                    String retorno = realizaReserva(datas, nomeCliente, quartoReserva);
                    System.out.println(retorno);
                    System.out.println(reservas[ultimaReserva].toString());
                }
                else if(resposta == 4) {
                    System.out.println("Opcao escolhida: cancelar reserva");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next();
                    cancelaReserva(nomeCliente);
                }
                else if(resposta == 5) {
                    System.out.println("Opcao escolhida: realizar check-in");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next();
                    realizaCheckIn(nomeCliente);
                } else {
                    System.out.println("Opcao invalida. Escolha uma das opcoes apresentadas abaixo. Digite apenas o numero");
                }
            }
            inputUsuario.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TODO: refatorar isso - botar uma funcao dentro das reservas
    public static boolean consultaDisponibilidade(int data, int numeroQuarto) {
        for (int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getDataInicio() <= data && reserva.getDataFim() >= data && reserva.getNumeroQuarto() == numeroQuarto)
                return false;
        }
        return true;
    }

    public static void realizaCheckIn(String cliente) {
        for (int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getCliente().equals(cliente)) {
                int retorno = reserva.realizaCheckIn();
                if(retorno == 0) {
                    System.out.println("Check-in realizado com sucesso");
                    System.out.println(reserva.dadosCheckIn());
                } else if(retorno == 1) {
                    System.out.println("Nao e possivel fazer check-in apos o cancelamento da reserva.");
                } else if(retorno == 2) {
                    System.out.println("O check-in ja havia sido realizado");
                } else if(retorno == 3) {
                    System.out.println("Nao e possivel realizar o check-in apos o check-out");
                }
                return;
            }
        }

        System.out.println("Nao foi encontrada nenhuma reserva com o nome " + cliente + ". Verifique se digitou corretamente");
    }
    public static void cancelaReserva(String cliente) {
        for (int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getCliente().equals(cliente)) {
                int resultado = reserva.cancelar();
                if(resultado == 0) {
                    System.out.println("Reserva cancelada com sucesso");
                } else if(resultado == 1) {
                    System.out.println("A reserva ja estava cancelada");
                } else if(resultado == 2) {
                    System.out.println("Nao e possivel cancelar a reserva apos o check-in");
                } else if(resultado == 3) {
                    System.out.println("Nao e possivel cancelar a reserva apos o check-out");
                }
                return;
            }
        }

        System.out.println("Nao foi encontrada nenhuma reserva com o nome " + cliente + ". Verifique se digitou corretamente");
    }

    public static String realizaReserva(int[] datas, String cliente, Quarto quarto) {
        //Verificar disponibilidade da reserva
        for (int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            int codigoDisponibilidade = reserva.verificarDisponibilidade(datas[0], datas[1], cliente, quarto);
            if(codigoDisponibilidade == 1) { 
                return "Ja ha reserva para esse cliente";
            } 
            if(codigoDisponibilidade == 2) {
                return "Esse quarto esta ocupado nesse periodo";//TODO: talvez mostrar as datas e ate outra opcao de quarto para o mesmo dia
            }
        }
        inserirReserva(datas[0], datas[1], cliente, quarto.getNumero());
        return "Reserva feita com sucesso";
    }
    //TODO:mudar para receber um objeto de quarto em todos lugares
    public static void inserirReserva(int diaInicio, int diaFim, String nome, int numeroQuarto) {
        ultimaReserva++;
        if(ultimaReserva == reservas.length) {
            reservas = Arrays.copyOf(reservas, reservas.length * 2);
        }
        reservas[ultimaReserva] = new Reserva(diaInicio, diaFim,nome, quartos[numeroQuarto]);
    }

    public static void carregaDados() {
        //carregar produtos
        ListaLigada informacoesProdutos = deserializar(caminhoProjeto + "/produto.txt");
        Nodo informacoesAtuais = informacoesProdutos.getPrimeiro();

        int quantidadeProdutos = informacoesProdutos.getTamanho();
        produtos = new Produto[quantidadeProdutos];

        for (int i = 0; i < quantidadeProdutos; i++) {
            String[] dados = informacoesAtuais.getConteudo().clone();
            String nomeProduto = dados[0];
            float preco = Float.parseFloat(dados[1]);
            int codigo = Integer.parseInt(dados[2]);
            produtos[codigo] = new Produto(nomeProduto, preco, codigo);

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
        reservas = new Reserva[quantidadeReservas * 2];

        for (int i = 0; i < quantidadeReservas; i++) {
            String[] dados = informacoesAtuais.getConteudo().clone();
            int diaInicio = Integer.parseInt(dados[0]);
            int diaFim = Integer.parseInt(dados[1]);
            String cliente = dados[2];
            int numeroQuarto = Integer.parseInt(dados[3]);
            Quarto quarto = quartos[numeroQuarto];
            char status = dados[4].charAt(0);
            reservas[i] = new Reserva(diaInicio, diaFim, cliente, quarto, status);
            ultimaReserva++;

            informacoesAtuais = informacoesAtuais.getProximo();
        }
    }

    private static ListaLigada deserializar(String caminhoArquivo) {
        ListaLigada informacoesArquivo = new ListaLigada();
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
