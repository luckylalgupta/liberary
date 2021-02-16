package com.liberary.store.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.EnumType;
import java.util.Date;

@Entity
@Getter @Setter
public class BookInstance {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JsonIgnore
    @ManyToOne
    private Book book;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Status status;

    @Column
    private Date issueDate;

    @Column
    private Date dueDate;

    @Override
    public String toString() {
        return "BookInstance{" +
                "Id=" + Id +
                ", book=" + book +
                ", status=" + status +
                ", dueDate=" + dueDate +
                '}';
    }
}
