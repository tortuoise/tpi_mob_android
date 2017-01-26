package biz.stratadigm.tpi.presenter;

import android.content.Context;
import android.os.Bundle;

import biz.stratadigm.tpi.interactor.GetThalisInteractor;
import biz.stratadigm.tpi.manager.AppSchedulers;
import biz.stratadigm.tpi.ui.view.ThaliListView;

public class ThaliListPresenter extends BasePresenter<ThaliListView> {
    public ThaliListPresenter(Context applicationContext,
                              AppSchedulers appSchedulers,
                              GetThalisInteractor getThalisInteractor) {
        super(applicationContext, appSchedulers);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

    }
}
