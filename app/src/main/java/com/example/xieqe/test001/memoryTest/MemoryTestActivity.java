package com.example.xieqe.test001.memoryTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xieqe.test001.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xieqe on 2018/3/12.
 */

public class MemoryTestActivity extends Activity {
    private static final String TAG = "MemoryTestActivity";
    private DisposableObserver observer;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        testRxMemory();
    }


    private void testRxMemory() {
        compositeDisposable = new CompositeDisposable();
        observer = new DisposableObserver<Long>() {
            @Override
            public void onNext(Long along) {
                Log.i(TAG, "onNext: " + String.valueOf(along));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        compositeDisposable.add(observer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
            observer = null;
        }
    }
}
