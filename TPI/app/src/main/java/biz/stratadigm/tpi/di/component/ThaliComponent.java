package biz.stratadigm.tpi.di.component;

import biz.stratadigm.tpi.di.module.ThaliModule;
import biz.stratadigm.tpi.di.scope.PerActivity;
import biz.stratadigm.tpi.ui.fragment.ThaliFragment;
import biz.stratadigm.tpi.ui.fragment.UploadPhotoFragment;
import dagger.Component;

@PerActivity
@Component(modules = ThaliModule.class, dependencies = AppComponent.class)
public interface ThaliComponent {
    void inject(ThaliFragment thaliFragment);
    void inject(UploadPhotoFragment uploadPhotoFragment);
}

