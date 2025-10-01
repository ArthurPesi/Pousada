import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class Pousada {
    private static String nome = "Pousadona dos Guri";
    private static String contato = "(51) 96420-6911";
    private static Produto[] produtos;
    private static Quarto[] quartos;
    private static Reserva[] reservas;

    private static int ultimaReserva = -1;

    private static String caminhoProjeto;
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

    public static void main(String[] args) throws Exception {
        caminhoProjeto = System.getProperty("java.class.path");
        
        carregaDados();
        System.out.println("Seja bem vindo a " + nome + ". Caso precise entrar em contato, ligue para " + contato);
        try {
            Scanner inputUsuario = new Scanner(System.in);

            while (true) { //TEST: testar todas opcoes
                System.out.println(TEXTOMENU);
                int resposta = inputUsuario.nextInt();
                // Sair se escolher 0
                if (resposta == 0) {
                    System.out.println("Opcao escolhida: sair.");
                    System.out.println("Salvando seus dados...");
                    salvaDados();
                    System.out.println("Tchau!");
                    break;
                } else if (resposta == 1) {
                    System.out.println("Opcao escolhida: consultar disponibilidade");
                    System.out.println("Insira a data desejada:");
                    int data = inputUsuario.nextInt();
                    System.out.println("Insira o número do quarto desejado:");
                    int quartoNumero = inputUsuario.nextInt();
                    Quarto quarto = quartos[quartoNumero];
                    boolean disponivel = consultaDisponibilidade(data, quarto);
                    if(disponivel) {
                        System.out.println("O quarto esta disponivel para essa data. Informacoes do quarto:");
                        System.out.println(quarto.toString());
                    } else {
                        System.out.println("O quarto nao esta disponivel para essa data");
                    }
                } else if(resposta == 2) {
                    System.out.println("Opcao escolhida: consultar reserva");
                    System.out.println("Insira a data (-1 se nao quiser incluir data na pesquisa");
                    int data = inputUsuario.nextInt();

                    System.out.println("Insira o cliente (Nao se nao quiser incluir cliente na pesquisa");
                    String nomeCliente = inputUsuario.next().toLowerCase();

                    System.out.println("Insira o numero do quarto (-1 se nao quiser incluir quarto na pesquisa");
                    int numeroQuarto = inputUsuario.nextInt();

                    consultaReserva(data, nomeCliente, numeroQuarto);
                    
                }else if(resposta == 3) {
                    System.out.println("Opcao escolhida: realizar reserva");
                    System.out.println("Insira a data inicial da reserva");
                    int[] datas = new int[2];
                    datas[0] = inputUsuario.nextInt();
                    System.out.println("Insira a data final da reserva");
                    datas[1] = inputUsuario.nextInt();
                    System.out.println("Quartos disponíveis:");
                    for(int i = 0; i < quartos.length; i++) {
                        if(i == 0) {
                            System.out.println("S (simples) diaria = 1");
                        } else if(i == 10) {
                            System.out.println("\nM (master) diaria = 100");
                        } else if (i == 20) {
                            System.out.println("\nP (premium) diaria = 100.000");
                        }
                        if(consultaDisponibilidade(datas[0], datas[1], quartos[i])) {
                            System.out.print(quartos[i].getNumero() + " ");
                        }
                    }
                    System.out.println();
                    System.out.println("Insira o numero do quarto desejado"); 
                    int numeroQuarto = inputUsuario.nextInt(); 

                    System.out.println("Insira o nome para a reserva. Nao use o nome 'Nao'");
                    String nomeCliente = inputUsuario.next().toLowerCase(); 

                    Quarto quartoReserva = quartos[numeroQuarto];
                    realizaReserva(datas, nomeCliente, quartoReserva);
                }
                else if(resposta == 4) {
                    System.out.println("Opcao escolhida: cancelar reserva");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();
                    cancelaReserva(nomeCliente);
                }
                else if(resposta == 5) {
                    System.out.println("Opcao escolhida: realizar check-in");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();
                    realizaCheckIn(nomeCliente);
                } else if(resposta == 6) {
                    System.out.println("Opcao escolhida: realizar check-out");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();
                    realizaCheckOut(nomeCliente);

                } else if (resposta == 7) {
                    System.out.println("Opcao escolhida: registrar consumo");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();
                    int reservaNumero = getReservaValidaPorCliente(nomeCliente, 'I'); 
                    if(reservaNumero == -1) {
                        System.out.println("Nao ha reserva em check-in para o nome informado. Verifique se ha erros de digitacao");
                        continue;
                    }
                    System.out.println("Escolha um dos produtos:");
                    for(int i = 0; i < produtos.length; i++) {
                        System.out.println(i + ": " + produtos[i].toString());
                    }
                    int produtoEscolhido = inputUsuario.nextInt();

                    Quarto quarto = reservas[reservaNumero].getQuarto();
                    quarto.adicionaConsumo(produtoEscolhido);
                } else if (resposta == 8) {
                    System.out.println("Opcao escolhida: salvar dados");
                    salvaDados();
                } else {
                    System.out.println("Opcao invalida. Escolha uma das opcoes apresentadas abaixo. Digite apenas o numero");
                }
            }
            inputUsuario.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean consultaDisponibilidade(int data, Quarto quarto) {
        for (int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getStatus() == 'O' || reserva.getStatus() == 'C') {
                continue;
            }
            if(reserva.getNumeroQuarto() != quarto.getNumero()) {
                continue;
            }
            
            if(reserva.incluiDia(data))
                return false;
        }
        return true;
    }

    public static boolean consultaDisponibilidade(int dataInicio, int dataFim, Quarto quarto) {
        for( int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getStatus() == 'O' || reserva.getStatus() == 'C') {
                continue;
            }
            if(reserva.getNumeroQuarto() != quarto.getNumero()) {
                continue;
            }
            if(reserva.getDataInicio()  > dataFim || dataInicio > reserva.getDataFim()) {
                continue; //Se uma comeca depois do fim da outra, nao ha colisao. Caso contrario, nao esta
                //disponivel e retorna falso.
            } else {
                return false;
            }
        }

        return true;
    } 

    public static void consultaReserva(int data, String cliente, int quartoNumero) {
        String textoReservas = "";
        for(int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getNumeroQuarto() != quartoNumero && quartoNumero != -1) {
                continue;
            } 
            else if(!reserva.getCliente().equals(cliente) && !cliente.equals("Nao")) {
                continue;
            }
            else if(!reserva.incluiDia(data) && data != -1) {
                continue;
            }
            textoReservas += reserva.toString() + "\n";
        }

        if(textoReservas.equals("")) {
            System.out.println("nenhuma reserva encontrada para os dados fornecidos");
        } else {
            System.out.print("Reservas encontradas:\n" + textoReservas);
        }
    }

    public static boolean consultaClientePodeReservar(String cliente) {
        int reservaAtiva = getReservaValidaPorCliente(cliente, 'A');
        int reservaIn = getReservaValidaPorCliente(cliente, 'I');
        if(reservaAtiva == -1 && reservaIn == -1) {
            return true;
        } else {
            return false;
        }
    }

    //retorna um indice na array de reservas. Se nao houver, retorna -1
    public static int getReservaValidaPorCliente(String cliente, char statusDesejado) {
        for(int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getCliente().equals(cliente)) {
                char status = reserva.getStatus();
                if(status == statusDesejado) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void realizaCheckIn(String cliente) {
        int indiceReserva = getReservaValidaPorCliente(cliente, 'A');
        if(indiceReserva == -1) {
            System.out.println("Nao ha reserva ativa nesse nome. Verifique se nao ha erros de digitacao");
            return;
        }

        Reserva reserva = reservas[indiceReserva];
        String retorno = reserva.realizaCheckIn();
        System.out.println("Check-in realizado com sucesso.\n" + retorno);
    }

    public static void realizaCheckOut(String cliente) {
        int indiceReserva = getReservaValidaPorCliente(cliente, 'I');
        if(indiceReserva == -1) {
            System.out.println("Nao ha reserva em check-in para esse nome. Verifique se nao ha erros de digitacao");
            return;
        }
        Reserva reserva = reservas[indiceReserva];
        String retorno = reserva.realizaCheckOut();
        System.out.println("Check-out realizado com sucesso.\n" + retorno);

    }

    public static void cancelaReserva(String cliente) {
        int indiceReserva = getReservaValidaPorCliente(cliente, 'A');
        if(indiceReserva == -1) {
            System.out.println("Nao ha reserva ativa nesse nome. Verifique se nao ha erros de digitacao");
            return;
        }

        Reserva reserva = reservas[indiceReserva];
        reserva.cancelar();
        System.out.println("Reserva cancelada com sucesso.");
    }

    public static void realizaReserva(int[] datas, String cliente, Quarto quarto) {
        if(!consultaDisponibilidade(datas[0], datas[1], quarto)) {
            System.out.println("O quarto nao esta disponivel para essa data");
            return;
        }

        if(!consultaClientePodeReservar(cliente)) {
            System.out.println("Ja ha uma reserva para esse cliente");
            return;
        }

        inserirReserva(datas[0], datas[1], cliente, quarto);
        System.out.println("Reserva feita com sucesso");
    }

    public static void inserirReserva(int diaInicio, int diaFim, String nome, Quarto quarto) {
        ultimaReserva++;
        if(ultimaReserva == reservas.length) {
            reservas = Arrays.copyOf(reservas, reservas.length * 2);
        }
        reservas[ultimaReserva] = new Reserva(diaInicio, diaFim,nome, quarto);
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

    public static void salvaDados() {//TODO: tentar usar o negocio de writeobject e readobject
        String conteudoQuarto = "";
        boolean primeiro = true;
        for(Quarto quarto: quartos) {
            if(!primeiro) {
                conteudoQuarto += "\n";
            }
            conteudoQuarto += quarto.getFormaArquivo();
            primeiro = false;
        }

        serializar(caminhoProjeto + "/quartoteste.txt", conteudoQuarto);
        
        String conteudoReserva = "";
        primeiro = true;
        for(int i = 0; i <= ultimaReserva; i++) {
            if(!primeiro) {
                conteudoReserva += "\n";
            }
            conteudoReserva += reservas[i].getFormaArquivo();
            primeiro = false;
        }

        serializar(caminhoProjeto + "/reserva.txt", conteudoReserva);
    }

    private static void serializar(String caminhoArquivo, String conteudo) {
        try {
            File arquivoAEscrever = new File(caminhoArquivo);
            FileWriter escritorArquivo = new FileWriter(arquivoAEscrever);
            escritorArquivo.write(conteudo);
            escritorArquivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
