package com.pkumar7.systemdesign.librarymanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class BookCatalog  {
    List<BookItem> allBooks = new ArrayList<>();

    HashMap<Integer, BookItem> booksById = new HashMap<>();
    int id = 1;

    int N_UNIQUE_BOOKS = 20;
    int M_UNIQUE_COPIES = 3;

    public BookCatalog(){
        addBooks();
    };

    public void addBooks() {
        Date currentDate = new Date(System.currentTimeMillis());

        try {
            BookItem book1 = new BookItem("The Prophet", id,
                    "Philosophy", "Khalil Gibran", currentDate);
            booksById.put(id, book1);
            allBooks.add(book1);

            int copies = M_UNIQUE_COPIES;
            while (copies > 0) {
                BookItem clone = book1.clone();
                clone.bookId = ++id;
                booksById.put(id, clone);
                allBooks.add(clone);
                copies--;
            }

            BookItem book2 = new BookItem("Train to Pakistan", ++id,
                    "Literature", "Ismail Khan", currentDate);
            booksById.put(id, book2);
            allBooks.add(book2);

            copies = M_UNIQUE_COPIES;
            while (copies > 0) {
                BookItem clone = book2.clone();
                clone.bookId = ++id;
                booksById.put(id, clone);
                allBooks.add(clone);
                copies--;
            }

            BookItem book3 = new BookItem("Essential Sociology - For Civil Services Mains", ++id,
                    "Education", "Kumar Gaurav", currentDate);
            booksById.put(id, book3);
            allBooks.add(book3);

            copies = M_UNIQUE_COPIES;
            while (copies > 0) {
                BookItem clone = book3.clone();
                clone.bookId = ++id;
                booksById.put(id, clone);
                allBooks.add(clone);
                copies--;
            }
            BookItem book4 = new BookItem("India Since Independence", ++id,
                    "Social Science", "Sumit Kumar", currentDate);
            booksById.put(id, book4);
            allBooks.add(book4);

            copies = M_UNIQUE_COPIES;
            while (copies > 0) {
                BookItem clone = book4.clone();
                clone.bookId = ++id;
                booksById.put(id, clone);
                allBooks.add(clone);
                copies--;
            }
        } catch (CloneNotSupportedException ex) {
        }

    }


    public List<BookItem> searchByTitle(String title) {
        List<BookItem> res = new ArrayList<>();
        for(BookItem book : allBooks){
            if(book.title.contains(title)){
                res.add(book);
            }
        }
        return res;
    }

    public List<BookItem> getAvailableBooks(){
        List<BookItem> res = new ArrayList<>();
        for (BookItem item : allBooks){
            if(item.status == BookStatus.AVAILABLE){
                res.add(item);
            }
        }
        return res;
    }

    public List<BookItem> getIssuedBooks(){
        List<BookItem> res = new ArrayList<>();
        for (BookItem item : allBooks){
            if(item.status == BookStatus.ISSUED){
                res.add(item);
            }
        }
        return res;
    }
}
