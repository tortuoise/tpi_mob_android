package biz.stratadigm.tpi.ui.view;

import java.util.List;

import biz.stratadigm.tpi.entity.vo.VenueVO;

/**
 * Created by vkiyako on 1/20/17.
 */

public interface VenuesView extends BaseView {
    //void showSplashLoader(boolean show);

    void showVenues(List<VenueVO> newVenues);

    void addVenues(List<VenueVO> newVenues);

    void showAuthError();
}
