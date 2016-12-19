package com.biz.stratadigm.tpi.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by tamara on 12/2/16.
 * EditText with customize attributes
 */

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
    }


    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
