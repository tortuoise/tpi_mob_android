package biz.stratadigm.tpi.ui.view;

import java.util.List;

import biz.stratadigm.tpi.entity.vo.ThaliVO;

/**
 * Created by vkiyako on 1/20/17.
 */

public interface ThaliView extends BaseView {

    Long getIdent();

    String getName();

    String getTarget();

    Boolean getLimited();

    String getRegion();
    
    Integer getPrice();
    
    Long getUserId();

    Long getVenue();

    void showThali();
    
    void showTakePhoto();

    void showAuthError();
}
