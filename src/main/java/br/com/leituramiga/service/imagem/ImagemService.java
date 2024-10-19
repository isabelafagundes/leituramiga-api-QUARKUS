package br.com.leituramiga.service.imagem;

import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@ApplicationScoped
public class ImagemService {

    @Inject
    LogService logService;

    final String NOME_ARQUIVO_PAI = "leituramiga";
    final String NOME_ARQUIVO_LIVROS = "livros";
    final String NOME_ARQUIVO_USUARIO = "usuarios";


    public void salvarImagemLivro(String arquivoBase64, String email, Integer numeroLivro) throws IOException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando a salvar a imagem do livro");
            byte[] arquivo = Base64.getDecoder().decode(arquivoBase64);

            String diretorio = System.getProperty("user.home") + "/" + NOME_ARQUIVO_PAI + "/" + NOME_ARQUIVO_LIVROS;

            File pastaDestino = new File(diretorio);

            if (!pastaDestino.exists()) {
                boolean pastaCriada = pastaDestino.mkdirs();
                if (!pastaCriada) {
                    throw new IOException("Falha ao criar diretório: " + diretorio);
                }
            }

            String emailUsuario = HashService.obterMd5(email);
            String nomeArquivo = emailUsuario + "_" + numeroLivro + ".png";
            File arquivoImagemLivro = new File(diretorio, nomeArquivo);

            logService.iniciar(ImagemService.class.getName(), "Salvando a imagem do livro no diretório: " + arquivoImagemLivro.getAbsolutePath());

            try (OutputStream outputStream = new FileOutputStream(arquivoImagemLivro)) {
                outputStream.write(arquivo);
            }

        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao salvar a imagem do livro", e);
            throw e;
        }
    }

    public String obterImagemLivro(String email, Integer numeroLivro) throws IOException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando a obter a imagem do livro");
            String diretorio = System.getProperty("user.home") + "/" + NOME_ARQUIVO_PAI + "/" + NOME_ARQUIVO_LIVROS;

            File pastaDestino = new File(diretorio);

            if (!pastaDestino.exists()) {
                boolean pastaCriada = pastaDestino.mkdirs();
                if (!pastaCriada) {
                    throw new IOException("Falha ao criar diretório: " + diretorio);
                }
            }

            String emailUsuario = HashService.obterMd5(email);
            String nomeArquivo = emailUsuario + "_" + numeroLivro + ".png";
            File arquivoImagemLivro = new File(diretorio, nomeArquivo);

            if (!arquivoImagemLivro.exists()) return null;

            logService.iniciar(ImagemService.class.getName(), "Obtendo a imagem do livro no diretório: " + arquivoImagemLivro.getAbsolutePath());

            byte[] arquivo = new byte[(int) arquivoImagemLivro.length()];

            try (FileOutputStream fileInputStream = new FileOutputStream(arquivoImagemLivro)) {
                fileInputStream.write(arquivo);
            }

            return Base64.getEncoder().encodeToString(arquivo);
        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao obter a imagem do livro", e);
            throw e;
        }
    }

}
