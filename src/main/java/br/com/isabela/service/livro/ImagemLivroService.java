package br.com.isabela.service.livro;

import br.com.isabela.dao.livro.LivroDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ImagemLivroService {


    @Inject
    LivroDao dao;


}
