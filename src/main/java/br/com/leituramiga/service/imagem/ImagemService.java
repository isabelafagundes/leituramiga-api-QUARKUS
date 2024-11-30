package br.com.leituramiga.service.imagem;

import br.com.leituramiga.service.autenticacao.HashService;
import br.com.leituramiga.service.autenticacao.LogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.*;
import java.util.Base64;

@ApplicationScoped
public class ImagemService {

    @Inject
    LogService logService;

    final String NOME_ARQUIVO_PAI = "leituramiga";
    final String NOME_ARQUIVO_LIVROS = "livros";
    final String NOME_ARQUIVO_USUARIO = "usuarios";


    public String salvarImagemLivro(String arquivoBase64, String email, Integer numeroLivro) throws IOException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando a salvar a imagem do livro");
            if(!arquivoBase64.contains(",")) return null;
            String arquivoBase64SemCabecalho = arquivoBase64.split(",")[1];
            byte[] arquivo = Base64.getDecoder().decode(arquivoBase64SemCabecalho);

            String diretorio = System.getProperty("user.home") + "/" + NOME_ARQUIVO_PAI + "/" + NOME_ARQUIVO_LIVROS;

            File pastaDestino = new File(diretorio);

            if (!pastaDestino.exists()) {
                boolean pastaCriada = pastaDestino.mkdirs();
                if (!pastaCriada) {
                    throw new IOException("Falha ao criar diretório: " + diretorio);
                }
            }

            String emailUsuario = HashService.obterMd5(email);
            String extensao = identificarExtensao(arquivoBase64);
            String nomeArquivo = emailUsuario + "_" + numeroLivro + "." + extensao;
            File arquivoImagemLivro = new File(diretorio, nomeArquivo);

            logService.iniciar(ImagemService.class.getName(), "Salvando a imagem do livro no diretório: " + arquivoImagemLivro.getAbsolutePath());

            try (OutputStream outputStream = new FileOutputStream(arquivoImagemLivro)) {
                outputStream.write(arquivo);
            }

            return arquivoImagemLivro.getAbsolutePath();

        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao salvar a imagem do livro", e);
            throw e;
        }
    }

    public String identificarExtensao(String base64String) {
        if (base64String.startsWith("data:image/jpeg")) {
            return "jpeg";
        } else if (base64String.startsWith("data:image/png")) {
            return "png";
        } else if (base64String.startsWith("data:image/gif")) {
            return "gif";
        } else {
            return "desconhecido";
        }
    }


    public String obterImagemLivro(String path) throws IOException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando a obter a imagem do livro");

            if (path == null || path.isEmpty()) return null;

            File arquivoImagem = new File(path);

            if (!arquivoImagem.exists()) {
                return null;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (FileInputStream fileInputStream = new FileInputStream(arquivoImagem)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
            }

            byte[] arquivo = baos.toByteArray();
            String imagemBase64 = Base64.getEncoder().encodeToString(arquivo);
            return imagemBase64;
        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao obter a imagem do livro", e);
            throw e;
        }
    }

    public String salvarImagemUsuario(String arquivoBase64, String email) throws IOException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando a salvar a imagem do usuário");
            if(!arquivoBase64.contains(",")) return null;
            String arquivoBase64SemCabecalho = arquivoBase64.split(",")[1];
            byte[] arquivo = Base64.getDecoder().decode(arquivoBase64SemCabecalho);

            String diretorio = System.getProperty("user.home") + "/" + NOME_ARQUIVO_PAI + "/" + NOME_ARQUIVO_USUARIO;

            File pastaDestino = new File(diretorio);

            if (!pastaDestino.exists()) {
                boolean pastaCriada = pastaDestino.mkdirs();
                if (!pastaCriada) {
                    throw new IOException("Falha ao criar diretório: " + diretorio);
                }
            }

            String emailUsuario = HashService.obterMd5(email);
            String extensao = identificarExtensao(arquivoBase64);
            String nomeArquivo = emailUsuario + "." + extensao;
            File arquivoImagemUsuario = new File(diretorio, nomeArquivo);

            logService.iniciar(ImagemService.class.getName(), "Salvando a imagem do usuário no diretório: " + arquivoImagemUsuario.getAbsolutePath());

            try (OutputStream outputStream = new FileOutputStream(arquivoImagemUsuario)) {
                outputStream.write(arquivo);
            }

            return arquivoImagemUsuario.getAbsolutePath();

        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao salvar a imagem do usuário", e);
            throw e;
        }
    }

    public String obterImagemUsuario(String path) throws IOException {
        try {
            logService.iniciar(ImagemService.class.getName(), "Iniciando a obter a imagem do usuário");

            if (path == null || path.isEmpty()) return null;

            File arquivoImagem = new File(path);

            if (!arquivoImagem.exists()) {
                return null;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (FileInputStream fileInputStream = new FileInputStream(arquivoImagem)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
            }

            byte[] arquivo = baos.toByteArray();
            String imagemBase64 = Base64.getEncoder().encodeToString(arquivo);
            return imagemBase64;
        } catch (Exception e) {
            logService.erro(ImagemService.class.getName(), "Ocorreu um erro ao obter a imagem do usuário", e);
            throw e;
        }
    }



}
