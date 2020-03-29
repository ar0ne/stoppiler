package com.ar0ne.stoppiler.ui;

import android.content.Context;
import android.util.AttributeSet;

public class RegularFontAwesome extends FontAwesome {

    public RegularFontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RegularFontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegularFontAwesome(Context context) {
        super(context);
    }

    @Override
    String getPath() {
        return "fontawesome_regular.ttf";
    }
}