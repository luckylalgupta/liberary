package com.liberary.store.demo.service;

import com.liberary.store.demo.exception.BookAllReadyReserved;
import com.liberary.store.demo.exception.BookNotFoundException;
import com.liberary.store.demo.model.*;
import com.liberary.store.demo.repository.BookInstanceRepository;
import com.liberary.store.demo.repository.BookRepository;
import com.liberary.store.demo.utility.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    public BookDetails addBook(Book book, Integer count){
        List<BookInstance> bookInstList= new ArrayList<>();
        book = bookRepository.save(book);
        for(int i=0;i<count;i++){
            BookInstance instance = new BookInstance();
            instance.setStatus(Status.Unreserved);
            instance.setBook(book);
            bookInstanceRepository.save(instance);
            bookInstList.add(instance);
        }
        book = bookRepository.getOne(book.getId());
        book.setBookInstance(bookInstList);
        BookDetails bookDetail = UtilityService.getBookDetailByBook(book);

      return bookDetail;
    }
    public BookDetails checkOutBook(BookCheckOutRequest bookCheckOutRequest) throws ParseException {
        BookInstance bookInstance = bookInstanceRepository.getOne(bookCheckOutRequest.getId());
        if(bookInstance.getStatus().equals(Status.Unreserved)){
            bookInstance.setStatus(Status.Reserved);

            //SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            int noOfDueDate = bookCheckOutRequest.getNoOfDueDays();

            Date currentDate = new Date();
            bookInstance.setIssueDate(currentDate);

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, noOfDueDate);

            Date currentDatePlusAdded = c.getTime();

           // Date customUtilDate = dateFormatter.parse(bookCheckOutRequest.getDate());
            bookInstance.setDueDate(currentDatePlusAdded);
            bookInstanceRepository.save(bookInstance);
            BookDetails bookDetails = UtilityService.getBookDetailByBookInstance(bookInstance);

            return bookDetails;
        }
            throw new BookAllReadyReserved("Book not found");

    }

    public List<Book> searchBook(String params) {
        List<Book> searchBooks = new ArrayList<>();
        List<Book> allBooks = bookRepository.findAll();
        for (Book book: allBooks){

            String bookName = book.getName().toLowerCase();
            String Author = book.getAuthor().toLowerCase();
            String ISBN = book.getIsbn().toLowerCase();

            params = params.toLowerCase();

            if(ISBN.matches("(.*)"+params+"(.*)")){
                searchBooks.add(book);
            }

            if(bookName.matches("(.*)"+params+"(.*)")){
                searchBooks.add(book);
            }

            if(Author.matches("(.*)"+params+"(.*)")){
                searchBooks.add(book);
            }
        }
        if(searchBooks.size()>0){
            return searchBooks;
        }
        throw new BookNotFoundException("Book not found");
    }


}
