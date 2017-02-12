package biz.stratadigm.tpi.di.component;

import biz.stratadigm.tpi.di.module.ThaliListModule;
import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.ui.fragment.ThaliListFragment;
import dagger.Component;

@PerActivity
@Component(modules = ThaliListModule.class, dependencies = AppComponent.class)
public interface ThaliListComponent {
    void inject(ThaliListFragment thaliListFragment);
}

