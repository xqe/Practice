package com.example.xieqe.test001.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xieqe.test001.R;

import java.util.List;

/**
 * Created by xieqe on 2017/8/18.
 */

public class BookAidlTestActivity extends Activity {

    private IBookManager bookManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            bookManager = IBookManager.Stub.asInterface(binder);

            try {
                //客户端绑定远程服务成功后，为binder绑定死亡代理
                binder.linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                List<Book> list = bookManager.getBookList();
                Log.i("BookAidlTestActivity", "onServiceConnected: "+list.get(0).bookName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                bookManager.addBook(new Book(3,"android artest"));
                bookManager.registerListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.i("BookAidlTestActivity", "onNewBookArrived: "+newBook.toString());
            //若要进行UI操作，需交给Handler执行，因为这个方法是在Binder线程池中执行的
            //handler.send ...
        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (bookManager == null){
                return;
            }
            //取消死亡代理绑定
            bookManager.asBinder().unlinkToDeath(deathRecipient,0);
            bookManager = null;

            //可在此重新发送请求，进行一些重连操作
            //....
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookManager != null && bookManager.asBinder().isBinderAlive()){
            try {
                bookManager.unregisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
    }
}
