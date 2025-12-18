package MODELS.CLASS;

public class Cliente {

    /* 
     * Atributos Bilhete
     */
    private int IdCliente;
    private String Nome;
    private String Email;
    private int NumeroTelefone;

    /* 
     * Getters e Setters
     */
    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getNumeroTelefone() {
        return NumeroTelefone;
    }

    public void setNumeroTelefone(int NumeroTelefone) {
        this.NumeroTelefone = NumeroTelefone;
    }

    /* 
     * Funções do cliente
     */
}
