package com.example.xieqe.test001.RxJava;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by xieqe on 2017/11/16.
 */

public class Test {

    private static final String TAG = "test===";



    //创建观察者
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.i(TAG, "observer onNext: "+s);
        }

        @Override
        public void onCompleted() {
            Log.i(TAG, "observer onCompleted: ");
        }


        @Override
        public void onError(Throwable e) {

        }
    };

    //Subscriber是实现了Observer的抽象类,也可当做观察者使用，
    //同时还增加了onStart方法,在订阅刚开始，事件还未发送之前被调用，
    //onStart可用于做一些准备工作
    Subscriber<String> subscriber = new Subscriber<String>() {

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onNext(String s) {
            Log.i(TAG, "subscriber onNext: "+s);
        }

        @Override
        public void onCompleted() {
            Log.i(TAG, "subscriber onCompleted: ");
        }

        @Override
        public void onError(Throwable e) {

        }
    };

    //创建被观察者，call方法在被订阅(OnSubscribe)时触发
    Observable observable = Observable.create(new Observable.OnSubscribe() {
        @Override
        public void call(Object o) {
            subscriber.onNext("111");
            subscriber.onNext("222");
            subscriber.onCompleted();
        }
    });

    Observable observable1 = Observable.just("111","222");

    public void testRxJava(){
        //observer向被观察者订阅
        observable.subscribe(observer);

        //subscriber向被观察者订阅
        observable.subscribe(subscriber);


        observable1.subscribe(subscriber);
    }
}
