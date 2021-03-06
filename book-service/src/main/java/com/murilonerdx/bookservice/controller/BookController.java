package com.murilonerdx.bookservice.controller;

import com.murilonerdx.bookservice.exception.NotFoundBook;
import com.murilonerdx.bookservice.model.Book;
import com.murilonerdx.bookservice.proxy.CambioProxy;
import com.murilonerdx.bookservice.repository.BookRepository;
import com.murilonerdx.bookservice.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Tag(name="Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy proxy;

    @Operation(summary="Find a specific book your by ID")
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        try{
            var book = repository.getById(id);
            if (book == null) throw new RuntimeException("Book not Found");

            var cambio = proxy.getCambio(book.getPrice(), "USD", currency);

            var port = environment.getProperty("local.server.port");
            book.setEnvironment("Book port: " + port + " Cambio port: " +cambio.getEnvironment());
            book.setPrice(cambio.getConvertedValue());

            return book;
        }catch(RuntimeException e){
            throw new NotFoundBook("Book not found");
        }


    }


}
