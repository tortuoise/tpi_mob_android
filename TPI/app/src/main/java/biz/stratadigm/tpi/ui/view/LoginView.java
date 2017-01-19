package biz.stratadigm.tpi.ui.view;

/**
 * Created by vkiyako on 1/5/17.
 */

public interface LoginView extends BaseView {
    String getUsername();

    String getPassword();

    void showAuthError();

    void showMainScreen();
}
