import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Pousada {
    private static String nome = "Pousadona dos Guri";
    private static String contato = "(51) 96420-6911";
    private static Produto[] produtos;
    private static Quarto[] quartos;
    private static Reserva[] reservas;
    private static Reserva[] testeReservas;

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
        //Carregar o caminho do projeto para poder acessar os arquivos
        caminhoProjeto = System.getProperty("java.class.path");
        //TODO: criar variaveis para acessar cada arquivo
        
        carregaDados();//Carregar dados dos arquivos

        System.out.println("Seja bem vindo a " + nome + ". Caso precise entrar em contato, ligue para " + contato);

        try {
            Scanner inputUsuario = new Scanner(System.in);

            while (true) { 
                System.out.println(TEXTOMENU);//Imprimir texto principal sempre que o usuario puder escolher

                int resposta = inputUsuario.nextInt();
                if (resposta == 0) {// Sair se escolher 0
                    System.out.println("Opcao escolhida: sair.");
                    System.out.println("Salvando seus dados...");

                    salvaDados();

                    System.out.println("Tchau!");
                    break;//Sair do loop para encerrar o programa
                } else if (resposta == 1) {//Consultar disponibilidade
                    System.out.println("Opcao escolhida: consultar disponibilidade");

                    System.out.println("Insira a data desejada:");
                    int data = inputUsuario.nextInt();

                    System.out.println("Insira o número do quarto desejado:");
                    int quartoNumero = inputUsuario.nextInt();
                    Quarto quarto = quartos[quartoNumero];

                    boolean disponivel = consultaDisponibilidade(data, quarto);

                    if(disponivel) {
                        System.out.println("O quarto esta disponivel para essa data. Informacoes do quarto:");
                        System.out.println(quarto.getInformacoes());
                    } else {
                        System.out.println("O quarto nao esta disponivel para essa data");
                    }

                } else if(resposta == 2) {//Consultar reserva
                    System.out.println("Opcao escolhida: consultar reserva");

                    System.out.println("Insira a data (-1 se nao quiser incluir data na pesquisa)");
                    int data = inputUsuario.nextInt();

                    System.out.println("Insira o cliente (Nao se nao quiser incluir cliente na pesquisa)");
                    String nomeCliente = inputUsuario.next().toLowerCase();

                    System.out.println("Insira o numero do quarto (-1 se nao quiser incluir quarto na pesquisa)");
                    int numeroQuarto = inputUsuario.nextInt();

                    consultaReserva(data, nomeCliente, numeroQuarto);
                    
                }else if(resposta == 3) {//Realizar reserva
                    System.out.println("Opcao escolhida: realizar reserva");

                    int[] datas = new int[2];
                    System.out.println("Insira a data inicial da reserva");
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

                    System.out.println();//Imprimir uma linha pra ficar chique
                    System.out.println("Insira o numero do quarto desejado"); 
                    int numeroQuarto = inputUsuario.nextInt(); 

                    System.out.println("Insira o nome para a reserva. Nao use o nome 'Nao'");
                    String nomeCliente = inputUsuario.next().toLowerCase(); 

                    Quarto quartoReserva = quartos[numeroQuarto];
                    realizaReserva(datas, nomeCliente, quartoReserva);
                }
                else if(resposta == 4) {//Cancelar reserva
                    System.out.println("Opcao escolhida: cancelar reserva");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();

                    cancelaReserva(nomeCliente);
                }
                else if(resposta == 5) {//Realizar check-in
                    System.out.println("Opcao escolhida: realizar check-in");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();

                    realizaCheckIn(nomeCliente);
                } else if(resposta == 6) {//Realizar check-out
                    System.out.println("Opcao escolhida: realizar check-out");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();

                    realizaCheckOut(nomeCliente);
                } else if (resposta == 7) {//Registrar consumo
                    System.out.println("Opcao escolhida: registrar consumo");
                    System.out.println("Insira o nome informado na reserva");
                    String nomeCliente = inputUsuario.next().toLowerCase();

                    int reservaNumero = getReservaValidaPorCliente(nomeCliente, 'I');//Pegar reserva com check-in
                    if(reservaNumero == -1) {//Retornar se nao tiver reserva com check-in
                        System.out.println("Nao ha reserva em check-in para o nome informado. Verifique se ha erros de digitacao");
                        continue;
                    }
                    Quarto quarto = reservas[reservaNumero].getQuarto();//Pegar o quarto da reserva

                    System.out.println("Escolha um dos produtos:");
                    for(int i = 0; i < produtos.length; i++)
                        System.out.println(i + ": " + produtos[i].getInformacoes());
                    int produtoEscolhido = inputUsuario.nextInt();

                    quarto.adicionaConsumo(produtoEscolhido);

                } else if (resposta == 8) {//Salvar dados
                    System.out.println("Opcao escolhida: salvar dados");
                    salvaDados();
                } else {
                    System.out.println("Opcao invalida. Escolha uma das opcoes apresentadas abaixo. Digite apenas o numero");
                }
            }
            inputUsuario.close();//Fechar scanner
        } catch (Exception e) {
            e.printStackTrace();//Printar stack em caso de erro
        }
    }

    //Consultar se o quarto esta disponivel em uma data especifica
    public static boolean consultaDisponibilidade(int data, Quarto quarto) {
        for (int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            System.out.println(reserva);
            if(reserva.getStatus() == 'O' || reserva.getStatus() == 'C') {//Ignora reservas canceladas e em check-out
                continue;
            }
            if(reserva.getNumeroQuarto() != quarto.getNumero()) {//Ignora outros quartos
                continue;
            }
            
            if(reserva.incluiDia(data))
                return false;
        }
        return true;//Retorna true se nao retornar false nenhuma vez
    }

    //Consultar se o quarto esta disponivel em um periodo
    public static boolean consultaDisponibilidade(int dataInicio, int dataFim, Quarto quarto) {
        for( int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getStatus() == 'O' || reserva.getStatus() == 'C') {//Ignora reservas canceladas e em check-out
                continue;
            }
            if(reserva.getNumeroQuarto() != quarto.getNumero()) {//Ignora outros quartos
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

    //Encontrar reservas que tenham os dados inseridos
    public static void consultaReserva(int data, String cliente, int quartoNumero) {
        String textoReservas = "";//Variavel para armazenar o resultado final

        for(int i = 0; i <= ultimaReserva; i++) {
            Reserva reserva = reservas[i];
            if(reserva.getNumeroQuarto() != quartoNumero && quartoNumero != -1) {//Se o quarto for diferente ou -1, ignora
                continue;
            } 
            else if(!reserva.getCliente().equals(cliente) && !cliente.equals("Nao")) {//Se o cliente for diferente ou Nao, ignora
                continue;
            }
            else if(!reserva.incluiDia(data) && data != -1) {//Se a data nao estiver inclusa ou for -1, ignora
                continue;
            }
            textoReservas += reserva.getInformacoes() + "\n";
        }

        if(textoReservas.equals("")) {//Caso nao encontre nenhuma reserva
            System.out.println("nenhuma reserva encontrada para os dados fornecidos");
        } else {//Caso encontre pelo menos uma
            System.out.print("Reservas encontradas:\n" + textoReservas);
        }
    }

    //Consulta se o cliente ja possui reserva ativa ou em check-in
    public static boolean consultaClientePodeReservar(String cliente) {
        int reservaAtiva = getReservaValidaPorCliente(cliente, 'A');//-1 se nao possui reserva ativa
        int reservaIn = getReservaValidaPorCliente(cliente, 'I');//-1 se nao possui reserva em check-in

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

            if(reserva.getCliente().equals(cliente)) {//Pega apenas clientes com o mesmo nome do input
                char status = reserva.getStatus();

                if(status == statusDesejado) {//Retorna apenas se o status da reserva for igual
                    return i;
                }
            }
        }
        return -1;
    }

    //Realizar check-in
    public static void realizaCheckIn(String cliente) {
        int indiceReserva = getReservaValidaPorCliente(cliente, 'A');//Pega o indice da reserva ativa, se existir
        if(indiceReserva == -1) {//Retornar em caso de erro
            System.out.println("Nao ha reserva ativa nesse nome. Verifique se nao ha erros de digitacao");
            return;
        }

        Reserva reserva = reservas[indiceReserva];
        String retorno = reserva.realizaCheckIn();
        System.out.println("Check-in realizado com sucesso.\n" + retorno);
    }

    //Realizar check-out
    public static void realizaCheckOut(String cliente) {
        int indiceReserva = getReservaValidaPorCliente(cliente, 'I');//Pega o indice da reserva em check-in, se existir
        if(indiceReserva == -1) {//Retornar em caso de erro
            System.out.println("Nao ha reserva em check-in para esse nome. Verifique se nao ha erros de digitacao");
            return;
        }

        Reserva reserva = reservas[indiceReserva];
        String retorno = reserva.realizaCheckOut();
        System.out.println("Check-out realizado com sucesso.\n" + retorno);

    }

    //Cancelar reserva
    public static void cancelaReserva(String cliente) {
        int indiceReserva = getReservaValidaPorCliente(cliente, 'A');//Pega o indice da reserva ativa, se existir
        if(indiceReserva == -1) {//Retornar em caso de erro
            System.out.println("Nao ha reserva ativa nesse nome. Verifique se nao ha erros de digitacao");
            return;
        }

        Reserva reserva = reservas[indiceReserva];

        reserva.cancelar();
    }

    //Criar uma reserva nova
    public static void realizaReserva(int[] datas, String cliente, Quarto quarto) {
        if(!consultaDisponibilidade(datas[0], datas[1], quarto)) {//Verificar se o quarto esta disponivel
            System.out.println("O quarto nao esta disponivel para essa data");
            return;
        }

        if(!consultaClientePodeReservar(cliente)) {//Verificar se o cliente ja tem reserva
            System.out.println("Ja ha uma reserva para esse cliente");
            return;
        }

        inserirReserva(datas[0], datas[1], cliente, quarto);//Inserir a reserva na lista
        System.out.println("Reserva feita com sucesso");
    }

    //Inserir reserva na lista de reservas
    public static void inserirReserva(int diaInicio, int diaFim, String nome, Quarto quarto) {
        ultimaReserva++;
        if(ultimaReserva == reservas.length) {//Duplicar array se nao tiver mais espaco
            reservas = Arrays.copyOf(reservas, reservas.length * 2);
        }

        reservas[ultimaReserva] = new Reserva(diaInicio, diaFim,nome, quarto);
    }

    public static void carregaDados() throws ClassNotFoundException {
        try {
            FileInputStream stream = new FileInputStream(caminhoProjeto + "/testeReserva.txt");
            ObjectInputStream input = new ObjectInputStream(stream);
            reservas = new Reserva[50];

            Object reserva;
            int i = 0;
            while((reserva = input.readObject()) != null) {
                reservas[i++] = (Reserva) reserva;
                ultimaReserva = i;
            }
            input.close();
        } catch (EOFException e) {
            System.out.println("ultimaReserva: " + ultimaReserva);
            System.out.println("Reservas carregadas com sucesso");
        } catch (IOException e) {
            System.out.println("Erro no carregamento das reservas:");
            e.printStackTrace();
        }

        try {
            FileInputStream stream = new FileInputStream(caminhoProjeto + "/testeQuarto.txt");
            ObjectInputStream input = new ObjectInputStream(stream);
            quartos = new Quarto[30];

            Object quarto;
            int i = 0;
            while((quarto = input.readObject()) != null) {
                quartos[i++] = (Quarto) quarto;
            }

            input.close();
        } catch (EOFException e) {
            System.out.println("Quartos carregados com sucesso");
        } catch (IOException e) {
            System.out.println("Erro no carregamento dos quartos:");
            e.printStackTrace();
        }

        try {
            FileInputStream stream = new FileInputStream(caminhoProjeto + "/testeProduto.txt");
            ObjectInputStream input = new ObjectInputStream(stream);
            produtos = new Produto[6];

            Object produto;
            int i = 0;
            while((produto = input.readObject()) != null) {
                produtos[i++] = (Produto) produto;
            }

            input.close();
        } catch (EOFException e) {
            System.out.println("Produtos carregados com sucesso");
        } catch (IOException e) {
            System.out.println("Erro no carregamento dos produtos:");
            e.printStackTrace();
        }

/*
        //carregar produtos /*
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
        */
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
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(caminhoProjeto + "/testeReserva.txt"));
            for(int i = 0; i <= ultimaReserva; i++) {
                outStream.writeObject(reservas[i]);
            }
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(caminhoProjeto + "/testeQuarto.txt"));
            for(Quarto quarto: quartos) {
                outStream.writeObject(quarto);
            }
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(caminhoProjeto + "/testeProduto.txt"));
            for(Produto produto: produtos) {
                outStream.writeObject(produto);
            }
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



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
