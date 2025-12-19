package CONTROLLERS;

import MODELS.CLASS.CategoriaTrabalho;
import MODELS.CLASS.Trabalhador;
import MODELS.CLASS.Credenciais;
import MODELS.DAO.CategoriaTrabalhoDAO;
import MODELS.DAO.TrabalhadorDAO;
import MODELS.DAO.CredenciaisDAO;
import java.util.List;

public class HomeController {

    private TrabalhadorDAO trabalhadorDAO;
    private CredenciaisDAO credenciaisDAO;
    private CategoriaTrabalhoDAO categoriaTrabalhoDAO;

    public HomeController() {
        this.trabalhadorDAO = new TrabalhadorDAO();
        this.credenciaisDAO = new CredenciaisDAO();
        this.categoriaTrabalhoDAO = new CategoriaTrabalhoDAO();
    }

    public boolean criarFuncionario(
            String nome,
            String emailpessoal,
            String emailempresa,
            String password,
            int categoria,
            boolean atividade
    ) {
        // Criar trabalhador
        Trabalhador t = new Trabalhador();
        t.setNome(nome);
        t.setEmailPessoal(emailpessoal);
        t.setCategoria(categoria);

        long idGerado = trabalhadorDAO.insertTrabalhador(t);

        if (idGerado <= 0) {
            System.err.println("Erro ao inserir trabalhador");
            return false;
        }

        // Criar Credenciais
        Credenciais c = new Credenciais();
        c.setIdTrabalhador((int) idGerado);
        c.setEmail(emailempresa);
        c.setPassword(password);

        boolean credOk = credenciaisDAO.insertCredenciais(c);

        if (!credOk) {
            System.err.println("Erro ao inserir credenciais");
            return false;
        }

        System.out.println("Funcionário criado com sucesso (ID " + idGerado + ")");
        return true;
    }

    public List<CategoriaTrabalho> getCategoriasTrabalho() {
        return categoriaTrabalhoDAO.getCategoriasTrabalho();
    }

    public boolean verificarDuplicidadeEmail(String email) {
        return credenciaisDAO.existeEmail(email);
    }

    public Integer efetuarLogin(String email, String password) {
        return credenciaisDAO.validarLogin(email, password);
    }

    public boolean verificarAtividadeTrabalhador(int id) {
        return trabalhadorDAO.isTrabalhadorAtivo(id);
    }

    public Trabalhador procurarTrabalhador(int id) {
        
        return trabalhadorDAO.getTrabalhadorById(id);
    }
}
