package com.liberary.store.demo.service;

import com.liberary.store.demo.repository.LibraryRepository;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;
}
