package biz.stratadigm.tpi.ui.view;

import java.util.List;

import biz.stratadigm.tpi.entity.vo.ThaliVO;

/**
 * Created by vkiyako on 1/20/17.
 */

public interface ThaliListView extends BaseView {
    void showSplashLoader(boolean show);

    void showThalisFilterLabel(String venueName);

    void showThalis(List<ThaliVO> newThalis);

    void setThalis(List<ThaliVO> newThalis);

    void addThalis(List<ThaliVO> newThalis);

    void showAddThali();

    void showAuthError();
}
