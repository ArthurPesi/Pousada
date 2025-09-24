public class Reserva {
    private int diaInicio;
    private int diaFim;
    private String cliente; //Nome do cliente
    private Quarto quarto;
    private char status; //A: ativa, C: cancelada, I: check-in, O: check-out

    Reserva(int diaInicio, int diaFim, String cliente, Quarto quarto) { //TODO: verificar se o quarto e valido
        this.diaInicio = diaInicio;
        this.diaFim = diaFim;
        this.cliente = cliente;
        this.quarto = quarto;
        this.status = 'A';
    }


    Reserva(int diaInicio, int diaFim, String cliente, Quarto quarto, char status) {
        this.diaInicio = diaInicio;
        this.diaFim = diaFim;
        this.cliente = cliente;
        this.quarto = quarto;
        this.status = status;
    }

    public int getDataInicio() {
        return diaInicio;
    }

    public int getDataFim() {
        return diaFim;
    }

    public String getCliente() {
        return this.cliente;
    }
    public int cancelar() {
        if(status == 'A') { //Retorna 0 em sucesso
            status = 'C';
            return 0;
        } else if (status == 'C') {//Retorna 1 se ja estiver cancelado
            return 1;
        } else if (status == 'I') {//Retorna 2 se estiver em check-in
            return 2;
        } else {//Retorna 3 se estiver em check-out
            return 3;
        }
    }

    public int realizaCheckIn() {
        if(status == 'A') { //Retorna 0 em sucesso
            status = 'I';
            return 0;
        } else if (status == 'C') {//Retorna 1 se ja estiver cancelado
            return 1;
        } else if (status == 'I') {//Retorna 2 se estiver em check-in
            return 2;
        } else {//Retorna 3 se estiver em check-out
            return 3;
        }
    }

    public String dadosCheckIn() {
        String resultado = "";
        int quantidadeDias = diaFim - diaInicio;
        resultado += "Datas: " + diaInicio + " - " + diaFim + "\n" +
                    "Quantidade de dias: " + quantidadeDias + "\n" +
                    "Valor das diarias : " + quarto.calcularPrecoDiarias(quantidadeDias) + "\n" +
                    "Dados do quarto: " + quarto.toString();
        return resultado;
    }

    public int verificarDisponibilidade(int diaInicioComparar, int diaFimComparar, String nomeComparar, Quarto quartoComparar) {
        //Retorna um codigo baseado se esta disponivel ou qual motivo nao esta disponivel
        //0 = essa reserva nao colide com a reserva comparada
        //1 = cliente ja possui reserva ativa
        //2 = quarto esta ocupado nessa data
        if(status == 'O' || status == 'C') //Se a reserva estiver inativa, nao pode influenciar o resultado
            return 0;
        if(cliente.equals(nomeComparar))
            return 1;
        if(quartoComparar.getNumero() != quarto.getNumero())//Se os quartos forem diferentes, nao precisa nem comparar as datas
            return 0;
        if(diaFim < diaInicioComparar || diaFimComparar < diaInicio) //Se nao houver colisao entre as datas
            return 0;
        else //Se houver colisao entre as datas
            return 2;
    }

    public int getNumeroQuarto() {
        return quarto.getNumero();
    }

    @Override
    public String toString() {
        return "Inicio: " + diaInicio + " Fim: " + 
                diaFim + " Cliente: " + cliente + 
                " Quarto: " + quarto.getNumero() + 
                " Status: " + status;
    }
}
