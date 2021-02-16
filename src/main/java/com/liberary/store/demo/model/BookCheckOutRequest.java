package com.liberary.store.demo.model;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public class BookCheckOutRequest {
    Long id;
    int noOfDueDays;
}
