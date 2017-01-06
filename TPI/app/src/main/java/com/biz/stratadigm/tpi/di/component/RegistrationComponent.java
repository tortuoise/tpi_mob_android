package com.biz.stratadigm.tpi.di.component;

import com.biz.stratadigm.tpi.di.module.RegistrationModule;
import com.biz.stratadigm.tpi.di.scope.PerActivity;
import com.biz.stratadigm.tpi.ui.fragment.RegistrationFragment;

import dagger.Component;

@PerActivity
@Component(modules = RegistrationModule.class, dependencies = AppComponent.class)
public interface RegistrationComponent {
    void inject(RegistrationFragment registrationFragment);
}
