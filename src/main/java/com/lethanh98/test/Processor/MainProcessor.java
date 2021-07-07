package com.lethanh98.test.Processor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class MainProcessor {
    public static void main(String[] args) throws InterruptedException {
        // Tạo 1 người gửi data
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // Tạo 1 center process để vừa là người gửi vừa là người nhận data từ 1 server
        CenterProcessor centerProcessor = new CenterProcessor();

        publisher.subscribe(centerProcessor);

        // đăng ký nhận data với cetner
        centerProcessor.subscribe(new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("Nhận data từ center : " + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("throwable");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

        new Thread(() -> {
            try {
                publisher.submit("Thân điêu đại hiệp");
                publisher.submit("Anh Hùng xạ điêu");
                Thread.sleep(1000);
                publisher.submit("Hiệp Khách Hành");
                publisher.submit("Người phán xử");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(10000);
        publisher.close();
        Thread.sleep(1000);
    }
}
