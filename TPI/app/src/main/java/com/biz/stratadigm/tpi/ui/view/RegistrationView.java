package com.biz.stratadigm.tpi.ui.view;

/**
 * Created by vkiyako on 1/6/17.
 */

public interface RegistrationView extends BaseView {
    String getEmail();

    String getUsername();

    String getPassword();

    void showRegistrationError();

    void showMainScreen();
}
