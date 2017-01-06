package com.biz.stratadigm.tpi.di.component;

import com.biz.stratadigm.tpi.di.module.LoginModule;
import com.biz.stratadigm.tpi.di.scope.PerActivity;
import com.biz.stratadigm.tpi.ui.fragment.LoginFragment;

import dagger.Component;

@PerActivity
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginFragment loginFragment);
}
