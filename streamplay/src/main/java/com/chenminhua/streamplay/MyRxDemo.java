package com.chenminhua.streamplay;


interface Observer<T> {
    void onCompleted();
    void onError(Throwable t);
    void onNext(T var1);
}

// 关系数据的东西，处理数据的东西
abstract class Subscriber<T> implements Observer<T> {
    public void onStart() {
    }
}

// 数据源
class Observable<T> {
    final OnSubscribe<T> onSubscribe;

    private Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<T>(onSubscribe);
    }

    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onStart();
        onSubscribe.call(subscriber);
    }

    // OnSubscribe 就是产生数据的那个东西，可以用一个lambda来表示
    public interface OnSubscribe<T> {
        void call(Subscriber<? super T> subscriber);
    }

    public <R> Observable<R> map(Transformer<? super T, ? extends R> transformer) {
        return create(subscriber -> {
            Observable.this.subscribe(new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    subscriber.onCompleted();
                }

                @Override
                public void onError(Throwable t) {
                    subscriber.onError(t);;
                }

                @Override
                public void onNext(T var1) {
                    subscriber.onNext(transformer.call(var1));
                }
            });
        });
    }

    public interface Transformer<T, R> {
        R call(T from);
    }
}


public class MyRxDemo {
    public static void main(String[] args) {
        Observable.create(  subscriber -> {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable t) {

            }
            @Override
            public void onNext(Object var1) {
                System.out.println(var1);
            }
        });

    }
}
