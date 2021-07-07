package com.lethanh98.test.PublisherAndSubscriber;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class MainPublisherAndSubscriberAnonymous {
    public static void main(String[] args) throws InterruptedException {
        // Tạo 1 nhà suất bản các sự kiện (Server gửi)
        SubmissionPublisher<String> serverDataBook = new SubmissionPublisher<>();
        serverDataBook.subscribe(new Flow.Subscriber<String>() {
            Flow.Subscription subscription;
            boolean request = true;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("Nhận thông tin nhạc : " + item + " mới ra mắt : Thread: " + Thread.currentThread().getName());
                if (request) {
                    // Nếu không có hành động gửi request thêm, thì vẫn sẽ nhận tối đa 5 data mới từ server gửi đến
                    // gửi thông báo nếu không có request mới thì vẫn nhận tối đa 5 request onNext
                    subscription.request(5);
                    request=false;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError: " + " Thread: " + Thread.currentThread().getName());

            }

            @Override
            public void onComplete() {
                // Khi server hoàn tất viễn viễn việc gửi data, sẽ thông báo onComplete
                System.out.println("onComplete: " + " Thread: " + Thread.currentThread().getName());
            }
        });

        new Thread(() -> {
            serverDataBook.submit("Em của ngày hôm kia");
            serverDataBook.submit("Thói đời");
            serverDataBook.submit("Người tình mua đông");
            serverDataBook.submit("Anh thanh niên");

        }).start();
        Thread.sleep(10000);
        serverDataBook.close();
        Thread.sleep(1000);
    }
}
