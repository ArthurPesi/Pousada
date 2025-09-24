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
