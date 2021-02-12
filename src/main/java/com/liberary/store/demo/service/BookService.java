package com.liberary.store.demo.service;

import com.liberary.store.demo.exception.BookNotFoundException;
import com.liberary.store.demo.model.Book;
import com.liberary.store.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book){
      return bookRepository.save(book);
    }

    public Book searchBook(String params) {

        List<Book> allBooks = bookRepository.findAll();
        for (Book book: allBooks){
            String bookName = book.getName().toLowerCase();
            String Author = book.getAuthor().toLowerCase();
            params = params.toLowerCase();

            if(book.getIsbn().equals(params)){
                return book;
            }

            if(bookName.matches("(.*)"+params+"(.*)")){
                return book;
            }
            if(Author.matches("(.*)"+params+"(.*)")){
                return book;
            }
        }
        throw new BookNotFoundException("Book not found");
    }
}
