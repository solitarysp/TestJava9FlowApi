package com.lethanh98.test.PublisherAndSubscriber;

import java.util.concurrent.Flow;

public class ClientBookSubscriber implements Flow.Subscriber<String> {
    Flow.Subscription subscription;
    private String name;
    private int numberReceivingItems = 1;

    public ClientBookSubscriber(String name) {
        this.name = name;
    }

    public ClientBookSubscriber(String name, int numberReceivingItems) {
        this.name = name;
        this.numberReceivingItems = numberReceivingItems;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(numberReceivingItems);
    }

    @Override
    public void onNext(String item) {
        try {
            System.out.println(name + ": Nhận thông tin sách : " + item + " mới ra mắt : Thread: " + Thread.currentThread().getName());
        } finally {
            // Thực hiện request số lượng item có thể xử lý tại thời điểm này
            subscription.request(numberReceivingItems);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(name + ": Error: " + " Thread: " + Thread.currentThread().getName());
    }

    @Override
    public void onComplete() {
        System.out.println(name + ": onComplete: " + " Thread: " + Thread.currentThread().getName());
    }
}
