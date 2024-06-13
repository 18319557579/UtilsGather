package com.example.utilsuser.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        GuideSettings.set(findViewById(R.id.lv_launcher_rxjava), new GuideItemEntity[]{
                new GuideItemEntity("使用fromCallable()", () -> one()),
                new GuideItemEntity("使用create()", () -> two()),
                new GuideItemEntity("两次切换线程 map()，都传递数据", () -> three()),
                new GuideItemEntity("两次切换线程 flatmap(), 都传递数据", () -> four()),
                new GuideItemEntity("两次切换线程 第一次为手动传递数据，第二次不传递数据", () -> five()),
                new GuideItemEntity("两次切换线程 第一次为return数据，第二次不传递数据(是真的没有数据了)", () -> five2()),
                new GuideItemEntity("两次切换线程 都不传递数据", this::six),
                new GuideItemEntity("两次切换线程 第一次不传递数据，第二次为return数据", this::seven),
                new GuideItemEntity("两次切换线程 第一次不传递数据，第二次为手动传递数据", this::eight)
        });
    }


    private void one() {
        Observable.fromCallable(() -> {
                    // 执行一些耗时任务
                    Thread.sleep(1000); // 模拟耗时
                    return "从后台任务获取的数据";
                }).subscribeOn(Schedulers.io()) // 指定在IO线程执行任务
                .observeOn(AndroidSchedulers.mainThread()) // 指定在主线程观察结果
                .subscribe(result -> {
                    LogUtil.d("更新TextView: " + result + ", 当前的线程: " + Thread.currentThread());
                });
    }

    private void two() {
        Observable.create((ObservableOnSubscribe<String>) observableEmitter -> {
                    observableEmitter.onNext("从后台任务获取的数据");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    LogUtil.d("更新TextView: " + s + ", 当前的线程: " + Thread.currentThread());
                });
    }

    private void three() {
        Observable.create((ObservableOnSubscribe<String>) observableEmitter -> {
                    Thread.sleep(1000); // 模拟耗时
                    observableEmitter.onNext("从后台任务获取的数据1");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread() + ", 传值: " + s);
                    return new User("hsf", 25);
                })
                .subscribe(user -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread() + ", use: " + user);
                });
    }

    private void four() {
        Observable.create((ObservableOnSubscribe<String>) observableEmitter -> {
                    Thread.sleep(1000); // 模拟耗时
                    observableEmitter.onNext("从后台任务获取的数据1");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(s -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread() + ", 传值: " + s);
                    return new Observable<Object>() {
                        @Override
                        protected void subscribeActual(Observer<? super Object> observer) {
                            observer.onNext(new User("hsf", 25));
                        }
                    };
                })
                .subscribe(user -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread() + ", use: " + user);
                });
    }

    private void five() {
        Observable.create((ObservableOnSubscribe<String>) observableEmitter -> {
                    Thread.sleep(1000); // 模拟耗时
                    observableEmitter.onNext("从后台任务获取的数据1");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //确实，doOnNext()即不用return Bean类型，也不用return Observable
                .doOnNext(s -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread() + ", 传值: " + s);
                })
                .observeOn(Schedulers.io())
                .subscribe(s -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread() + ", 这个传值我不需要: " + s);
                });

    }

    private void five2() {
        Observable.fromCallable(() -> {
                    Thread.sleep(1000); // 模拟耗时
                    return new User("hsf", 25);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread() + ", 传值: " + s);
                })
                //这个操作符可以将一个Observable转换为Completable，它忽略掉所有的onNext事件，只传递onComplete和onError状态
                .ignoreElements()
                .observeOn(Schedulers.io())
                .subscribe(() -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread() );
                });
    }

    private void six() {
        Completable.fromAction(() -> {
                    Thread.sleep(1000); // 模拟耗时
                    LogUtil.d("在后台执行任务");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread());
                })
                .observeOn(Schedulers.io())
                .subscribe(() -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread());
                });
    }

    private void seven() {
        Completable.fromAction(() -> {
            Thread.sleep(1000); // 模拟耗时
            LogUtil.d("在后台执行任务");
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(Observable.fromCallable(() -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread());
                    return new User("hsf", 25);
                }))
                .observeOn(Schedulers.io())
                .subscribe(user -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread() + ", 传值: " + user);
                });
    }
    private void eight() {
        Completable.fromAction(() -> {
                    Thread.sleep(1000); // 模拟耗时
                    LogUtil.d("在后台执行任务");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(Observable.fromPublisher((Publisher<User>) subscriber -> {
                    LogUtil.d("当前在主线程中进行操作: " + Thread.currentThread());
                    subscriber.onNext(new User("hsf", 25));
                }))
                .observeOn(Schedulers.io())
                .subscribe(user -> {
                    LogUtil.d("又回到子线程了: " + Thread.currentThread() + ", 传值: " + user);
                });
    }
}