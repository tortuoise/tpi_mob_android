package biz.stratadigm.tpi.di.component;

import biz.stratadigm.tpi.di.module.RegistrationModule;
import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.ui.fragment.RegistrationFragment;
import dagger.Component;

@PerActivity
@Component(modules = RegistrationModule.class, dependencies = AppComponent.class)
public interface RegistrationComponent {
    void inject(RegistrationFragment registrationFragment);
}
