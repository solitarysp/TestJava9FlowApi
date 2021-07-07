package com.lethanh98.test.PublisherAndSubscriber;

import java.util.concurrent.SubmissionPublisher;

public class MainPublisherAndSubscriber {
    public static void main(String[] args) throws InterruptedException {
        // Tạo 1 nhà suất bản các sự kiện (Server gửi)
        SubmissionPublisher<String> serverDataBook = new SubmissionPublisher<>();
        serverDataBook.subscribe(new ClientBookSubscriber("Client 1", 2));

        new Thread(() -> {
            try {
                serverDataBook.submit("Thân điêu đại hiệp");
                serverDataBook.submit("Anh Hùng xạ điêu");
                Thread.sleep(1000);
                serverDataBook.submit("Hiệp Khách Hành");
                serverDataBook.submit("Người phán xử");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(10000);
        serverDataBook.close();
        Thread.sleep(1000);

    }
}
