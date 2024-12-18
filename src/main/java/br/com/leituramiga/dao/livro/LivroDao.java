package br.com.leituramiga.dao.livro;

import br.com.leituramiga.dao.FabricaDeConexoes;
import br.com.leituramiga.dao.endereco.EnderecoDao;
import br.com.leituramiga.dto.livro.LivroDto;
import br.com.leituramiga.dto.livro.LivroSolicitacaoDto;
import br.com.leituramiga.model.livro.Livro;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class LivroDao {
    @Inject
    FabricaDeConexoes bd;

    @Inject
    LogService logService;

    public Livro obterLivroPorNumero(Integer livroId) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_ID);
            pstmt.setInt(1, livroId);
            ResultSet resultado = pstmt.executeQuery();
            Livro livro = new Livro();
            if (resultado.next()) livro = obterLivroDeResult(resultado);
            logService.sucesso(LivroDao.class.getName(), "Busca do livro finalizada");
            return livro;
        }
    }

    public boolean verificarExistenciaLivro(Integer livroId) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação da existência do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.LIVRO_EXISTE);
            pstmt.setInt(1, livroId);
            ResultSet resultSet = pstmt.executeQuery();
            boolean livroExiste = false;
            if (resultSet.next()) livroExiste = resultSet.getBoolean(1);
            return livroExiste;
        }

    }

    public List<Livro> obterLivroPorUsuario(String emailUsuario) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca dos livros do usuário");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.OBTER_LIVRO_POR_USUARIO);
            pstmt.setString(1, emailUsuario);
            ResultSet resultado = pstmt.executeQuery();
            List<Livro> livros = new ArrayList<>();
            while (resultado.next()) livros.add(obterLivroDeResult(resultado));
            logService.sucesso(LivroDao.class.getName(), "Busca dos livros do usuário finalizada");
            return livros;
        }
    }

    public Integer salvarLivro(LivroDto livro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando o salvamento do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.SALVAR_LIVRO, PreparedStatement.RETURN_GENERATED_KEYS);
            definirParametrosDeSalvamento(pstmt, livro);
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet result = pstmt.getGeneratedKeys()) {
                    if (result.next()) {
                        Integer idGerado = result.getInt(1);
                        return idGerado;
                    }
                }
            }
            logService.sucesso(LivroDao.class.getName(), "Salvamento do livro concluído");
            return null;
        }
    }

    public void atualizarLivro(LivroDto livro, String email) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a atualização do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.ATUALIZAR_LIVRO);
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getDescricao());
            pstmt.setString(3, livro.getEstadoFisico());
            pstmt.setInt(4, livro.getCodigoCategoria());
            pstmt.setString(5, livro.getAutor());
            pstmt.setString(6, livro.tipoSolicitacao);
            pstmt.setInt(7, livro.getCodigoLivro());
            pstmt.setString(8, email);
            pstmt.executeUpdate();
        }
        logService.sucesso(LivroDao.class.getName(), "Atualização do livro finalizada");
    }


    public void deletarLivro(int numeroLivro, String email) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando exclusão do livro " + numeroLivro);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.DELETAR_LIVRO);
            pstmt.setInt(1, numeroLivro);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            logService.sucesso(EnderecoDao.class.getName(), "Exclusão de livro finalizada " + numeroLivro);
        }
    }

    public void atualzarCodigoUltimaSolicitacao(List<LivroSolicitacaoDto> livros, Integer codigoSolicitacao, Connection conexao) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a atualização do código da última solicitação");
        String query = LivroQueries.ATUALIZAR_CODIGO_ULTIMA_SOLICITACAO.replaceAll("CODIGOS_LIVROS", obterIdLivrosConcatenados(livros));
        PreparedStatement pstmt = conexao.prepareStatement(query);
        pstmt.setInt(1, codigoSolicitacao);
        pstmt.executeUpdate();
        logService.sucesso(LivroDao.class.getName(), "Atualização do código da última solicitação finalizada");
    }


    private String obterIdLivrosConcatenados(List<LivroSolicitacaoDto> livroSolicitacaoDtos) {
        StringBuilder ids = new StringBuilder();
        for (LivroSolicitacaoDto livroSolicitacaoDto : livroSolicitacaoDtos) {
            ids.append(livroSolicitacaoDto.codigoLivro);
            ids.append(",");
        }
        return ids.substring(0, ids.length() - 1);
    }

    public boolean validarExistenciaLivro(int numeroLivro) throws SQLException {
        logService.iniciar(EnderecoDao.class.getName(), "Iniciando validação da existência do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_EXISTENCIA);
            pstmt.setInt(1, numeroLivro);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int qtd = resultado.getInt(1);
            logService.sucesso(EnderecoDao.class.getName(), "Sucesso na validação de existência do livro");
            return qtd > 0;
        }

    }

    public List<Livro> obterLivrosPaginados(Integer pagina, Integer tamanhoPagina, String pesquisa, Integer numeroCategoria, Integer numeroInstituicao, Integer numeroCidade, Integer tipoSolicitacao, String emailUsuario) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando busca de livros paginados");

        try (Connection conexao = bd.obterConexao()) {
            String query = LivroQueries.OBTER_LIVROS_PAGINADOS;

            String pesquisaQuery = pesquisa == null ? "" : pesquisa;
            query = query.replace("PESQUISA", "'%" + pesquisaQuery + "%'");

            String tipoSolicitacaoQuery = tipoSolicitacao == null ? "" : tipoSolicitacao.toString();
            query = query.replace("TIPO_SOLICITACAO", "'%" + tipoSolicitacaoQuery + "%'");

            System.out.println(query);

            PreparedStatement pstmt = conexao.prepareStatement(query);

            pstmt.setObject(1, numeroInstituicao);
            pstmt.setObject(2, numeroInstituicao);

            pstmt.setObject(3, numeroCidade);
            pstmt.setObject(4, numeroCidade);

            pstmt.setObject(5, numeroCategoria);
            pstmt.setObject(6, numeroCategoria);

            pstmt.setString(7, String.valueOf(tipoSolicitacao));

            pstmt.setString(8, emailUsuario);
            pstmt.setString(9, emailUsuario);

            pstmt.setInt(10, tamanhoPagina);
            pstmt.setInt(11, pagina * tamanhoPagina);


            ResultSet resultado = pstmt.executeQuery();
            List<Livro> livros = new ArrayList<>();
            while (resultado.next()) {
                livros.add(obterLivroDeResult(resultado));
            }

            logService.sucesso(LivroDao.class.getName(), "Sucesso em obter os livros paginados");
            return livros;
        }
    }


    public boolean verificarStatusDisponivel(Integer numeroLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação do status de disponibilidade do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_STATUS_DISPONIVEL);
            pstmt.setInt(1, numeroLivro);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(LivroDao.class.getName(), "Sucesso na validação do status de disponibilidade livro");
            return quantidade > 0;
        }
    }

    public boolean verificarStatusEmprestado(Integer numeroLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação do status de empréstimo do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_STATUS_EMPRESTADO);
            pstmt.setInt(1, numeroLivro);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(LivroDao.class.getName(), "Sucesso na validação do status de empréstimo do livro");
            return quantidade > 0;
        }
    }

    public boolean verificarStatusDesativado(Integer numeroLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação do status de desativado do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_STATUS_DESATIVADO);
            pstmt.setInt(1, numeroLivro);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(LivroDao.class.getName(), "Sucesso na validação do status de desativado do livro");
            return quantidade > 0;
        }
    }

    public boolean verificarStatusIndisponivel(Integer numeroLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação do status de indisponibilidade do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.VALIDAR_STATUS_INDISPONIVEL);
            pstmt.setInt(1, numeroLivro);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(LivroDao.class.getName(), "Sucesso na validação do status de indisponibilidade do livro");
            return quantidade > 0;
        }
    }

    public void atualizarLivrosIndisponiveis(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, Connection conexao) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a atualização dos livros indisponíveis");
        String query = LivroQueries.ATUALIZAR_LIVROS_INDISPONIVEIS.replaceAll("CODIGOS_LIVROS", obterIdsConcatenados(livroSolicitacaoDtos));
        PreparedStatement pstmt = conexao.prepareStatement(query);
        pstmt.setInt(1, numeroSolicitacao);
        pstmt.executeUpdate();
        logService.sucesso(LivroDao.class.getName(), "Atualização dos livros indisponíveis finalizada");
    }

    public void atualizarLivrosEmprestados(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, Connection conexao) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a atualização dos livros emprestados");
        String query = LivroQueries.ATUALIZAR_LIVROS_EMPRESTADOS.replaceAll("CODIGOS_LIVROS", obterIdsConcatenados(livroSolicitacaoDtos));
        PreparedStatement pstmt = conexao.prepareStatement(query);
        pstmt.setInt(1, numeroSolicitacao);
        pstmt.executeUpdate();
        logService.sucesso(LivroDao.class.getName(), "Atualização dos livros emprestados finalizada");
    }

    public void atualizarLivrosDisponiveis(Integer numeroSolicitacao, List<LivroSolicitacaoDto> livroSolicitacaoDtos, Connection conexao) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a atualização dos livros disponíveis");
        String query = LivroQueries.ATUALIZAR_LIVROS_DISPONIVEIS.replaceAll("CODIGOS_LIVROS", obterIdsConcatenados(livroSolicitacaoDtos));
        PreparedStatement pstmt = conexao.prepareStatement(query);
        pstmt.executeUpdate();
        logService.sucesso(LivroDao.class.getName(), "Atualização dos livros disponíveis finalizada");
    }

    private String obterIdsConcatenados(List<LivroSolicitacaoDto> livroSolicitacaoDtos) {
        StringBuilder ids = new StringBuilder();
        for (LivroSolicitacaoDto livroSolicitacaoDto : livroSolicitacaoDtos) {
            ids.append(livroSolicitacaoDto.codigoLivro);
            ids.append(",");
        }
        return ids.substring(0, ids.length() - 1);
    }

    public void definirParametrosDeSalvamento(PreparedStatement pstmt, LivroDto livro) throws SQLException {
        pstmt.setString(1, livro.getTitulo());
        pstmt.setString(2, livro.getDescricao());
        pstmt.setString(3, livro.getEstadoFisico());
        pstmt.setString(4, livro.getEmailUsuario());
        pstmt.setInt(5, livro.getCodigoCategoria());
        pstmt.setString(6, livro.getAutor());
        pstmt.setString(7, livro.tipoSolicitacao);
    }

    public void salvarImagemLivro(String caminhoImagem, Integer codigoLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando o salvamento da imagem do livro de código" + codigoLivro);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.SALVAR_IMAGEM);
            pstmt.setString(1, caminhoImagem);
            pstmt.setInt(2, codigoLivro);
            pstmt.executeUpdate();
            logService.sucesso(LivroDao.class.getName(), "Sucesso em salvar a imagem do livro de código " + codigoLivro);
        }

    }

    public Boolean validarExistenciaImagem(Integer codigoLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a validação da existência da imagem do livro");
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.IMAGEM_EXISTE);
            pstmt.setInt(1, codigoLivro);
            ResultSet resultado = pstmt.executeQuery();
            resultado.next();
            int quantidade = resultado.getInt(1);
            logService.sucesso(LivroDao.class.getName(), "Sucesso na validação da existência da imagem do livro");
            return quantidade > 0;
        }
    }

    public void atualizarImagemLivro(String caminhoImagem, Integer codigoLivro) throws SQLException {
        logService.iniciar(LivroDao.class.getName(), "Iniciando a atualização da imagem do livro de código" + codigoLivro);
        try (Connection conexao = bd.obterConexao()) {
            PreparedStatement pstmt = conexao.prepareStatement(LivroQueries.ATUALIZAR_IMAGEM);
            pstmt.setString(1, caminhoImagem);
            pstmt.setInt(2, codigoLivro);
            pstmt.executeUpdate();
            logService.sucesso(LivroDao.class.getName(), "Sucesso em atualizar a imagem do livro de código " + codigoLivro);
        }
    }

    public Livro obterLivroDeResult(ResultSet resultado) throws SQLException {
        return Livro.carregar(
                resultado.getInt("codigo_livro"),
                resultado.getString("nome_usuario"),
                resultado.getString("nome"),
                resultado.getString("autor"),
                resultado.getString("descricao"),
                resultado.getString("nome_categoria"),
                resultado.getString("estado_fisico"),
                resultado.getString("nome_instituicao"),
                resultado.getString("nome_cidade"),
                resultado.getString("ultima_solicitacao"),
                resultado.getString("email_usuario"),
                resultado.getInt("codigo_ultima_solicitacao"),
                resultado.getInt("codigo_categoria"),
                resultado.getInt("codigo_status_livro"),
                resultado.getInt("codigo_cidade"),
                resultado.getString("tipo_solicitacao"),
                null,
                resultado.getString("imagem")
        );
    }

}