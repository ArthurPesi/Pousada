public class Reserva {
    private int diaInicio;
    private int diaFim;
    private String cliente; //Nome do cliente
    private Quarto quarto;
    private char status; //A: ativa, C: cancelada, I: check-in, O: check-out

    Reserva(int diaInicio, int diaFim, String cliente, Quarto quarto) { 
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
    
    //Getters e setters
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

    public int getNumeroQuarto() {
        return quarto.getNumero();
    }

    public Quarto getQuarto() {
        return quarto;
    }

	public String getFormaArquivo() {
        return diaInicio + ";" + diaFim + ";" + cliente + ";" +  quarto.getNumero() + ";" +  status;
	}

    //Fim dos getters e setters

    //Mudar estado para cancelado
    public void cancelar() {
        if(status != 'A') {//Se nao estiver ativa, printa erro
            System.out.println("Erro no checkin. A reserva nao esta ativa. Ela esta " + status);
            return;
        }
        status = 'C';
        System.out.println("Reserva cancelada com sucesso");
    }

    //Mudar estado para checkin
    public String realizaCheckIn() {//Realiza o checkin e retorna os dados a serem imprimidos.
        if(status != 'A') {//Erro se nao estiver ativa
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

    //Verificar se um dia esta dentro da reserva
    public boolean incluiDia(int data) {
        if(data >= diaInicio && data <= diaFim) {
            return true;
        } else {
            return false;
        }
    }


    @Override//TODO: usar toString
    public String toString() {
        return "Inicio: " + diaInicio + " Fim: " + 
                diaFim + " Cliente: " + cliente + 
                " Quarto: " + quarto.getNumero() + 
                " Status: " + status;
    }

    //Mudar estado para check-out
	public String realizaCheckOut() {
        if(status != 'I')//Retornar em caso de erro
            return "Nao e possivel fazer check-out, pois a reserva esta " + status;

        status = 'O';
        String resultado = "";

        int quantidadeDias = diaFim - diaInicio;
        float totalConsumo = quarto.valorTotalConsumo();
        float totalDiarias = quarto.calcularPrecoDiarias(quantidadeDias);
        float valorTotal = totalConsumo + totalDiarias;
        resultado += "Datas: " + diaInicio + " - " + diaFim + "\n" +
                    "Quantidade de dias: " + quantidadeDias + "\n" +
                    "Valor das diarias : " + totalDiarias + "\n" +
                    "Dados do quarto:\n" + quarto.toString() + "\n" +
                    "Valor do consumo: " + totalConsumo + "\n" +
                    "Valor total: " + valorTotal;
        return resultado;
	}


}
