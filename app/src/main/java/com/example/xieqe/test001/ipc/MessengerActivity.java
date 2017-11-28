package com.example.xieqe.test001.ipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2017/8/16.
 *
 */

public class MessengerActivity extends Activity {
    public Messenger sendMessenger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(getApplication(),MessengerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }
    //客户端发送消息部分
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            sendMessenger = new Messenger(binder);
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello this is client");
            Message msg = Message.obtain();
            msg.what = Constant.MSG_FROM_CLIENT;
            msg.setData(bundle);

            msg.replyTo = replyMessenger;//为发送的message设置接收服务端回复消息的messenger

            try {
                sendMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    //客户端接收消息部分，replyMessenger在发送消息时通过replyTo设置
    private Messenger replyMessenger = new Messenger(new MessengerHandler());
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
             switch (msg.what){
                case Constant.MSG_FROM_SERVER:
                    Log.i("MessengerActivity", "handleMessage: "+ msg.getData().get("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
