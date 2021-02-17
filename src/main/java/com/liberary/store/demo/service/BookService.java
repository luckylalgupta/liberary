package com.liberary.store.demo.service;

import com.liberary.store.demo.exception.BookAllReadyReserved;
import com.liberary.store.demo.exception.BookIsDiscontinued;
import com.liberary.store.demo.exception.BookNotFoundException;
import com.liberary.store.demo.model.*;
import com.liberary.store.demo.repository.BookInstanceRepository;
import com.liberary.store.demo.repository.BookRepository;
import com.liberary.store.demo.utility.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    public BookDetails addBook(Book book, Integer count){
        List<BookInstance> bookInstList= new ArrayList<>();
        book.setStatus(Status.Live);

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
        else if(bookInstance.getStatus().equals(Status.Reserved))
            throw new BookAllReadyReserved("Book already reserved");
        else
            throw new BookIsDiscontinued("Sorry this book is discontinued");
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


    public BookDetails returnBook(Long bookInstanceId) {

        BookInstance instance = bookInstanceRepository.getOne(bookInstanceId);
        instance.setStatus(Status.Unreserved);
        instance.setIssueDate(null);
        instance.setDueDate(null);

        BookDetails bookDetails = UtilityService.getBookDetailByBookInstance(instance);

        return bookDetails;
    }

    public BookDetails extendDueDate(BookCheckOutRequest bookCheckOutRequest) {
        BookInstance bookInstance = bookInstanceRepository.getOne(bookCheckOutRequest.getId());
        int noOfDueDate = bookCheckOutRequest.getNoOfDueDays();

        Date currentDate = bookInstance.getDueDate();

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, noOfDueDate);

        Date currentDatePlusAdded = c.getTime();

        bookInstance.setDueDate(currentDatePlusAdded);
        bookInstanceRepository.save(bookInstance);
        BookDetails bookDetails = UtilityService.getBookDetailByBookInstance(bookInstance);

        return bookDetails;
    }

    public List<BookDetails> getAllBooks() {
        List<BookDetails> bookDetailList = new ArrayList<>();

        List<Book> allBooks = bookRepository.findAll();
        for(Book book:allBooks){
            BookDetails bookDetails = new BookDetails(book.getName(),book.getAuthor());
            bookDetailList.add(bookDetails);
        }
        return bookDetailList;
    }

    public BookDetails discontinueTheBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.get().setStatus(Status.Discontinued);
        bookRepository.save(book.get());
        List<BookInstance> bookInstances = bookInstanceRepository.getAllByBook_Id(book.get().getId());
        for(BookInstance bookInstance:bookInstances){
            if(bookInstance.getStatus().equals(Status.Unreserved)){
                bookInstance.setStatus(Status.Discontinued);
            }
        }
        bookInstanceRepository.saveAll(bookInstances);
        BookDetails bookDetail = UtilityService.getBookDetailByBook(book.get());

        return bookDetail;
    }
}
