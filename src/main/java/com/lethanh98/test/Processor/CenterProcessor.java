package com.lethanh98.test.Processor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

// 1 center vừa là người gửi data (Server)  và là người nhận data
public class CenterProcessor extends SubmissionPublisher<String> implements Flow.Subscriber<String> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String data) {
        submit(data);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        closeExceptionally(throwable);
    }

    @Override
    public void onComplete() {
        close();
    }
}
