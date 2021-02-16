package com.liberary.store.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String isbn;

    @Column
    private String name;

    @Column
    private String author;

    @OneToMany(mappedBy = "book")
    private List<BookInstance> bookInstance;



}
