package com.example.xieqe.test001.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by xieqe on 2017/8/17.
 *
 */

public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1,"android"));
    }

    private Binder binder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
            notifyClient(book);//从listener集合遍历通知
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
        }
    };

    private void notifyClient(Book newBook){
        int N = listeners.beginBroadcast();//返回集合中总的监听数量
        for (int i = 0; i < N; i++){
            try {
                listeners.getBroadcastItem(i).onNewBookArrived(newBook);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        listeners.finishBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission(
                "com.example.xieqe.test001.permission.ACCESS_AIDL_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return binder;
    }

}
