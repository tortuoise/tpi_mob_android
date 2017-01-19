package biz.stratadigm.tpi.di.component;

import biz.stratadigm.tpi.di.module.LoginModule;
import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.ui.fragment.LoginFragment;
import dagger.Component;

@PerActivity
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginFragment loginFragment);
}
