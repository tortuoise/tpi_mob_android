package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.support.annotation.CallSuper;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import biz.stratadigm.tpi.BuildConfig;
import biz.stratadigm.tpi.manager.AppSchedulers;
import rx.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 23)
public abstract class BasePresenterTest {
    private Context context;
    /**
     * executes all background code synchronously for testing
     */
    private static final AppSchedulers appSchedulers = new AppSchedulers(Schedulers.immediate(), Schedulers.immediate());

    @CallSuper
    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application;
    }

    public Context getContext() {
        return context;
    }

    protected static AppSchedulers getAppSchedulers() {
        return appSchedulers;
    }


}
