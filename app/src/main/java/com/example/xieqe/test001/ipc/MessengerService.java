package com.example.xieqe.test001.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by xieqe on 2017/8/16.
 *
 */

public class MessengerService extends Service {

    private final Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    Log.i("MessengerService", "handleMessage: "+msg.getData().getString("msg"));

                    Messenger replyMessenger = msg.replyTo;//Client发送消息时设置的replyTo Messenger

                    Bundle bundle = new Bundle();
                    bundle.putString("reply","got your message  --reply from service");
                    Message replyMessage = Message.obtain();
                    replyMessage.what = Constant.MSG_FROM_SERVER;
                    replyMessage.setData(bundle);

                    try {
                        replyMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
