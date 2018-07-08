package com.jimmy.mvp.external.lib.rxjava;

import com.jimmy.mvp.AbsPresenter;
import com.jimmy.mvp.IView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 该类和AbsPresenter的区别就在于它会自动回收Observer
 * 如果你有使用rxjava, 请使用本presenter
 *
 * @author yangyoujun
 * @Date 17-11-6
 */
public abstract class AbsRxPresenter<VP extends IView> extends AbsPresenter<VP> {

    protected AbsRxPresenter(VP viewProxy) {
        super(viewProxy);
    }

    @Override
    public void destroy() {
        DisposableManager.ins().dispose(toString());
        super.destroy();
    }

    /**
     * 如果你使用了RxPresenter来作为你的Presenter,那么请使用本Observer,因为它会自动回收
     */
    public abstract class ObserverOnRecycler<T> implements Observer<T> {

        private final String mName;
        private Disposable d;

        protected ObserverOnRecycler() {
            mName = AbsRxPresenter.this.toString();
        }

        @Override
        public void onSubscribe(Disposable d) {
            this.d = d;
            DisposableManager.ins().onSubscribe(mName, d);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            DisposableManager.ins().onError(mName, d);
            dispose();
        }

        @Override
        public void onComplete() {
            DisposableManager.ins().onComplete(mName, d);
            dispose();
        }

        protected void dispose() {
            if (d != null && !d.isDisposed()) {
                d.dispose();
            }
            d = null;
        }
    }

}
