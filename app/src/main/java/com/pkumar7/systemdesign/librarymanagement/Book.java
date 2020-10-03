package com.pkumar7.systemdesign.librarymanagement;

import java.util.Date;

public abstract class Book {
    String title;
    int bookId;
    String subject;
    String author;
    Date publicationYear;

    public Book(String title, int bookId, String subject, String author, Date publicationYear) {
        this.title = title;
        this.bookId = bookId;
        this.subject = subject;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
