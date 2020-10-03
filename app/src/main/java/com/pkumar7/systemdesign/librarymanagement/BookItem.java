package com.pkumar7.systemdesign.librarymanagement;

import java.util.Date;

class BookItem extends Book implements Cloneable {
    protected Date issueDate;
    protected Date dueDate;
    protected BookStatus status;

    public BookItem(String title, int bookId, String subject, String author, Date publicationYear) {
        super(title, bookId, subject, author, publicationYear);
        status = BookStatus.AVAILABLE;
    }

    @Override
    protected BookItem clone() throws CloneNotSupportedException {
        return (BookItem) super.clone();
    }
}
