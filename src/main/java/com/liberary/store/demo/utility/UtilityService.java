package com.liberary.store.demo.utility;

import com.liberary.store.demo.model.Book;
import com.liberary.store.demo.model.BookDetails;
import com.liberary.store.demo.model.BookInstance;

public class UtilityService {
    public static BookDetails getBookDetailByBook(Book book){
        BookDetails bookDetails = new BookDetails(book.getName(),book.getAuthor());
        return bookDetails;
    }

    public static BookDetails getBookDetailByBookInstance(BookInstance instance){
        Book book = instance.getBook();
        BookDetails bookDetails = new BookDetails(book.getName(),book.getAuthor());
        return bookDetails;
    }
}
