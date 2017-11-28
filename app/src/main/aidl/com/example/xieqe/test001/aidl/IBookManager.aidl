// IBookManager.aidl
package com.example.xieqe.test001.aidl;

// Declare any non-default types here with import statements
import com.example.xieqe.test001.aidl.Book;
import com.example.xieqe.test001.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
