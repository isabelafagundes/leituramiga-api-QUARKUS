package br.com.leituramiga.service.livro;

import br.com.leituramiga.dao.livro.LivroDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ImagemLivroService {


    @Inject
    LivroDao dao;


}
