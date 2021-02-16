package com.liberary.store.demo.repository;

import com.liberary.store.demo.model.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstance,Long> {
}
