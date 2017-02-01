package biz.stratadigm.tpi.di.component;

import biz.stratadigm.tpi.di.module.VenuesModule;
import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.ui.fragment.VenuesFragment;
import dagger.Component;

@PerActivity
@Component(modules = VenuesModule.class, dependencies = AppComponent.class)
public interface VenuesComponent {
    void inject(VenuesFragment venuesFragment);
}

