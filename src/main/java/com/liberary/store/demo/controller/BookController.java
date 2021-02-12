package com.liberary.store.demo.controller;

import com.liberary.store.demo.model.Book;
import com.liberary.store.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/")
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping("/search/{params}")
    public Book searchBookByNameOrAuthor(@PathVariable(value = "params") String params){
        return bookService.searchBook(params);
    }
}
