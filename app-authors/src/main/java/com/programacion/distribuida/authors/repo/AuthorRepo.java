package com.programacion.distribuida.authors.repo;

import com.programacion.distribuida.authors.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@ApplicationScoped
public class AuthorRepo implements PanacheRepositoryBase<Author, Integer> {

    public List<Author> findByBook(String isbn) {
        String query = "SELECT o.author FROM BookAuthor o WHERE o.id.bookIsbn=?1";
        return this.list(query, isbn);
    }

}
