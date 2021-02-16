package com.liberary.store.demo.controller;

import com.liberary.store.demo.model.Book;
import com.liberary.store.demo.model.BookCheckOutRequest;
import com.liberary.store.demo.model.BookDetails;
import com.liberary.store.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public List<BookDetails> getAllBook(){
        return bookService.getAllBooks();
    }

    @PostMapping("/{bookCopy}")
    public BookDetails addBook(@RequestBody Book book, @PathVariable(value="bookCopy") Integer bookCount){
        return bookService.addBook(book,bookCount);
    }

    @GetMapping("/search/{params}")
    public List<Book> searchBookByNameOrAuthor(@PathVariable(value = "params") String params){
        return bookService.searchBook(params);
    }

    @PostMapping("/checkout")
    public BookDetails checkOutBook(@RequestBody BookCheckOutRequest bookCheckOutRequest) throws ParseException {
        return bookService.checkOutBook(bookCheckOutRequest);
    }

    @PutMapping("/return/{bookInstanceId}")
    public BookDetails returnBook(@PathVariable(value = "bookInstanceId") Long bookInstanceId) throws ParseException {
        return bookService.returnBook(bookInstanceId);
    }
    @PutMapping("/extendDay")
    public BookDetails extendDay(@RequestBody BookCheckOutRequest bookCheckOutRequest) throws ParseException {
        return bookService.extendDay(bookCheckOutRequest);
    }
}
