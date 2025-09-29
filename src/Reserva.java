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

    public char getStatus() {
        return this.status;
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

    public String realizaCheckIn() {//Realiza o checkin e retorna os dados a serem imprimidos.
        if(status != 'A') {
            return "Erro no checkin. A reserva nao esta ativa. Ela esta " + status;
        } 
        status = 'I';

        String resultado = "";
        int quantidadeDias = diaFim - diaInicio;
        resultado += "Datas: " + diaInicio + " - " + diaFim + "\n" +
                    "Quantidade de dias: " + quantidadeDias + "\n" +
                    "Valor das diarias : " + quarto.calcularPrecoDiarias(quantidadeDias) + "\n" +
                    "Dados do quarto: " + quarto.toString();
        return resultado;
    }

    public boolean incluiDia(int data) {
        if(data >= diaInicio && data <= diaFim) {
            return true;
        } else {
            return false;
        }
    }

    public int getNumeroQuarto() {
        return quarto.getNumero();
    }

    public Quarto getQuarto() {
        return quarto;
    }

    @Override
    public String toString() {
        return "Inicio: " + diaInicio + " Fim: " + 
                diaFim + " Cliente: " + cliente + 
                " Quarto: " + quarto.getNumero() + 
                " Status: " + status;
    }


	public String realizaCheckOut() {
        status = 'O';
        String resultado = "";

        int quantidadeDias = diaFim - diaInicio;
        resultado += "Datas: " + diaInicio + " - " + diaFim + "\n" +
                    "Quantidade de dias: " + quantidadeDias + "\n" +
                    "Valor das diarias : " + quarto.calcularPrecoDiarias(quantidadeDias) + "\n" +
                    "Dados do quarto: " + quarto.toString();
        //TODO: inserir valor total com diaria e consumo e ver como e o retorno do quarto
        return resultado;
	}
}
