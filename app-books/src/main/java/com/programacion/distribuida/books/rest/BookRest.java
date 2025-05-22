package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.AuthorDto;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BooksRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {

    @Inject
    BooksRepository booksRepository;

    @GET
    @Path("/{isbn}")
    public Response findById(@PathParam("isbn") String isbn) {


        BookDto bookDto = new BookDto();

        // 1. Buscar el libro
        var obj = booksRepository.findByIdOptional(isbn);
        if (obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var book = obj.get();

        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(book.getPrice());

        // 2. Buscar el inventario

        var inventory = book.getInventory();
        if (inventory != null) {
            bookDto.setInventorySold(inventory.getSold());
            bookDto.setInventorySupplied(inventory.getSupplied());
        }

        // 3  Buscar los autores
        var client = ClientBuilder.newClient();
        AuthorDto[] authors = client.target("http://localhost:8080")
                .path("authors/find/{isbn}")
                .resolveTemplate("isbn", isbn)
                .request(MediaType.APPLICATION_JSON)
                .get(AuthorDto[].class);
        bookDto.setAuthors(
                Stream.of(authors)
                        .map(AuthorDto::getName)
                        .toList()
        );


        return Response.ok(bookDto).build();


    }


@GET
@Path("/getAll")
public Response getAll() {
    var client = ClientBuilder.newClient();

    List<BookDto> booksWithAuthors = booksRepository.listAll().stream().map(book -> {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(book.getPrice());

        if (book.getInventory() != null) {
            bookDto.setInventorySold(book.getInventory().getSold());
            bookDto.setInventorySupplied(book.getInventory().getSupplied());
        }

        try {
            AuthorDto[] authors = client.target("http://localhost:8080")
                    .path("authors/find/{isbn}")
                    .resolveTemplate("isbn", book.getIsbn())
                    .request(MediaType.APPLICATION_JSON)
                    .get(AuthorDto[].class);

            bookDto.setAuthors(Stream.of(authors)
                    .map(AuthorDto::getName)
                    .toList());
        } catch (Exception e) {
            // En caso de error en el servicio de autores, simplemente deja la lista vacía
            bookDto.setAuthors(List.of());
        }

        return bookDto;
    }).toList();

    return Response.ok(booksWithAuthors).build();
}



    @GET
    public List<Book> findAll() {
        return booksRepository.listAll();
    }

    @POST
    public void insert(Book book) {
        booksRepository.persist(book);
    }

}
