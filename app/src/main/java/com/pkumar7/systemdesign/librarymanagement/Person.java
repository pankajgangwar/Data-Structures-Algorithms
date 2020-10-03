package com.pkumar7.systemdesign.librarymanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Person {

    public static void main(String[] args) {
        Person person = new Person();
        String title = "The Prophet";
        List<BookItem> booksAvailable = person.searchForBook(title);
        BookItem firstItem = booksAvailable.get(0);
        System.out.println("*****************");
        person.checkoutBookItem(firstItem);
        System.out.println("*****************");
        person.getAvailableBooks();
        System.out.println("*****************");
        person.allIssuedBooks();
        System.out.println("*****************");
        person.returnBook(firstItem);
        System.out.println("*****************");
        person.getAvailableBooks();
    }

    int personId;
    int totalBooksIssued;
    public void increaseBookIssued(){
        totalBooksIssued++;
    }


    public Person() {
        bookReservation = BookReservation.getInstance();
    }

    BookReservation bookReservation;
    public int getTotalBooksIssued(){
        return totalBooksIssued;
    }

    public List<BookItem> searchForBook(String title){
        List<BookItem> res = bookReservation.searchByTitle(title);
        List<BookItem> availableBooks = new ArrayList<>();
        for(BookItem item : res){
            System.out.println("Book info = " + item.title + " " + item.author + " " + item.subject);
            if(item.status == BookStatus.AVAILABLE){
                System.out.println("Book available ");
                availableBooks.add(item);
            }else{
                Date availability = item.dueDate;
                DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                System.out.println("Book already issued. Last date of availability = " + formatter.format(availability));
            }
        }
        return availableBooks;
    }

    public boolean checkoutBookItem(BookItem bookItem){
        if(getTotalBooksIssued() >= Util.MAX_BOOKS_ISSUED_TO_USER){
            System.out.println(" This user has already issued max allowed books !!" );
            return false;
        }

        if(bookReservation.lendBook(bookItem, personId)){
            increaseBookIssued();
            System.out.println(" Book id: " + bookItem.bookId + " "
                    +  bookItem.title + " issued to user " + personId );
            return true;
        }
        return false;
    }

    public void returnBook(BookItem bookItem){
        bookReservation.returnBook(personId, bookItem);
    }

    public void allIssuedBooks(){
        List<BookItem> res = bookReservation.getIssuedBooks();
        System.out.println("Issued books: " );
        for (BookItem item : res) {
            Date availability = item.dueDate;
            DateFormat formatter = new SimpleDateFormat("dd.MM HH:mm ");
            System.out.println(item.title + " Latest date of availability " + formatter.format(availability));
        }
    }

    public void getAvailableBooks(){
        List<BookItem> res = bookReservation.getAvailableBooks();
        System.out.println("Available books: " );
        for (BookItem item : res) {
            System.out.println(item.title + " id " + item.bookId);
        }
    }

}
