// IOnNewBookArrivedListener.aidl
package com.example.xieqe.test001.aidl;
import com.example.xieqe.test001.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);
}
