package com.mission.google.systemdesign.librarymanagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class BookReservation {
    private static Object lock = new Object();
    private static BookReservation mInstance;
    private static BookCatalog bookCatalog;
    HashMap<Integer, List<BookItem>> userBookMap = new HashMap<>();

    public static BookReservation getInstance(){
        synchronized (lock){
            if(mInstance == null){
                mInstance = new BookReservation();
                bookCatalog = new BookCatalog();
            }
        }
        return mInstance;
    }

    private BookReservation (){
    }

    public boolean checkStatus(int userId, BookItem bookItem){
        BookItem available = fetchDetails(userId, bookItem);
        if(available == null){
            System.out.println(" Book already issued to user " + userId );
            return false;
        }
        return true;
    }
    public boolean lendBook(BookItem book, int userId){
        if(!checkStatus(userId, book)){
            return false;
        }
        if(book.status == BookStatus.ISSUED){
            System.out.println(" Book is already issued ");
            return false;
        }
        userBookMap.putIfAbsent(userId, new ArrayList<>());
        book.issueDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, Util.MAX_ISSUE_PERIOD);
        Date after10Days = new Date(calendar.getTimeInMillis());
        book.dueDate = after10Days;
        book.status = BookStatus.ISSUED;
        userBookMap.get(userId).add(book);
        return true;
    }

    public BookItem fetchDetails(int user, BookItem book){
        if(userBookMap.containsKey(user)) {
            for (BookItem booksByUser : userBookMap.get(user)) {
                if (booksByUser.title.equals(book.title)) {
                    return null;
                }
            }
        }
        return book;
    }

    public void returnBook(int userId, BookItem bookItem){
        if(userBookMap.containsKey(userId)){
            boolean removed = userBookMap.get(userId).remove(bookItem);
            if(removed) {
                bookItem.status = BookStatus.AVAILABLE;
                System.out.println("Book returned " + bookItem.bookId +
                        " " + bookItem.title );
            }
        }
    }

    public List<BookItem> getAvailableBooks(){
        return bookCatalog.getAvailableBooks();
    }

    public List<BookItem> getIssuedBooks(){
        return bookCatalog.getIssuedBooks();
    }

    public List<BookItem> searchByTitle(String title){
        return bookCatalog.searchByTitle(title);
    }


}
