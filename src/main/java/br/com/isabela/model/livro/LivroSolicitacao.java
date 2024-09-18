package br.com.isabela.model.livro;

public class LivroSolicitacao {
    private Integer codigoLivro;
    private String titulo;
    private String autor;
    private String emailUsuario;

    public LivroSolicitacao() {
    }

    public static LivroSolicitacao carregar(
            Integer codigoLivro,
            String titulo,
            String autor,
            String emailUsuario
    ) {
        LivroSolicitacao livro = new LivroSolicitacao();
        livro.setAutor(autor);
        livro.setCodigoLivro(codigoLivro);
        livro.setTitulo(titulo);
        livro.setEmailUsuario(emailUsuario);
        return livro;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Integer getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(Integer codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getInsert() {
        return "(" + codigoLivro + ", '" + titulo + "', '" + autor + "')";
    }

}
