package com.biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.biz.stratadigm.tpi.manager.AppSchedulers;
import com.biz.stratadigm.tpi.ui.view.BaseView;

import nucleus.presenter.RxPresenter;
import nucleus.presenter.delivery.Delivery;
import rx.Observable;
import rx.Subscriber;

public abstract class BasePresenter<V extends BaseView> extends RxPresenter<V> {
    private final Context context;
    private final AppSchedulers appSchedulers;

    @SuppressWarnings("WeakerAccess")
    public BasePresenter(Context applicationContext, AppSchedulers appSchedulers) {
        context = applicationContext;
        this.appSchedulers = appSchedulers;
    }

    protected final Context getContext() {
        return context;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public V getView() {
        return super.getView();
    }

    @SuppressWarnings("WeakerAccess")
    protected final <T> void executeRequest(Observable<T> observable, SimpleSubscriber<T> subscriber) {
        DeliveryWrapperSubscriber<V, T> subscriberWrapper = new DeliveryWrapperSubscriber<>(subscriber);
        observable.observeOn(appSchedulers.mainThread())
                .subscribeOn(appSchedulers.io())
                .compose(this.deliverFirst())
                .subscribe(subscriberWrapper);
    }

    /**
     * Adapts Delivery subscriber to usual rx subscriber
     */
    private static class DeliveryWrapperSubscriber<V, T> extends Subscriber<Delivery<V, T>> {
        private final Subscriber<T> subscriber;

        DeliveryWrapperSubscriber(Subscriber<T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void onStart() {
            subscriber.onStart();
        }

        @Override
        public void onCompleted() {
            subscriber.onCompleted();
        }

        @Override
        public final void onError(Throwable e) {
            subscriber.onError(e);
        }

        @Override
        public final void onNext(Delivery<V, T> delivery) {
            delivery.split((v, t) -> subscriber.onNext(t), (v, throwable) -> subscriber.onError(throwable));
        }
    }

    /**
     * Base class for all subscribers. It prints out errors
     */
    protected abstract static class SimpleSubscriber<T> extends Subscriber<T> {

        @CallSuper
        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }
    }
}
