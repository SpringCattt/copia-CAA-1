package CONTROLLERS;

import java.util.List;
import MODELS.CLASS.Trabalhador;
import MODELS.CLASS.Credenciais;
import MODELS.DAO.TrabalhadorDAO;
import MODELS.DAO.CredenciaisDAO;
public class HomeController {

    private TrabalhadorDAO trabalhadorDAO;
    private CredenciaisDAO credenciaisDAO;
    
    public List<Trabalhador> obterTodosFuncionarios() {
        return trabalhadorDAO.getAllTrabalhadores();
    }

    public HomeController() {
        this.trabalhadorDAO = new TrabalhadorDAO();
        this.credenciaisDAO = new CredenciaisDAO();
        
    }

    public boolean criarFuncionario(
            String nome,
            String email,
            String password,
            int categoria,
            boolean atividade
    ) {
        // Criar trabalhador
        Trabalhador t = new Trabalhador();
        t.setNome(nome);
        t.setEmailPessoal(email);
        t.setCategoria(categoria);
        t.setAtividade(atividade);

        long idGerado = trabalhadorDAO.insertTrabalhador(t);

        if (idGerado <= 0) {
            System.err.println("Erro ao inserir trabalhador");
            return false;
        }

        // Criar Credenciais
        Credenciais c = new Credenciais();
        c.setIdTrabalhador((int) idGerado);
        c.setEmail(email);
        c.setPassword(password);

        boolean credOk = credenciaisDAO.insertCredenciais(c);

        if (!credOk) {
            System.err.println("Erro ao inserir credenciais");
            return false;
        }

        System.out.println("Funcionário criado com sucesso (ID " + idGerado + ")");
        return true;
    }
}
