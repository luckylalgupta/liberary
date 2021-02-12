package com.liberary.store.demo.model;

import javax.persistence.*;
import javax.persistence.EnumType;
import java.util.Date;

@Entity
public class BookInstance {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Book book;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    private Date dueDate;
}
