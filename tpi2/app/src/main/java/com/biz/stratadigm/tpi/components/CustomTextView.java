package com.biz.stratadigm.tpi.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tamara on 12/4/16.
 * TextView with customize attributes
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
