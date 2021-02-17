package com.liberary.store.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.EnumType;
import java.util.List;

@Entity
@Getter @Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;

    @Column
    private String name;

    @Column
    private String author;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Status status;

    @OneToMany(mappedBy = "book")
    private List<BookInstance> bookInstance;

}
