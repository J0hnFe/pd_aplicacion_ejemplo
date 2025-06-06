package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.AuthorDto;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BooksRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.stork.Stork;
import io.smallrye.stork.api.Service;
import io.smallrye.stork.api.ServiceInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {

    @Inject
    BooksRepository booksRepository;

    @Inject
    ModelMapper mapper;

//    @Inject
//    Stork stork;

    @Inject
    @ConfigProperty(name = "authors.url")
    String authorsUrl;

    @Inject
    @RestClient
    private AuthorRestClient authorRestClient;

    @GET
    @Path("/{isbn}")
    public Response findById(@PathParam("isbn") String isbn) {

        var stork = Stork.getInstance();

        Service service = stork.getService("my-service");
        Uni<List<ServiceInstance>> instances = service.getInstances();
        Uni<ServiceInstance> instance = service.selectInstance();

        Map<String, Service> services = stork.getServices();
        services.entrySet().stream().forEach(it -> {
            System.out.println(it.getKey() + " -> " + it.getValue());
            var ser = it.getValue();

            ser.getInstances()
                    .subscribe().with(
                            ls -> {
                                ls.forEach(
                                        inst -> {
                                            System.out.println(" " + inst.getHost() + ":" + inst.getPort());
                                        });
                            });
        });

        System.out.println("Available services: " + services);

        // Crear un nuevo DTO para el libro
        BookDto bookDto = new BookDto();

        // Buscar el libro por su ISBN en el repositorio
        var obj = booksRepository.findByIdOptional(isbn);
        if (obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Mapear la entidad Book encontrada al DTO
        mapper.map(obj.get(), bookDto);

        // Consultar los autores asociados al libro usando el cliente REST
        var authors = authorRestClient.findByBook(isbn)
                .stream()
                .map(AuthorDto::getName) // Obtener solo los nombres de los autores
                .toList();

        // Asignar la lista de autores al DTO del libro
        bookDto.setAuthors(authors);
        // Retornar la respuesta con el DTO del libro y estado 200 OK
        return Response.ok(bookDto).build();
    }


    @GET
    @Path("/findAll")
    public List<BookDto> findAll() {
        // Crear un cliente REST para consultar los autores usando la URL configurada
        AuthorRestClient client = RestClientBuilder.newBuilder()
                .baseUri(authorsUrl)
                .build(AuthorRestClient.class);

        // Obtener todos los libros del repositorio y mapearlos a DTOs
        return booksRepository.streamAll()
                .map(book -> {
                    var dto = new BookDto(); // Mapear la entidad Book a BookDto
                    mapper.map(book, dto);
                    return dto;
                })
                .map(book -> {
                    // Consultar los autores del libro usando el cliente REST
                    var authors = client.findByBook(book.getIsbn())
                            .stream()
                            .map(AuthorDto::getName) // Obtener solo los nombres de los autores
                            .toList();
                    book.setAuthors(authors); // Asignar la lista de autores al DTO del libro
                    return book;
                })
                .toList();
    }

    @POST
    public void insert(Book book) {
        booksRepository.persist(book);
    }

}
